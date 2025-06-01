package com.zyuniversita.data.local.database.db

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.zyuniversita.data.local.database.dao.LanguageDao
import com.zyuniversita.data.local.database.dao.LevelDao
import com.zyuniversita.data.local.database.dao.UserInfoDao
import com.zyuniversita.data.local.database.dao.UserQuizPerformanceDao
import com.zyuniversita.data.local.database.dao.WordDao
import com.zyuniversita.data.local.database.dao.WordUserDataDao
import com.zyuniversita.data.local.database.db.QuizDatabase.Companion.getInstance
import com.zyuniversita.data.local.database.entities.LanguageEntity
import com.zyuniversita.data.local.database.entities.LevelEntity
import com.zyuniversita.data.local.database.entities.UserInfoEntity
import com.zyuniversita.data.local.database.entities.UserQuizPerformanceEntity
import com.zyuniversita.data.local.database.entities.WordEntity
import com.zyuniversita.data.local.database.entities.WordUserDataEntity
import java.util.concurrent.Executors

/**
 * The Room database for the quiz application.
 *
 * This abstract class defines the database configuration and serves as the main access point to the underlying connection.
 * In this database, the following entities are managed:
 * - [LanguageEntity]
 * - [LevelEntity]
 * - [WordEntity]
 * - [WordUserDataEntity]
 * - [UserInfoEntity]
 * - [UserQuizPerformanceEntity]
 *
 * The database is defined with version 1 and schema export is disabled.
 *
 * The abstract functions provide access to the corresponding Data Access Objects (DAOs):
 * - [languageDao] provides operations related to language data.
 * - [levelDao] provides operations related to level data.
 * - [wordDao] provides operations related to word data.
 * - [wordUserDataDao] provides operations related to user-specific word data.
 * - [userInfoDao] provides operations related to the user information.
 * - [userQuizPerformanceDao] provides operations related to user quiz performance data.
 *
 * A singleton instance of [QuizDatabase] is provided via the [getInstance] method.
 * The database is built using Room's [databaseBuilder] and includes a callback that pre-populates the tables
 * with initial language, level, and word data on its creation.
 */
