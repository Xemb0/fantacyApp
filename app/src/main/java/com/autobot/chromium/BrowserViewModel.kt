package com.autobot.chromium

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class BrowserViewModel : ViewModel() {
    // Mutable state list to hold the URLs of the tabs
    private val _tabs = mutableStateListOf("https://www.google.com")
    val tabs: List<String> get() = _tabs

    // Variable to keep track of the currently selected tab index
    var selectedTabIndex by mutableStateOf(0)

    // Function to add a new tab
    fun addTab(url: String) {
        _tabs.add(url)
        selectedTabIndex = _tabs.size - 1 // Select the newly added tab
    }

    // Function to update an existing tab
    fun updateTab(index: Int, url: String) {
        if (index in _tabs.indices) {
            _tabs[index] = url
        }
    }

    // Function to select a tab
    fun selectTab(index: Int) {
        if (index in _tabs.indices) {
            selectedTabIndex = index
        }
    }
}