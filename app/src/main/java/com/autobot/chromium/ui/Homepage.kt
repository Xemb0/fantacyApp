package com.autobot.chromium.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.autobot.chromium.theme.MyAppThemeComposable

@Composable
fun Homepage() {
    val viewModel: BrowserViewModel = viewModel()
    BrowserScreen(viewModel = viewModel)

}

    @Preview(showBackground = true)
    @Composable
    fun HelloWorldPreview() {
        MyAppThemeComposable {
            Homepage()
        }
    }
