package com.autobot.chromium.ui.customcomposable

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
//
//@Composable
//fun AnimatedIcon() {
//    val animationState = remember { Animatable(0f) }
//    var isAnimating by remember { mutableStateOf(false) }
//
//    LaunchedEffect(isAnimating) {
//        if (isAnimating) {
//            animationState.animateTo(1f, tween(durationMillis = 300))
//        } else {
//            animationState.snapTo(0f)
//        }
//    }
//
//    Canvas(
//        modifier = Modifier
//            .size(32.dp)
//            .clickable { isAnimating = !isAnimating }
//    ) {
//        // Load the icon using Coil (assuming it's a drawable resource)
//        val painter = rememberImagePainter("drawable/drawable/ic_hambuger.xml") // Change to your icon path
//
//        // Draw your icon here with animation
//        // Example: Change the scale based on animationState.value
//        drawImage(
//            painter.image,
//            topLeft = Offset(0f, 0f),
//            size = Size(size.width * animationState.value, size.height * animationState.value)
//        )
//    }
//}
