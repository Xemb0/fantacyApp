package com.autobot.chromium
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

data class TabState(val url: String)

class BrowserViewModel : ViewModel() {
    var tabs = mutableListOf<TabState>()
        private set

    var selectedTabIndex by mutableStateOf(0)

    init {
        // Add an initial tab with a default URL
        addTab("http://google.com")
    }

    fun addTab(url: String) {
        tabs.add(TabState(url))
        selectedTabIndex = tabs.size - 1 // Select the new tab
    }

    fun selectTab(index: Int) {
        if (index in tabs.indices) {
            selectedTabIndex = index
        }
    }

    fun getCurrentTabUrl(): String {
        return if (tabs.isNotEmpty()) {
            tabs[selectedTabIndex].url
        } else {
            "" // Return an empty string if no tabs are present
        }
    }

    fun updateTabUrl(index: Int, url: String) {
        if (index in tabs.indices) {
            tabs[index] = TabState(url)
        }
    }

    fun closeTab(index: Int) {
        if (index in tabs.indices) {
            tabs.removeAt(index) // Remove the tab from the list
            // Update selected tab index
            if (tabs.isEmpty()) {
                selectedTabIndex = 0 // Reset to first tab if empty
            } else if (selectedTabIndex >= tabs.size) {
                selectedTabIndex = tabs.size - 1 // Adjust if the last tab is closed
            }
        }
    }

}