@Database(
    entities = [
        LanguageEntity::class,
        LevelEntity::class,
        WordEntity::class,
        WordUserDataEntity::class,
        UserInfoEntity::class,
        UserQuizPerformanceEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class QuizDatabase : RoomDatabase() {

    /** Returns an instance of [LanguageDao] for language-related database operations. */
    abstract fun languageDao(): LanguageDao

    /** Returns an instance of [LevelDao] for level-related database operations. */
    abstract fun levelDao(): LevelDao

    /** Returns an instance of [WordDao] for word-related database operations. */
    abstract fun wordDao(): WordDao

    /** Returns an instance of [WordUserDataDao] for user-specific word data operations. */
    abstract fun wordUserDataDao(): WordUserDataDao

    /** Returns an instance of [UserInfoDao] for user information related operations. */
    abstract fun userInfoDao(): UserInfoDao

    /** Returns an instance of [UserQuizPerformanceDao] for user quiz performance related operations. */
    abstract fun userQuizPerformanceDao(): UserQuizPerformanceDao

    companion object {
        @Volatile
        private var INSTANCE: QuizDatabase? = null

        /**
         * Returns the singleton instance of [QuizDatabase].
         *
         * If the database has not been created yet, it will be built using Room's [Room.databaseBuilder].
         * The builder uses a custom callback to insert initial data into the database once it is created.
         *
         * @param context The application context.
         * @return The singleton instance of [QuizDatabase].
         */
        fun getInstance(context: Context): QuizDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    QuizDatabase::class.java,
                    "quiz_database"
                )
                    .setQueryCallback({ sqlQuery, bindArgs ->
                    Log.d("SQL_QUERY", "Query: $sqlQuery, Args: $bindArgs")
                }, Executors.newSingleThreadExecutor())

                    .fallbackToDestructiveMigration(false)
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            Executors.newSingleThreadExecutor().execute {
                                // Insert initial languages
                                db.execSQL("INSERT INTO language (code, name) VALUES ('ch', 'Chinese')")
                                db.execSQL("INSERT INTO language (code, name) VALUES ('jp', 'Japanese')")
                                db.execSQL("INSERT INTO language (code, name) VALUES ('kr', 'Korean')")
                                
                                // Insert initial levels for Chinese and Japanese, and Korean
                                db.execSQL("INSERT INTO level (code, languageCode) VALUES ('HSK1', 'ch')")
                                db.execSQL("INSERT INTO level (code, languageCode) VALUES ('HSK2', 'ch')")
                                db.execSQL("INSERT INTO level (code, languageCode) VALUES ('HSK3', 'ch')")
                                db.execSQL("INSERT INTO level (code, languageCode) VALUES ('N1', 'jp')")
                                db.execSQL("INSERT INTO level (code, languageCode) VALUES ('N2', 'jp')")
                                db.execSQL("INSERT INTO level (code, languageCode) VALUES ('N3', 'jp')")
                                db.execSQL("INSERT INTO level (code, languageCode) VALUES ('N4', 'jp')")
                                db.execSQL("INSERT INTO level (code, languageCode) VALUES ('TOPIK1', 'kr')")
                                
                                // Insert initial words for KSH1 levels
                                db.execSQL("INSERT INTO word (wordId, levelCode, languageCode, word, meaning) VALUES (1, 'HSK1', 'ch', '家', 'Home')")
                                db.execSQL("INSERT INTO word (wordId, levelCode, languageCode, word, meaning) VALUES (2, 'HSK1', 'ch', '门', 'Door')")
                                db.execSQL("INSERT INTO word (wordId, levelCode, languageCode, word, meaning) VALUES (3, 'HSK1', 'ch', '耳', 'Ear')")
                                db.execSQL("INSERT INTO word (wordId, levelCode, languageCode, word, meaning) VALUES (4, 'HSK1', 'ch', '马', 'Horse')")
                                db.execSQL("INSERT INTO word (wordId, levelCode, languageCode, word, meaning) VALUES (5, 'HSK1', 'ch', '猫', 'Cat')")
                                db.execSQL("INSERT INTO word (wordId, levelCode, languageCode, word, meaning) VALUES (6, 'HSK1', 'ch', '狗', 'Dog')")
                                db.execSQL("INSERT INTO word (wordId, levelCode, languageCode, word, meaning) VALUES (7, 'HSK1', 'ch', '毛', 'Hair')")
                                db.execSQL("INSERT INTO word (wordId, levelCode, languageCode, word, meaning) VALUES (8, 'HSK1', 'ch', '学校', 'School')")
                                db.execSQL("INSERT INTO word (wordId, levelCode, languageCode, word, meaning) VALUES (9, 'HSK1', 'ch', '熊猫', 'Panda')")
                                db.execSQL("INSERT INTO word (wordId, levelCode, languageCode, word, meaning) VALUES (10, 'HSK1', 'ch', '鱼', 'Fish')")
                                
                                // Insert initial words for HSK2 level
                                db.execSQL("INSERT INTO word (wordId, levelCode, languageCode, word, meaning) VALUES (11, 'HSK2', 'ch', '面', 'Noodle')")
                                db.execSQL("INSERT INTO word (wordId, levelCode, languageCode, word, meaning) VALUES (12, 'HSK2', 'ch', '线', 'Thread')")
                                db.execSQL("INSERT INTO word (wordId, levelCode, languageCode, word, meaning) VALUES (13, 'HSK2', 'ch', '火', 'Fire')")
                                db.execSQL("INSERT INTO word (wordId, levelCode, languageCode, word, meaning) VALUES (14, 'HSK2', 'ch', '草', 'Grass')")
                                db.execSQL("INSERT INTO word (wordId, levelCode, languageCode, word, meaning) VALUES (15, 'HSK2', 'ch', '鸟', 'Bird')")
                                
                                // Insert initial words for Japanese levels
                                db.execSQL("INSERT INTO word (wordId, levelCode, languageCode, word, meaning) VALUES (16, 'N1', 'jp', '私', 'I')")
                                db.execSQL("INSERT INTO word (wordId, levelCode, languageCode, word, meaning) VALUES (17, 'N2', 'jp', '教室', 'Classroom')")
                                db.execSQL("INSERT INTO word (wordId, levelCode, languageCode, word, meaning) VALUES (18, 'N3', 'jp', '東京', 'Tokyo')")
                                db.execSQL("INSERT INTO word (wordId, levelCode, languageCode, word, meaning) VALUES (19, 'N4', 'jp', '京都', 'Kyoto')")
                                
                                // Insert initial word for Korean level
                                db.execSQL("INSERT INTO word (wordId, levelCode, languageCode, word, meaning) VALUES (20, 'TOPIK1', 'kr', '베개', 'Pillow')")
                            }
                        }
                    }).build()
                INSTANCE = instance
                instance
            }
        }
    }
}