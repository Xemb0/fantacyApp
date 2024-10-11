package com.autobot.chromium.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun Crousal() {
    // Define a height for the HorizontalPager to avoid infinite height issues
    val pagerHeight = 200.dp // Adjust the height based on your design
    val totalPages = 4
    val infinitePageCount = Int.MAX_VALUE
    val pagerState = rememberPagerState(
        pageCount = { infinitePageCount },
        initialPage = 1
    )

    LaunchedEffect(pagerState) {
        while (true) {
            delay(3000L)
            val nextPage = pagerState.currentPage + 1
            pagerState.animateScrollToPage(nextPage)
        }
    }

    Text(textAlign = TextAlign.Center,
        text = "My Matches",
        color = Color.White,
        style = TextStyle(fontSize = 18.sp),
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(top = 32.dp, bottom = 4.dp)
    )

    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxWidth()
            .height(pagerHeight), // Specify a fixed height to avoid infinite height issues
        verticalAlignment = Alignment.Top
    ) { page ->
        val actualPage = page % totalPages
        MatchCard("T20 World Cup", "DC vs MI", "30m:15s", "Top Running Rank: $actualPage")
    }

    DotIndicatorWithAnimation(
        totalDots = totalPages,
        selectedIndex = pagerState.currentPage % totalPages
    )
}
