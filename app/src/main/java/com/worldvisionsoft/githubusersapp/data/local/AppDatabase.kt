package com.worldvisionsoft.githubusersapp.data.local

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.worldvisionsoft.githubusersapp.GUApplication
import com.worldvisionsoft.githubusersapp.data.local.db.UserDao
import com.worldvisionsoft.githubusersapp.data.model.User
import com.worldvisionsoft.githubusersapp.utils.Constants

@Database(version = 1, entities = [User::class], exportSchema = false)
@TypeConverters()
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase().also { INSTANCE = it }
            }

        private fun buildDatabase() =
            Room.databaseBuilder(GUApplication.getInstance(),
                AppDatabase::class.java, Constants.DB_NAME)
                // prepopulate the database after onCreate was called
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                    }
                })
                .build()
    }
}