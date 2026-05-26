package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.api.Content
import com.example.api.GeminiRequest
import com.example.api.Part
import com.example.api.RetrofitClient
import com.example.data.AppDatabase
import com.example.data.AssistantChatMessage
import com.example.data.CodingProgress
import com.example.data.FitnessStats
import com.example.data.SubjectProgress
import com.example.data.TimetableTask
import com.example.data.UserLevelState
import com.example.data.VayuRepository
import com.example.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VayuViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: VayuRepository

    init {
        val db = AppDatabase.getDatabase(application)
        repository = VayuRepository(db)
    }

    // --- REPOSITORY OBSERVABLES ---
    val allTasks: StateFlow<List<TimetableTask>> = repository.allTasks
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allSubjectProgress: StateFlow<List<SubjectProgress>> = repository.allSubjectProgress
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allCodingProgress: StateFlow<List<CodingProgress>> = repository.allCodingProgress
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val fitnessStats: StateFlow<FitnessStats?> = repository.fitnessStats
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val levelState: StateFlow<UserLevelState?> = repository.levelState
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val chatMessages: StateFlow<List<AssistantChatMessage>> = repository.chatMessages
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // --- NAVIGATION LOGIC ---
    private val _currentScreenIndex = MutableStateFlow(0)
    val currentScreenIndex: StateFlow<Int> = _currentScreenIndex.asStateFlow()

    fun navigateToScreen(index: Int) {
        _currentScreenIndex.value = index
    }

    // --- POMODORO TIMER LOGIC ---
    private val _timerSecondsLeft = MutableStateFlow(1500) // 25 Minutes default
    val timerSecondsLeft: StateFlow<Int> = _timerSecondsLeft.asStateFlow()

    private val _timerIsRunning = MutableStateFlow(false)
    val timerIsRunning: StateFlow<Boolean> = _timerIsRunning.asStateFlow()

    private val _timerPresetType = MutableStateFlow("Pomodoro") // Pomodoro, Deep Work, Custom
    val timerPresetType: StateFlow<String> = _timerPresetType.asStateFlow()

    private val _selectedAmbientSound = MutableStateFlow("None") // None, Study, Coding, Rain
    val selectedAmbientSound: StateFlow<String> = _selectedAmbientSound.asStateFlow()

    private var timerJob: Job? = null

    fun selectPreset(type: String) {
        _timerPresetType.value = type
        when (type) {
            "Pomodoro" -> _timerSecondsLeft.value = 1500 // 25:00
            "Deep Work" -> _timerSecondsLeft.value = 3000 // 50:00
            "Custom" -> _timerSecondsLeft.value = 600 // 10:00 default custom
        }
        pauseTimer()
    }

    fun selectAmbientSound(sound: String) {
        _selectedAmbientSound.value = sound
    }

    fun startTimer() {
        if (_timerIsRunning.value) return
        _timerIsRunning.value = true
        timerJob = viewModelScope.launch(Dispatchers.Default) {
            while (_timerSecondsLeft.value > 0 && _timerIsRunning.value) {
                delay(1000)
                _timerSecondsLeft.value -= 1
            }
            if (_timerSecondsLeft.value == 0) {
                _timerIsRunning.value = false
                // Add Focus session completed rewards in database!
                levelState.value?.let { state ->
                    val updatedState = state.copy(
                        xp = (state.xp + 150).let { if (it >= 3000) it - 3000 else it },
                        level = if (state.xp + 150 >= 3000) state.level + 1 else state.level,
                        focusHours = state.focusHours + 0.42f // add focus time
                    )
                    repository.updateLevelState(updatedState)
                }
            }
        }
    }

    fun pauseTimer() {
        _timerIsRunning.value = false
        timerJob?.cancel()
    }

    fun resetTimer() {
        pauseTimer()
        selectPreset(_timerPresetType.value)
    }

    // --- TIMETABLE TASKS LOGIC ---
    fun addTask(time: String, title: String, category: String) {
        viewModelScope.launch {
            repository.insertTask(
                TimetableTask(time = time, title = title, completed = false, category = category)
            )
        }
    }

    fun toggleTask(id: Int, completed: Boolean) {
        viewModelScope.launch {
            repository.updateTaskStatus(id, completed)
            // Reward or adjust XP/Streak based on activity!
            levelState.value?.let { state ->
                val xpReward = if (completed) 50 else -30
                var newXp = state.xp + xpReward
                var newLevel = state.level
                if (newXp >= 3000) {
                    newXp -= 3000
                    newLevel++
                } else if (newXp < 0) {
                    newXp = 0
                }
                repository.updateLevelState(state.copy(xp = newXp, level = newLevel))
            }
        }
    }

    fun deleteTask(id: Int) {
        viewModelScope.launch {
            repository.deleteTask(id)
        }
    }

    // --- PROGRESS LOGIC ---
    fun updateSubjectProgress(name: String, percentage: Int) {
        viewModelScope.launch {
            repository.updateSubjectProgress(SubjectProgress(name, percentage))
        }
    }

    fun updateCodingProgress(name: String, percentage: Int) {
        viewModelScope.launch {
            repository.updateCodingProgress(CodingProgress(name, percentage))
        }
    }

    // --- FITNESS STATS LOGIC ---
    fun addWaterCup() {
        viewModelScope.launch {
            fitnessStats.value?.let { stats ->
                repository.updateFitnessStats(stats.copy(waterIntakeL = stats.waterIntakeL + 0.25f))
            }
        }
    }

    fun adjustWeight(kg: Float) {
        viewModelScope.launch {
            fitnessStats.value?.let { stats ->
                repository.updateFitnessStats(stats.copy(weightKg = kg))
            }
        }
    }

    fun adjustCalories(cal: Int) {
        viewModelScope.launch {
            fitnessStats.value?.let { stats ->
                repository.updateFitnessStats(stats.copy(calories = stats.calories + cal))
            }
        }
    }

    // --- CHAT AI LOGIC ---
    private val _isGeneratingResponse = MutableStateFlow(false)
    val isGeneratingResponse: StateFlow<Boolean> = _isGeneratingResponse.asStateFlow()

    fun askQuestion(text: String) {
        if (text.isBlank() || _isGeneratingResponse.value) return

        viewModelScope.launch {
            // 1. Save user query in chat history
            repository.insertChatMessage(AssistantChatMessage(message = text, isUser = true))
            _isGeneratingResponse.value = true

            // 2. Load context instructions for study planning & tracking from DB to build a prompt
            val contextPrompt = buildString {
                append("You are Vayu AI, an elite discipline & learning companion.\n")
                append("Acknowledge user's target: current user level is Warrior Level 7, UPSC Study status (Polity 75%, History 60%, Geography 80%), Coding Skills (Python 75%, DSA 60%).\n")
                append("Provide hyper-practical, highly encouraging, and disciplined feedback/suggestions.\n")
                append("User query: ")
                append(text)
            }

            // 3. Perform REST api call via Retrofit
            val apiResponse = withContext(Dispatchers.IO) {
                try {
                    val key = BuildConfig.GEMINI_API_KEY
                    if (key == "MY_GEMINI_API_KEY" || key.isEmpty()) {
                        "API Key missing. Please set your actual GEMINI_API_KEY in the Secrets panel in AI Studio"
                    } else {
                        val request = GeminiRequest(
                            contents = listOf(
                                Content(parts = listOf(Part(text = contextPrompt)))
                            ),
                            systemInstruction = Content(parts = listOf(Part("Respond in clear, brief, inspiring markdown format. Keep it concise (1-2 sections, under 150 words).")))
                        )
                        val response = RetrofitClient.service.generateContent(key, request)
                        response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text
                            ?: "No response received. Enhance your streak to retry."
                    }
                } catch (e: Exception) {
                    "Error connecting to Vayu Core Neural Interface: ${e.localizedMessage}"
                }
            }

            // 4. Save response in chat history
            repository.insertChatMessage(AssistantChatMessage(message = apiResponse, isUser = false))
            _isGeneratingResponse.value = false
        }
    }

    fun clearChat() {
        viewModelScope.launch {
            repository.clearChatHistory()
            // insert initial greeting
            repository.insertChatMessage(
                AssistantChatMessage(
                    message = "System reset complete. I am Vayu AI. Initiate instruction.",
                    isUser = false
                )
            )
        }
    }
}
