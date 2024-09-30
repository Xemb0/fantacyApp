package com.autobot.chromium

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.webkit.WebSettingsCompat
import androidx.webkit.WebViewFeature
import com.autobot.chromium.theme.MyAppThemeColors
import kotlinx.coroutines.launch


@Composable
fun ChromiumWebViewWithWebKit(url: String) {
    AndroidView(factory = { context ->
        WebView(context).apply {
            webViewClient = WebViewClient()  // Keeps WebView inside the app
            settings.javaScriptEnabled = true  // Enable JavaScript
            loadUrl(url)

            // Enable Dark Mode if supported
            if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
                WebSettingsCompat.setForceDark(settings, WebSettingsCompat.FORCE_DARK_ON)
            }

            // Allow zoom controls
            settings.setSupportZoom(true)
            settings.builtInZoomControls = true
            settings.displayZoomControls = false
        }
    }, modifier = Modifier.fillMaxSize())
}
@Composable
fun BrowserWithTabs(searchText: String, isSearching: Boolean) {
    var tabs by remember { mutableStateOf(listOf("https://www.google.com")) }
    var selectedTabIndex by remember { mutableStateOf(0) }

    // Only update the tab URL if a search has been initiated
    if (isSearching && searchText.isNotBlank()) {
        tabs = tabs.toMutableList().apply {
            this[selectedTabIndex] = searchText
        }
    }

    Column {
        // Show the WebView for the selected tab
        ChromiumWebViewWithWebKit(url = tabs[selectedTabIndex])
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BrowserUI(
    //new tab url callback 
    onAddTab: (String) -> String
) {
    var searchText by remember { mutableStateOf("") }
    var isSearching by remember { mutableStateOf(false) }
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {},
        content = { innerPadding ->
            Column(
                modifier = Modifier.padding(innerPadding)
            ) {
                // Show the browser with tabs and update with the search text
                if (searchText.isBlank() || !isSearching) {
                    BrowserHomePage()  // Shows the homepage if no search text
                } else {
                    BrowserWithTabs(searchText, isSearching)  // Pass the search state
                }
            }
        },
        bottomBar = {
            SearchBar(
                searchText = searchText,
                onTextChange = { searchText = it },
                onAddTab = { /* Handle new tab click */ },
                onMenuClick = {
                    // Show bottom sheet on menu click
                    scope.launch {
                        showBottomSheet = true
                    }
                },
                onSearch = { query ->
                    searchText = query  // Update the searchText with the query
                    isSearching = true  // Set searching state to true
                }
            )
        }
    )

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet = false
            },
            sheetState = sheetState,
            modifier = Modifier.background(MyAppThemeColors.current.tertiaryDark)
        ) {
            BottomSheetContent()
        }
    }
}



@Preview
@Composable
fun BrowserHome(){

    val image: Painter = painterResource(id = R.drawable.ic_chromium)
    val bookmark: Painter = painterResource(id = R.drawable.ic_bookmark)

    Row(
        modifier = Modifier.fillMaxWidth().padding(16.dp), // Makes the row take up the full width
        horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically // Space items evenly within the row
    ) {
        // First icon
        Image(
            painter = image,
            contentDescription = "Icon with image",
            modifier = Modifier.size(40.dp)
        )

        // Text in the middle
        Text(
            "Chromium",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(8.dp)
                .weight(1f)  // Give weight to the text to push the third icon to the end
        )

        Row (modifier = Modifier.padding(12.dp).background(color = MyAppThemeColors.current.tertiaryDark , shape = RoundedCornerShape(24.dp)).padding(8.dp) , verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center){
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "Icon with image",
                modifier = Modifier.size(20.dp)
            )
            Text("28 C" , fontSize = 16.sp)
        }

        // Third icon aligned at the end
        Image(
            painter = bookmark,
            contentDescription = "Icon with image",
            modifier = Modifier.size(40.dp)
        )
    }

}

@Composable
fun RecentWebsites(){
    LazyRow { // LazyRow to display items horizontally
        items(10) { index ->
            // Display 10 items
            Column(
                modifier = Modifier.padding(8.dp).fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_chromium),
                    contentDescription = "Icon with image",
                    modifier = Modifier.size(50.dp)
                )
                Text(
                    "Website $index",
                    fontSize = 16.sp
                )
            }
        }
    }
}



@Composable
fun VoiceSearchBar(
    modifier: Modifier = Modifier,
    onVoiceSearch: () -> Unit
) {
    Surface(
        modifier = modifier
        , color = Color.Transparent
    ) {
        Row(
            modifier = Modifier.fillMaxSize()
                    ,verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center,
        ) {

            // Microphone icon for voice search
            IconButton(onClick = onVoiceSearch) {
                Icon(Icons.Default.Favorite, contentDescription = "Voice Search")
            }
        }
    }
}


@Preview
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
        // Top drag handle
        HorizontalDivider(
            modifier = Modifier
                .width(80.dp)
                .padding(vertical = 8.dp)
                .clip(RoundedCornerShape(48.dp)),
            thickness = 6.dp,
            color = MyAppThemeColors.current.myText
        )

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

        HorizontalDivider(
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
            IconColumn(painter = painterResource(id = R.drawable.ic_dark_mode), label = "Dark Mode")
        }

        // Third Row of icons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconColumn(painter = painterResource(id = R.drawable.ic_desktop_mode), label = "Desktop Mode")
            IconColumn(painter = painterResource(id = R.drawable.ic_help_feedback), label = "Help")
            IconColumn(painter = painterResource(id = R.drawable.ic_find_onpage), label = "Find")
            IconColumn(painter = painterResource(id = R.drawable.ic_delete), label = "Delete")
        }
    }
}

@Composable
fun IconColumn(
    imageVector: ImageVector? = null,
    painter: Painter? = null,
    label: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .clickable { }
            .padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (imageVector != null) {
            Image(
                imageVector = imageVector,
                contentDescription = label,
                modifier = Modifier.size(40.dp)
            )
        } else if (painter != null) {
            Image(
                painter = painter,
                contentDescription = label,
                modifier = Modifier.size(50.dp)
            )
        }
        Text(label, fontSize = 16.sp)
    }
}

@Composable
fun HorizontalDivider(
    modifier: Modifier = Modifier,
    thickness: Dp = 1.dp,
    color: Color = Color.Gray
) {
    Box(
        modifier
            .fillMaxWidth()
            .height(thickness)
            .background(color = color)
    )
}




@Preview
@Composable
fun BrowserUIPreview() {
}