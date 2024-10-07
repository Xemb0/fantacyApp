package com.autobot.chromium.ui

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.autobot.chromium.R
import com.autobot.chromium.database.TabData
import com.autobot.chromium.database.WebBrowserViewModel
import com.autobot.chromium.theme.MyAppThemeColors
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(viewModel: WebBrowserViewModel = hiltViewModel()) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var currentUrl by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {
        // Row for displaying tabs
        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(top = 8.dp, start = 8.dp, end = 8.dp)
        ) {
            viewModel.tabs.forEachIndexed { index, tab ->
                TabItem(
                    tabData = tab,
                    isSelected = index == selectedTabIndex,
                    onClick = {
                        selectedTabIndex = index
                        currentUrl = tab.url // Update current URL when selecting a tab
                    },
                    modifier = Modifier.width(160.dp).padding(end = 8.dp),
                    onCloseClick = {
                        viewModel.tabs.removeAt(index)
                        // Update selected tab index after removal
                        if (viewModel.tabs.isNotEmpty()) {
                            // Ensure selectedTabIndex is valid
                            selectedTabIndex = selectedTabIndex.coerceIn(0, viewModel.tabs.size - 1)
                            currentUrl = viewModel.tabs[selectedTabIndex].url // Update current URL
                        } else {
                            // Handle case where no tabs are left
                            selectedTabIndex = -1 // Reset index to an invalid state
                            currentUrl = "Home" // Set to "Home" if no tabs remain
                        }
                    }
                )
            }
        }

        Box(
            modifier = Modifier.fillMaxWidth().weight(1f),
            contentAlignment = Alignment.Center
        ) {
            // Safely get the current webViewHolder
            val webViewHolder = if (selectedTabIndex in viewModel.tabs.indices) {
                viewModel.getWebViewHolder(selectedTabIndex)
            } else {
                null // Handle if the index is invalid
            }

            // Determine the current URL
            currentUrl = if (webViewHolder != null) {
                webViewHolder.currentUrl ?: viewModel.tabs[selectedTabIndex].url
            } else {
                "Home" // Default to Home when no valid tab
            }

            // Show the Home view if the current URL is "Home" or if there are no tabs
            if (currentUrl == "Home" || viewModel.tabs.isEmpty()) {
                BrowserHomePage {
                    // Show default view for the "Home" page
                }
            } else {
                WebViewContainer(
                    url = currentUrl,
                    onWebViewCreated = { webView ->
                        webViewHolder?.webView = webView
                    },
                    onUrlChanged = { url ->
                        webViewHolder?.currentUrl = url
                    }
                )
            }
        }

        // Bottom button bar
        SearchBar(
            onAddTab = {
                // Add a new tab with a default name and URL set to "Home"
                viewModel.addTab("Tab ${viewModel.tabs.size + 1}", "Home")
                selectedTabIndex = viewModel.tabs.size - 1 // Select the newly created tab
                currentUrl = "Home" // Set the current URL to the default for the new tab
            },
            onSearch = { query ->
                val newUrl = "https://www.google.com/search?q=$query"
                val currentTab = viewModel.getWebViewHolder(selectedTabIndex)
                currentTab?.webView?.loadUrl(newUrl)
                currentUrl = newUrl // Update current URL
            },
            onMenuClick = {
                showBottomSheet = true
            },
            onTextChange = { text ->
                currentUrl = text
                val webViewHolder = viewModel.getWebViewHolder(selectedTabIndex)
                webViewHolder?.webView?.loadUrl(currentUrl)
            },
            searchBarText = currentUrl // Display the current URL in the search bar
        )
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet = false
            },
            sheetState = sheetState,
            modifier = Modifier.background(MyAppThemeColors.current.tertiaryDark)
        ) {
            BottomSheetContent() // Display bottom sheet content
        }
    }
}
@Composable
fun TabItem(
    tabData: TabData, isSelected: Boolean, onClick: () -> Unit, modifier: Modifier, onCloseClick: () -> Unit
) {
    val backgroundColor = if (isSelected) Color.LightGray else Color.Gray

    Row(
        modifier = modifier
            .clip(
                if (isSelected) {
                    RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp) // Rounded corners for the selected tab
                } else {
                    RoundedCornerShape(8.dp) // Full rounded corners for non-selected tab
                }
            )
            .clickable { onClick() }
            .background(backgroundColor)
            .padding(vertical = 8.dp, horizontal = 8.dp), // Padding inside the tab
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically // Align content vertically to center
    ) {
        Text(
            text = tabData.url,
            modifier = Modifier
                .weight(1f) // Give the text space to fill as much as possible
                .padding(end = 8.dp),
            maxLines = 1, // Limit to 1 line to avoid overflow
            overflow = TextOverflow.Ellipsis // Ellipsis if the text is too long
        )
        if (isSelected) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close Tab",
                modifier = Modifier
                    .clickable { onCloseClick() }
                    .clip(RoundedCornerShape(24.dp)) // Adjust icon size to fit the tab nicely
                    .size(24.dp)
                    .background(Color.Red.copy(alpha = .3f))
            )
        }
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

@Composable
fun BottomSheetContent() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 4.dp, vertical = 16.dp)
            .background(
                color = MyAppThemeColors.current.tertiaryDark,
                shape = RoundedCornerShape(24.dp)
            )
            .padding(horizontal = 4.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // First Row of icons
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconColumn(imageVector = Icons.Outlined.Star, label = "Website")
            IconColumn(painter = painterResource(id = R.drawable.ic_download), label = "Download")
            IconColumn(painter = painterResource(id = R.drawable.ic_recent), label = "Recent")
            IconColumn(painter = painterResource(id = R.drawable.ic_settings), label = "Settings")
        }

        androidx.compose.material3.HorizontalDivider(
            modifier = Modifier.padding(vertical = 8.dp),
            thickness = 1.dp,
            color = MyAppThemeColors.current.myText
        )
        // Second Row of icons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconColumn(painter = painterResource(id = R.drawable.ic_exit), label = "Exit")
            IconColumn(painter = painterResource(id = R.drawable.ic_incognito), label = "Incognito")
            IconColumn(painter = painterResource(id = R.drawable.ic_share), label = "Share")
            IconColumn(painter = painterResource(id = R.drawable.ic_dark_mode), label = "Dark M")
        }

        // Third Row of icons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            IconColumn(imageVector = Icons.Default.Close, label = "Close")
            IconColumn(painter = painterResource(id = R.drawable.ic_share), label = "Share")
            IconColumn(painter = painterResource(id = R.drawable.ic_help_feedback), label = "Help")
        }
    }
}

@Composable
fun IconColumn(imageVector: ImageVector? = null, painter: Painter? = null, label: String) {
    Column(
        modifier = Modifier
            .width(64.dp)
            .padding(vertical = 8.dp)
            .wrapContentSize(Alignment.Center)
            .clickable { /* Handle click event */ }
    ) {
        if (imageVector != null) {
            Icon(
                imageVector = imageVector,
                contentDescription = label,
                modifier = Modifier.size(24.dp),
                tint = Color.Black
            )
        } else if (painter != null) {
            Image(
                painter = painter,
                contentDescription = label,
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = label, fontSize = 12.sp, color = Color.Black)
    }
}

