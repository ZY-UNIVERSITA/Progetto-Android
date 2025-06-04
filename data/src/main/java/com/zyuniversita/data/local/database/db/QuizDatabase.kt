package com.zyuniversita.data.local.database.db

import android.content.Context
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
//                    .setQueryCallback({ sqlQuery, bindArgs ->
//                    Log.d("SQL_QUERY", "Query: $sqlQuery, Args: $bindArgs")
//                }, Executors.newSingleThreadExecutor())

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

                                // Insert initial words for HSK1 level
                                db.execSQL("INSERT INTO word (wordId, levelCode, languageCode, word, meaning, transliteration) VALUES (1, 'HSK1', 'ch', '家', 'Home', 'Jiā')")
                                db.execSQL("INSERT INTO word (wordId, levelCode, languageCode, word, meaning, transliteration) VALUES (2, 'HSK1', 'ch', '门', 'Door', 'Mén')")
                                db.execSQL("INSERT INTO word (wordId, levelCode, languageCode, word, meaning, transliteration) VALUES (3, 'HSK1', 'ch', '耳', 'Ear', 'Ěr')")
                                db.execSQL("INSERT INTO word (wordId, levelCode, languageCode, word, meaning, transliteration) VALUES (4, 'HSK1', 'ch', '马', 'Horse', 'Mǎ')")
                                db.execSQL("INSERT INTO word (wordId, levelCode, languageCode, word, meaning, transliteration) VALUES (5, 'HSK1', 'ch', '猫', 'Cat', 'Māo')")
                                db.execSQL("INSERT INTO word (wordId, levelCode, languageCode, word, meaning, transliteration) VALUES (6, 'HSK1', 'ch', '狗', 'Dog', 'Gǒu')")
                                db.execSQL("INSERT INTO word (wordId, levelCode, languageCode, word, meaning, transliteration) VALUES (7, 'HSK1', 'ch', '毛', 'Hair', 'Máo')")
                                db.execSQL("INSERT INTO word (wordId, levelCode, languageCode, word, meaning, transliteration) VALUES (8, 'HSK1', 'ch', '学校', 'School', 'Xuéxiào')")
                                db.execSQL("INSERT INTO word (wordId, levelCode, languageCode, word, meaning, transliteration) VALUES (9, 'HSK1', 'ch', '熊猫', 'Panda', 'Xióngmāo')")
                                db.execSQL("INSERT INTO word (wordId, levelCode, languageCode, word, meaning, transliteration) VALUES (10, 'HSK1', 'ch', '鱼', 'Fish', 'Yú')")

                                // Insert initial words for HSK2 level
                                db.execSQL("INSERT INTO word (wordId, levelCode, languageCode, word, meaning, transliteration) VALUES (11, 'HSK2', 'ch', '面', 'Noodle', 'Miàn')")
                                db.execSQL("INSERT INTO word (wordId, levelCode, languageCode, word, meaning, transliteration) VALUES (12, 'HSK2', 'ch', '线', 'Thread', 'Xiàn')")
                                db.execSQL("INSERT INTO word (wordId, levelCode, languageCode, word, meaning, transliteration) VALUES (13, 'HSK2', 'ch', '火', 'Fire', 'Huǒ')")
                                db.execSQL("INSERT INTO word (wordId, levelCode, languageCode, word, meaning, transliteration) VALUES (14, 'HSK2', 'ch', '草', 'Grass', 'Cǎo')")
                                db.execSQL("INSERT INTO word (wordId, levelCode, languageCode, word, meaning, transliteration) VALUES (15, 'HSK2', 'ch', '鸟', 'Bird', 'Niǎo')")

                                // Insert initial words for Japanese levels
                                db.execSQL("INSERT INTO word (wordId, levelCode, languageCode, word, meaning, transliteration) VALUES (16, 'N1', 'jp', '私', 'I', 'Watashi')")
                                db.execSQL("INSERT INTO word (wordId, levelCode, languageCode, word, meaning, transliteration) VALUES (17, 'N2', 'jp', '教室', 'Classroom', 'Kyōshitsu')")
                                db.execSQL("INSERT INTO word (wordId, levelCode, languageCode, word, meaning, transliteration) VALUES (18, 'N3', 'jp', '東京', 'Tokyo', 'Tōkyō')")
                                db.execSQL("INSERT INTO word (wordId, levelCode, languageCode, word, meaning, transliteration) VALUES (19, 'N4', 'jp', '京都', 'Kyoto', 'Kyōto')")

                                // Insert initial words for Korean level
                                db.execSQL("INSERT INTO word (wordId, levelCode, languageCode, word, meaning, transliteration) VALUES (20, 'TOPIK1', 'kr', '베개', 'Pillow', 'Begae')")
                                db.execSQL("INSERT INTO word (wordId, levelCode, languageCode, word, meaning, transliteration) VALUES (21, 'TOPIK1', 'kr', '책', 'Book', 'Chaek')")
                                db.execSQL("INSERT INTO word (wordId, levelCode, languageCode, word, meaning, transliteration) VALUES (22, 'TOPIK1', 'kr', '의자', 'Chair', 'Uija')")
                                db.execSQL("INSERT INTO word (wordId, levelCode, languageCode, word, meaning, transliteration) VALUES (23, 'TOPIK1', 'kr', '물', 'Water', 'Mul')")
                            }
                        }
                    }).build()
                INSTANCE = instance
                instance
            }
        }
    }
}