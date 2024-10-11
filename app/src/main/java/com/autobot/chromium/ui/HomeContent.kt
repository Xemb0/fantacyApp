package com.autobot.chromium.ui
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeContent() {
    LazyColumn(
        modifier = Modifier
            .padding(vertical = 16.dp) // Optional padding for visual spacing
    ) {
        // Carousel and Banner
        item {
            Crousal() // Ensure Crousal has a fixed height
        }

        // Sticky Header for Offers
        item {
            InfiniteOffersBannerCarousel(modifier = Modifier.height(200.dp)) // Ensure this has a fixed height
        }
        item {
            // Replace with your sticky header content
            Text(
                text = "Indian T20 league",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                fontSize = 20.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
            T20LeagueCardRow(
                matches = listOf(
                    Match("T20 World Cup", "DC vs MI", "30m:15s"),
                    Match("T20 World Cup", "RCB vs CSK", "1h:15m"),
                    Match("T20 World Cup", "MI vs KKR", "2h:30m"),
                    Match("T20 World Cup", "DC vs SRH", "3h:45m"),
                )
            )
        }

        // List of T20 Matches


        // Upcoming Matches

            UpcomingMatches(
                listOf(
                    Match("IPL 2024", "DC vs MI", "30m:15s"),
                    Match("IPL 2024", "RCB vs CSK", "1h:15m"),
                    Match("IPL 2024", "MI vs KKR", "2h:30m"),
                    Match("IPL 2024", "DC vs SRH", "3h:45m"),
                )
            )

    }
}




