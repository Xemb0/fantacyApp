package com.autobot.chromium.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
@Preview
@Composable
fun GradientDemo() {
    Column(modifier = Modifier.fillMaxSize()) {
        
        // Ascent Gradient
        Box(
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(myAscent, ascent)
                    )
                )
        ) {
            Text(text = "Ascent Gradient (10%)", modifier = Modifier.padding(16.dp), color = Color.White)
        }
        
        Spacer(modifier = Modifier.height(16.dp))

        // Element Gradient
        Box(
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(myElement, element)
                    )
                )
        ) {
            Text(text = "Element Gradient", modifier = Modifier.padding(16.dp), color = Color.Black)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Secondary Gradient
        Box(
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(mySecodary, secondary)
                    )
                )
        ) {
            Text(text = "Secondary Gradient (30%)", modifier = Modifier.padding(16.dp), color = Color.White)
        }
    }
}
