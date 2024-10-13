package com.autobot.chromium.ui.customcomposable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.autobot.chromium.theme.reverseSecondaryGradient

@Composable
fun RibbonBadge(text: String , modifier: Modifier) {
    // Outer container for the ribbon
    Box(
        modifier = modifier
            .padding(vertical = 16.dp) // Adjust padding as needed
            .background(brush = reverseSecondaryGradient, shape = CustomRibbonShape()) // Custom shape
            .padding(horizontal = 16.dp, vertical = 4.dp) // Adjust inner padding for the text
    ) {
        Row(horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                color = Color.White, // White text
                fontSize = 10.sp, // Adjust text size as per design
                fontWeight = FontWeight.Bold // Bold style
            )

            Spacer(modifier = Modifier.width(8.dp)) // Spacing between texts

        }
    }
}

// Define a custom shape to create the flag/ribbon effect with an inward cut on the right side
class CustomRibbonShape : Shape {
    override fun createOutline(
        size: androidx.compose.ui.geometry.Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            moveTo(0f, 0f) // Top-left corner
            lineTo(size.width * 0.85f, 0f) // Move to near the right edge
            lineTo(size.width, 0f) // Top-right corner (straight)
            lineTo(size.width * 0.85f, size.height / 2) // Create the inward triangular notch
            lineTo(size.width, size.height) // Bottom-right corner (straight)
            lineTo(size.width * 0.85f, size.height) // Near bottom-right corner
            lineTo(0f, size.height) // Bottom-left corner
            close() // Close the path
        }
        return Outline.Generic(path)
    }
}
