package com.autobot.chromium

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.autobot.chromium.theme.MyAppThemeColors
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BrowserScreen(viewModel: BrowserViewModel) {
    var searchText by remember { mutableStateOf("") }
    var isSearching by remember { mutableStateOf(false) }
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    val tabs by remember { mutableStateOf(viewModel.tabs) }
    var newTabUrl by remember { mutableStateOf("") }
    var currentTabUrl by remember { mutableStateOf("") } // Holds the URL of the currently selected tab

    LaunchedEffect(newTabUrl) {
        if (newTabUrl.isNotBlank()) {
            viewModel.addTab(newTabUrl)
            newTabUrl = ""
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Tab Row
        TabRow(selectedTabIndex = viewModel.selectedTabIndex) {
            tabs.forEachIndexed { index, url ->
                Tab(
                    selected = viewModel.selectedTabIndex == index,
                    onClick = {
                        viewModel.selectTab(index)
                        currentTabUrl = url // Update the current tab URL
                    },
                    text = { Text(url) }
                )
            }
        }
        Row(modifier = Modifier.weight(1f)) {
            // Show the browser with tabs and update with the search text
            if (isSearching) {
                BrowserWithTabs(searchText,isSearching = false) // Use your custom composable for showing results
            } else {
                if (currentTabUrl.isBlank()) {
                    BrowserHomePage() // Shows the homepage if no search text
                } else {
                    BrowserWithTabs(currentTabUrl,isSearching = true) // Show the current tab URL content
                }
            }
        }


        SearchBar(
            searchText = searchText,
            onTextChange = { searchText = it },
            onAddTab = { newTabUrl = "Home" },
            onMenuClick = {
                // Show bottom sheet on menu click
                scope.launch {
                    showBottomSheet = true
                }
            },
            onSearch = { query ->
                searchText = query  // Update the searchText with the query
                isSearching = true  // Set searching state to true
                currentTabUrl = query // Update current tab URL with search query
            }
        )
    }
}

@Composable
fun SearchBar(
    searchText: String,
    onTextChange: (String) -> Unit,
    onAddTab: () -> Unit,
    onMenuClick: () -> Unit,
    onSearch: (String) -> Unit // New parameter to handle search submission
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Surface(
        color = MyAppThemeColors.current.tertiaryDark,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .background(
                            color = MyAppThemeColors.current.tertiaryDark,
                            shape = RoundedCornerShape(8.dp)
                        )
                ) {
                    Icon(
                        Icons.Default.Settings,
                        contentDescription = "More Options",
                        modifier = Modifier
                            .padding(8.dp)
                            .background(color = MyAppThemeColors.current.tertiary)
                    )
                    BasicTextField(
                        value = searchText,
                        onValueChange = onTextChange,
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = LocalTextStyle.current.copy(fontSize = 18.sp),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Search  // Set IME action to "Search"
                        ),
                        keyboardActions = KeyboardActions(
                            onSearch = {
                                keyboardController?.hide()  // Hide the keyboard
                                onSearch(searchText)  // Trigger search when "Search" key is pressed
                            }
                        ),
                        singleLine = true  // Ensure it stays single-line
                    )
                }
            }

            // Plus icon and Menu icon
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                IconButton(onClick = onAddTab) {
                    Icon(Icons.Default.Add, contentDescription = "Add New Tab")
                }

                IconButton(onClick = onMenuClick) {
                    Icon(Icons.Default.MoreVert, contentDescription = "More Options")
                }
            }
        }
    }
}


@Preview
@Composable
fun BrowserHomePage() {
    // Replace R.drawable.your_background_image with your actual image resource
    val backgroundImage = painterResource(id = R.drawable.bg)

    Box(
        modifier = Modifier.wrapContentHeight()
    ) {
        // Background image
        Image(
            painter = backgroundImage,
            contentDescription = null,
            contentScale = ContentScale.Crop, // Adjusts the image scaling
            modifier = Modifier.fillMaxSize()
        )

        // Overlay content
        Column(
            modifier = Modifier.fillMaxSize().padding(vertical = 16.dp)// Adjust padding as needed
        ) {
            BrowserHome()
            VoiceSearchBar(modifier = Modifier.weight(1f)) { }
            RecentWebsites()
        }
    }
}