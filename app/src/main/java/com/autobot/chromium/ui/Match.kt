package com.autobot.chromium.ui
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Match(val league: String, val teams: String, val time: String)

@Composable
fun T20LeagueCardRow(matches: List<Match>) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth() // Full width for the row
            .padding(vertical = 16.dp),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp)
    ) {
        items(matches) { match ->
            MatchCard(match)
        }
    }
}

@Composable
fun MatchCard(match: Match) {
    Card(
        modifier = Modifier
            .padding(end = 16.dp) // Space between cards
            .width(300.dp) // Set card width (adjust to fit 90% of the screen)
            .clickable { /* Handle card click */ },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE7E8F0)),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = match.league, fontSize = 16.sp, color = Color.Black)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = match.teams, fontSize = 20.sp, color = Color.Blue)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = match.time, fontSize = 14.sp, color = Color.Red)
        }
    }
}

@Composable
fun MainScreen() {
    val matches = listOf(
        Match("IPL 2024", "DC vs MI", "30m:15s"),
        Match("IPL 2024", "RCB vs CSK", "1h:30m"),
        Match("IPL 2024", "KKR vs SRH", "2h:45m"),
        Match("IPL 2024", "PBKS vs GT", "3h:00m"),
        Match("IPL 2024", "RR vs LSG", "4h:15m")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White) // Optional background color
    ) {
        Text(
            text = "Indian T20 League",
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        T20LeagueCardRow(matches = matches)
    }
}
