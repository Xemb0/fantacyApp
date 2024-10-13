package com.autobot.chromium.theme


import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// Reusable gradient brushes
val ascentGradient = Brush.linearGradient(
    colors = listOf(myAscent, ascent)  // Use your color variables here
)

val grayGradient = Brush.linearGradient(
    colors = listOf(Color.Gray, Color.Gray.copy(alpha = .7f))  // Use your color variables here
)
val ascentLightGradient = Brush.linearGradient(
    colors = listOf(myAscent.copy(alpha = 0.2f), ascent.copy(alpha = 0.2f))  // Use your color variables here
)
val transparentGradient = Brush.linearGradient(
    colors = listOf(Color.Transparent, Color.Transparent)  // Use your color variables here
)
val elementGradient = Brush.linearGradient(
    colors = listOf(myElement, element)  // Use your color variables here
)

val secondaryGradient = Brush.horizontalGradient(
    colors = listOf(mySecodary, secondary)  // Use your color variables here
)
val reverseSecondaryGradient = Brush.linearGradient(
    colors = listOf(secondary, mySecodary)  // Use your color variables here
)
val lightSecondaryGradient = Brush.linearGradient(
    colors = listOf(mySecodary.copy(alpha = 0.3f), secondary.copy(alpha = 0.3f))  // Use your color variables here
)
