package com.example.data

import kotlinx.coroutines.flow.Flow

class VayuRepository(private val db: AppDatabase) {

    // Tasks API
    val allTasks: Flow<List<TimetableTask>> = db.taskDao().getAllTasks()
    suspend fun insertTask(task: TimetableTask) = db.taskDao().insertTask(task)
    suspend fun updateTaskStatus(id: Int, completed: Boolean) = db.taskDao().updateTaskStatus(id, completed)
    suspend fun deleteTask(id: Int) = db.taskDao().deleteTask(id)

    // UPSC Progress API
    val allSubjectProgress: Flow<List<SubjectProgress>> = db.subjectProgressDao().getAllProgress()
    suspend fun updateSubjectProgress(progress: SubjectProgress) = db.subjectProgressDao().insertProgress(progress)

    // Coding Progress API
    val allCodingProgress: Flow<List<CodingProgress>> = db.codingProgressDao().getAllProgress()
    suspend fun updateCodingProgress(progress: CodingProgress) = db.codingProgressDao().insertProgress(progress)

    // Fitness API
    val fitnessStats: Flow<FitnessStats?> = db.fitnessStatsDao().getStats()
    suspend fun updateFitnessStats(stats: FitnessStats) = db.fitnessStatsDao().insertOrUpdateStats(stats)

    // User State API
    val levelState: Flow<UserLevelState?> = db.userLevelStateDao().getLevelState()
    suspend fun updateLevelState(state: UserLevelState) = db.userLevelStateDao().insertOrUpdateState(state)

    // Assistant Chat API
    val chatMessages: Flow<List<AssistantChatMessage>> = db.chatMessageDao().getAllMessages()
    suspend fun insertChatMessage(message: AssistantChatMessage) = db.chatMessageDao().insertMessage(message)
    suspend fun clearChatHistory() = db.chatMessageDao().clearHistory()
}
