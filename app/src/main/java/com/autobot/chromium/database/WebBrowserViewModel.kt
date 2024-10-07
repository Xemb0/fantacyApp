package com.autobot.chromium.database

import android.webkit.WebView
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WebBrowserViewModel @Inject constructor(private val repository: TabRepository) : ViewModel() {
    var tabs = mutableStateListOf<TabData>()
    private val webViewMap = mutableMapOf<Int, WebViewHolder>()
    init {
        viewModelScope.launch {
            tabs.addAll(repository.getAllTabs())
        }
    }

    fun addTab(name: String, url: String) {
        val tab = TabData(name = name, url = url)
        viewModelScope.launch {
            repository.addTab(tab)
            tabs.add(tab)
        }
    }

    fun getWebViewHolder(index: Int): WebViewHolder {
        return webViewMap.getOrPut(index) { WebViewHolder() }
    }

    fun updateWebViewHolder(index: Int, webViewHolder: WebViewHolder) {
        webViewMap[index] = webViewHolder
    }
}
class WebViewHolder(var webView: WebView? = null, var currentUrl: String? = null)

