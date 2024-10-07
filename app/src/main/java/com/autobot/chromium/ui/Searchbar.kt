package com.autobot.chromium.ui
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.autobot.chromium.theme.MyAppThemeColors

@Composable
fun SearchBar(
    onAddTab: () -> Unit,
    onSearch: (String) -> Unit,
    onMenuClick: () -> Unit,
    onTextChange: (String) -> Unit,
    searchBarText: String // This holds the current text of the search bar
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(searchBarText){

    }

    Surface(
        color = MyAppThemeColors.current.tertiaryDark,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
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
                    // Your image icon here...

                    // BasicTextField to allow user input
                    BasicTextField(
                        value = searchBarText, // Display the current text
                        onValueChange = { newText ->
                            onTextChange(newText) // Update the text when it changes
                        },
                        textStyle = LocalTextStyle.current.copy(fontSize = 16.sp),
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
                        keyboardActions = KeyboardActions(
                            onSearch = {
                                onSearch(searchBarText) // Perform search with current text
                                keyboardController?.hide() // Hide the keyboard
                            }
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp)
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

@Composable
@Preview
fun SearchBarPreview() {
    SearchBar(
        searchBarText = "",
        onTextChange = {},
        onAddTab = {},
        onMenuClick = {},
        onSearch = {}
    )
}