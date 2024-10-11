package com.autobot.chromium.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeContent() {
    val lazyListState = remember { LazyListState() }

    LazyColumn(
        state = lazyListState,
        modifier = Modifier
            .padding(vertical = 16.dp) // Optional padding for visual spacing
    ) {
        // Carousel
        item {
            Crousal() // Ensure Crousal has a fixed height
        }

        // Sticky Header for Offers
        stickyHeader {
            InfiniteOffersBannerCarousel(modifier = Modifier.height(200.dp)) // Ensure this has a fixed height
        }

        // Upcoming Matches with its own LazyColumn
        item {
            UpcomingMatches(
                matches = listOf(
                    Match("IPL 2024", "DC vs MI", "30m:15s"),
                    Match("IPL 2024", "RCB vs CSK", "1h:15m"),
                    Match("IPL 2024", "MI vs KKR", "2h:30m"),
                    Match("IPL 2024", "DC vs SRH", "3h:45m"),
                ),
                T20matchs = listOf(
                    Match("T20 World Cup", "DC vs MI", "30m:15s"),
                    Match("T20 World Cup", "RCB vs CSK", "1h:15m"),
                    Match("T20 World Cup", "MI vs KKR", "2h:30m"),
                    Match("T20 World Cup", "DC vs SRH", "3h:45m"),
                ),
                lazyListState = lazyListState // Pass the scroll state
            )
        }
    }
}

@Composable
fun UpcomingMatches(matches: List<Match>, T20matchs: List<Match>, lazyListState: LazyListState) {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        // Title for Upcoming Matches
        Text(
            text = "Upcoming Matches",
            modifier = Modifier.padding(bottom = 8.dp),
            fontSize = 20.sp,
            color = Color.Black
        )

        // Create a LazyColumn for upcoming matches
        LazyColumn(
            state = lazyListState,
            modifier = Modifier
                .fillMaxWidth() // Fill the width
                .heightIn(max = 400.dp) // Set a max height for scrolling
                .padding(bottom = 16.dp) // Maintain consistent bottom padding
        ) {
            // Display T20 matches
            item {
                T20LeagueCardRow(T20matchs)
            }
            // Display upcoming matches
            items(matches) { match ->
                MatchCard(match)
            }
        }
    }
}
