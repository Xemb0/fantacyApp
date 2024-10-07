package com.autobot.chromium.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

// TabDao.kt


// TabDao.kt
@Dao
interface TabDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(tab: TabData)

    @Query("SELECT * FROM tabs")
    suspend fun getAllTabs(): List<TabData>

    @Delete
    suspend fun delete(tab: TabData)
}
