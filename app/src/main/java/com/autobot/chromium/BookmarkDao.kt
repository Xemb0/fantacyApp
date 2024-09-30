package com.autobot.chromium

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BookmarkDao {
    @Insert
    suspend fun insert(bookmark: Bookmark)

    @Query("SELECT * FROM bookmarks")
    suspend fun getAllBookmarks(): List<Bookmark>
}