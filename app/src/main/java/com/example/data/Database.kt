package com.example.data

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

// --- 1. ENTITIES ---

@Entity(tableName = "timetable_tasks")
data class TimetableTask(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val time: String,
    val title: String,
    val completed: Boolean,
    val category: String = "General"
)

@Entity(tableName = "subject_progress")
data class SubjectProgress(
    @PrimaryKey val name: String,
    val progressPercent: Int
)

@Entity(tableName = "coding_progress")
data class CodingProgress(
    @PrimaryKey val name: String,
    val progressPercent: Int
)

@Entity(tableName = "fitness_stats")
data class FitnessStats(
    @PrimaryKey val id: Int = 1,
    val runningKm: Float,
    val workoutHours: Float,
    val calories: Int,
    val weightKg: Float,
    val waterIntakeL: Float,
    val sleepHours: Float
)

@Entity(tableName = "user_level_state")
data class UserLevelState(
    @PrimaryKey val id: Int = 1,
    val streakCount: Int,
    val xp: Int,
    val level: Int,
    val codingStreakCount: Int,
    val leetcodeSolvedCount: Int,
    val focusHours: Float
)

@Entity(tableName = "assistant_chat_messages")
data class AssistantChatMessage(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val message: String,
    val isUser: Boolean,
    val timestamp: Long = System.currentTimeMillis()
)

// --- 2. DAOS ---

@Dao
interface TimetableTaskDao {
    @Query("SELECT * FROM timetable_tasks ORDER BY id ASC")
    fun getAllTasks(): Flow<List<TimetableTask>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TimetableTask)

    @Query("UPDATE timetable_tasks SET completed = :completed WHERE id = :id")
    suspend fun updateTaskStatus(id: Int, completed: Boolean)

    @Query("DELETE FROM timetable_tasks WHERE id = :id")
    suspend fun deleteTask(id: Int)
}

@Dao
interface SubjectProgressDao {
    @Query("SELECT * FROM subject_progress")
    fun getAllProgress(): Flow<List<SubjectProgress>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProgress(progress: SubjectProgress)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<SubjectProgress>)
}

@Dao
interface CodingProgressDao {
    @Query("SELECT * FROM coding_progress")
    fun getAllProgress(): Flow<List<CodingProgress>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProgress(progress: CodingProgress)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<CodingProgress>)
}

@Dao
interface FitnessStatsDao {
    @Query("SELECT * FROM fitness_stats WHERE id = 1")
    fun getStats(): Flow<FitnessStats?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateStats(stats: FitnessStats)
}

@Dao
interface UserLevelStateDao {
    @Query("SELECT * FROM user_level_state WHERE id = 1")
    fun getLevelState(): Flow<UserLevelState?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateState(state: UserLevelState)
}

@Dao
interface AssistantChatMessageDao {
    @Query("SELECT * FROM assistant_chat_messages ORDER BY timestamp ASC")
    fun getAllMessages(): Flow<List<AssistantChatMessage>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: AssistantChatMessage)

    @Query("DELETE FROM assistant_chat_messages")
    suspend fun clearHistory()
}

// --- 3. DATABASE ---

@Database(
    entities = [
        TimetableTask::class,
        SubjectProgress::class,
        CodingProgress::class,
        FitnessStats::class,
        UserLevelState::class,
        AssistantChatMessage::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TimetableTaskDao
    abstract fun subjectProgressDao(): SubjectProgressDao
    abstract fun codingProgressDao(): CodingProgressDao
    abstract fun fitnessStatsDao(): FitnessStatsDao
    abstract fun userLevelStateDao(): UserLevelStateDao
    abstract fun chatMessageDao(): AssistantChatMessageDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "vayu_tracker_database"
                )
                .addCallback(DatabasePrepopulationCallback(context))
                .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class DatabasePrepopulationCallback(private val context: Context) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            CoroutineScope(Dispatchers.IO).launch {
                val database = getDatabase(context)
                
                // Prepopulate Tasks
                val defaultTasks = listOf(
                    TimetableTask(time = "5:00 AM", title = "Wake up", completed = true, category = "General"),
                    TimetableTask(time = "5:30 AM", title = "Running", completed = true, category = "Workout"),
                    TimetableTask(time = "9:00 AM", title = "UPSC Study", completed = true, category = "Study"),
                    TimetableTask(time = "11:00 AM", title = "Python Development", completed = false, category = "Coding"),
                    TimetableTask(time = "6:00 PM", title = "Gym Workout", completed = false, category = "Workout")
                )
                defaultTasks.forEach { database.taskDao().insertTask(it) }

                // Prepopulate Subject Progress (UPSC Tracker)
                val defaultSubjects = listOf(
                    SubjectProgress("Polity", 75),
                    SubjectProgress("History", 60),
                    SubjectProgress("Geography", 80),
                    SubjectProgress("Economy", 55),
                    SubjectProgress("Ethics", 70),
                    SubjectProgress("Environment", 65),
                    SubjectProgress("Current Affairs", 90)
                )
                database.subjectProgressDao().insertAll(defaultSubjects)

                // Prepopulate Coding Progress
                val defaultCoding = listOf(
                    CodingProgress("Python", 75),
                    CodingProgress("DSA", 60),
                    CodingProgress("AI/ML", 70),
                    CodingProgress("Web Dev", 50),
                    CodingProgress("Projects", 80)
                )
                database.codingProgressDao().insertAll(defaultCoding)

                // Prepopulate Fitness Stats
                database.fitnessStatsDao().insertOrUpdateStats(
                    FitnessStats(
                        runningKm = 4.2f,
                        workoutHours = 1.33f, // 1h 20m
                        calories = 560,
                        weightKg = 72.5f,
                        waterIntakeL = 2.5f,
                        sleepHours = 7.5f // 7h 30m
                    )
                )

                // Prepopulate User Level State
                database.userLevelStateDao().insertOrUpdateState(
                    UserLevelState(
                        streakCount = 12,
                        xp = 2450,
                        level = 7,
                        codingStreakCount = 15,
                        leetcodeSolvedCount = 1530,
                        focusHours = 2.75f // 2h 45m
                    )
                )

                // Prepopulate chatbot message
                database.chatMessageDao().insertMessage(
                    AssistantChatMessage(
                        message = "Welcome, Warrior. I am Vayu AI, your personal discipline assistant. Ask me to draft a custom study schedule, suggest revision topics, or resolve concept difficulties.",
                        isUser = false
                    )
                )
            }
        }
    }
}
