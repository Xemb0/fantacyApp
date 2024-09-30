package com.autobot.chromium

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Bookmark::class], version = 1, exportSchema = false)
abstract class ChromiumDatabase : RoomDatabase() {

    abstract fun bookmarkDao(): BookmarkDao

    companion object {
        @Volatile
        private var INSTANCE: ChromiumDatabase? = null

        fun getDatabase(context: Context): ChromiumDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ChromiumDatabase::class.java,
                    "chromium_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}