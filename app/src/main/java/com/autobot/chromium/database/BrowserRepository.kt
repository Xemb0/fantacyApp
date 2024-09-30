package com.autobot.chromium.database

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BrowserRepository() {
    @Inject
    lateinit var bookmarkDao: BookmarkDao

    suspend fun insertBookmark(url: String) {
        bookmarkDao.insert(Bookmark(url = url))
    }

    suspend fun getAllBookmarks(): List<Bookmark> {
        return bookmarkDao.getAllBookmarks()
    }

    fun getInitialTabs(): List<String> {
        return listOf("https://www.google.com") // Starting tab
    }
}