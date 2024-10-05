package com.autobot.chromium.ui

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.autobot.chromium.database.TabData
import com.autobot.chromium.database.WebBrowserViewModel
import com.autobot.chromium.theme.MyAppThemeComposable


@Composable
fun HomePage(viewModel: WebBrowserViewModel) {
    var selectedTabIndex by remember { mutableStateOf(0) }

    Column(modifier = Modifier.fillMaxSize()) {
        // Row for displaying tabs
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            viewModel.tabs.forEachIndexed { index, tab ->
                TabItem(
                    tabData = tab,
                    isSelected = index == selectedTabIndex,
                    onClick = { selectedTabIndex = index },
                    modifier = Modifier.weight(1f) // Ensure even distribution of tab width
                )
            }
        }

        // Content area for the selected tab
        Box(
            modifier = Modifier
                .weight(1f) // Allow the content area to take remaining space
                .background(viewModel.tabs[selectedTabIndex].color),
            contentAlignment = Alignment.Center
        ) {
            val webViewHolder = viewModel.getWebViewHolder(selectedTabIndex)
            WebViewContainer(
                url = webViewHolder.currentUrl ?: viewModel.tabs[selectedTabIndex].url,
                onWebViewCreated = { webView ->
                    webViewHolder.webView = webView
                },
                onUrlChanged = { url ->
                    webViewHolder.currentUrl = url
                }
            )
        }

        // Bottom button bar
        BottomAppBar(
            content = {
                Button(
                    onClick = {
                        viewModel.addTab("Tab ${viewModel.tabs.size + 1}", "https://www.google.com") // Add URL for the new tab
                        selectedTabIndex = viewModel.tabs.size - 1  // Automatically select the new tab
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(text = "Add Tab")
                }
            }
        )
    }
}

@Composable
fun TabItem(tabData: TabData, isSelected: Boolean, onClick: () -> Unit, modifier: Modifier) {
    val backgroundColor = if (isSelected) Color.Gray else Color.LightGray

    Card(
        modifier = modifier
            .padding(end = 8.dp)
            .clickable { onClick() }
            .background(backgroundColor),
    ) {
        BasicText(
            text = tabData.name,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}

@Composable
fun WebViewContainer(url: String, onWebViewCreated: (WebView) -> Unit, onUrlChanged: (String) -> Unit) {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                webViewClient = WebViewClient()
                loadUrl(url)
                onWebViewCreated(this)
                setWebViewClient(WebViewClient())
            }
        },
        update = { webView ->
            if (url != webView.url) {
                webView.loadUrl(url) // Load the saved URL
            }

            webView.webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    url?.let { onUrlChanged(it) } // Update the current URL
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyAppThemeComposable {
    }
}