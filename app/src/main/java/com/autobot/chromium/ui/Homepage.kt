package com.autobot.chromium.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.autobot.chromium.database.ViewModel
import com.autobot.chromium.theme.MyAppThemeColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(viewModel: ViewModel = hiltViewModel()) {
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {
        // Row for displaying tabs
        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(top = 8.dp, start = 8.dp, end = 8.dp)
        ) {

        }

        Box(
            modifier = Modifier.fillMaxWidth().weight(1f),
            contentAlignment = Alignment.Center
        ) {

        }

        // Bottom button bar
        SearchBar(
            onAddTab = {
            },
            onSearch = { query ->
                val newUrl = "https://www.google.com/search?q=$query"
            },
            onMenuClick = {
                showBottomSheet = true
            },
            onTextChange = { text ->
            },
            searchBarText ="https://www.google.com/search?q="
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
