
import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import java.util.UUID

@Composable
fun BrowserApp() {
    val tabs = remember { mutableStateListOf<BrowserTab>() }
    var selectedTabIndex by remember { mutableStateOf(0) }

    // Add initial tab (homepage)
    LaunchedEffect(Unit) {
        if (tabs.isEmpty()) {
            tabs.add(BrowserTab(id = UUID.randomUUID().toString(), url = "https://www.google.com"))
        }
    }

    // UI Layout
    Column {
        // TabRow for displaying browser tabs
        TabRow(
            selectedTabIndex = selectedTabIndex,
        ) {
            tabs.forEachIndexed { index, tab ->
                Tab(
                    selected = index == selectedTabIndex,
                    onClick = { selectedTabIndex = index },
                    text = { Text("Tab ${index + 1}") },
                    // Close button for tabs
                    icon = {
                        if (tabs.size > 1) {
                            IconButton(onClick = { tabs.removeAt(index) }) {
                                Icon(Icons.Default.Close, contentDescription = "Close Tab")
                            }
                        }
                    }
                )
            }

            // "+" button to add a new tab
            Tab(
                selected = false,
                onClick = {
                    tabs.add(
                        BrowserTab(id = UUID.randomUUID().toString(), url = "https://www.google.com")
                    )
                    selectedTabIndex = tabs.size - 1
                },
                text = { Text("+") }
            )
        }

        // Display the WebView for the selected tab
        if (tabs.isNotEmpty()) {
            WebViewContainer(tabs[selectedTabIndex])
        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewContainer(tab: BrowserTab) {
    val context = LocalContext.current
    var webViewBundle by remember { mutableStateOf(Bundle()) }

    AndroidView(
        factory = {
            WebView(context).apply {
                settings.javaScriptEnabled = true
                webViewClient = WebViewClient()

                // Restore the state if available
                if (tab.webViewState == null) {
                    loadUrl(tab.url)  // Load the URL if there's no saved state
                } else {
                    restoreState(tab.webViewState!!)  // Restore the state when available
                }
            }
        },
        update = { webView ->
            // Save the WebView state when switching tabs
            tab.webViewState = Bundle().also {
                webView.saveState(it)
            }
        },
        modifier = Modifier.fillMaxSize()
    )

    // Handle back navigation inside WebView
    BackHandler {
        tab.webViewState?.let { state ->
            val webView = WebView(context)
            webView.restoreState(state)
            if (webView.canGoBack()) {
                webView.goBack()
            }
        }
    }
}

// BrowserTab data class to store WebView state and URL
data class BrowserTab(
    val id: String,
    var url: String,
    var webViewState: Bundle? = null
)
