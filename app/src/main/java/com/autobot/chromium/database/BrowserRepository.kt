package com.autobot.chromium.database

import javax.inject.Inject


// TabRepositoryImpl.kt

    class BrowserRepository @Inject constructor(private val tabDao: TabDao) : TabRepository {
        override suspend fun addTab(tab: TabData) {
            tabDao.insert(tab)
        }

        override suspend fun getAllTabs(): List<TabData> {
            return tabDao.getAllTabs()
        }

        override suspend fun deleteTab(tab: TabData) {
            tabDao.delete(tab)
        }
    }
