package com.autobot.chromium.database

import android.webkit.WebView
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class WebBrowserViewModel : ViewModel() {
    var tabs = mutableStateListOf<TabData>()
    private val webViewMap = mutableMapOf<Int, WebViewHolder>()

    init {
        // Initialize with one tab
        addTab("Tab 1", "https://www.google.com")
    }

    fun addTab(name: String, url: String) {
        tabs.add(TabData(name, Color(Random.nextFloat(), Random.nextFloat(), Random.nextFloat()), url))
    }

    fun getWebViewHolder(index: Int): WebViewHolder {
        return webViewMap.getOrPut(index) { WebViewHolder() }
    }

    fun updateWebViewHolder(index: Int, webViewHolder: WebViewHolder) {
        webViewMap[index] = webViewHolder
    }
}
class WebViewHolder(var webView: WebView? = null, var currentUrl: String? = null)
data class TabData(val name: String, val color: Color, val url: String)

