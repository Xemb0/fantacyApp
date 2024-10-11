package com.autobot.chromium.database.di

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.autobot.chromium.database.UserDao
import com.autobot.chromium.database.models.UserData

@Database(entities = [UserData::class], version = 1, exportSchema = false)
abstract class ChromiumDataBase : RoomDatabase() {

    companion object {
        fun getInstance(context: Context) =
            Room.databaseBuilder(context, ChromiumDataBase::class.java, "Database")
                .fallbackToDestructiveMigration()
                .build()
    }

    abstract fun userDao(): UserDao

}