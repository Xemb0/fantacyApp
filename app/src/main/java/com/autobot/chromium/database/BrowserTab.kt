package com.autobot.chromium.database

import android.os.Bundle

// BrowserTab data class to store WebView state and URL
data class BrowserTab(
    val id: String,
    var url: String,
    var webViewState: Bundle? = null
)