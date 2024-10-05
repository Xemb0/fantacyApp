    package com.autobot.chromium.database

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import java.util.UUID

    class BrowserViewModel : ViewModel() {
        private val _tabs = mutableStateListOf<BrowserTab>()
        val tabs: List<BrowserTab> get() = _tabs

        init {
            addTab("https://yourhomepage.com")
        }

        fun addTab(url: String = "https://yourhomepage.com") {
            _tabs.add(BrowserTab(
                id = UUID.randomUUID().toString(),
                url = url
            ))
        }

        fun removeTab(tabId: String) {
            _tabs.removeAll { it.id == tabId }
        }

        fun updateUrl(tabId: String, newUrl: String) {
            _tabs.find { it.id == tabId }?.let { tab ->
                tab.url = newUrl
            }
        }
    }

