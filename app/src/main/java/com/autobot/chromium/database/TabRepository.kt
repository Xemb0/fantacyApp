package com.autobot.chromium.database

interface TabRepository {
    suspend fun addTab(tab: TabData)
    suspend fun getAllTabs(): List<TabData>
    suspend fun deleteTab(tab: TabData)
}