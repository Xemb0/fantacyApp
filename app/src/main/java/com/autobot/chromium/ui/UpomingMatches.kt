//package com.autobot.chromium.ui
//
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.lazy.LazyListScope
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardDefaults
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//
//
//fun LazyListScope.UpcomingMatches(match: List<Match>) {
//    items(match.size) {
//        UpcomingMatchCard(match[it])
//    }
//}
//
//@Composable
//fun UpcomingMatchCard(match: Match) {
//    Card(
//        modifier = Modifier
//            .padding(24.dp) // Space between cards
//            .fillMaxWidth() // Full width for each card
//            .clickable { /* Handle card click */ },
//        shape = RoundedCornerShape(16.dp),
//        colors = CardDefaults.cardColors(containerColor = Color(0xFFE7E8F0)),
//        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(16.dp),
//            verticalArrangement = Arrangement.SpaceBetween
//        ) {
//            Text(text = match.league, fontSize = 18.sp, color = Color.Black)
//            Spacer(modifier = Modifier.height(8.dp))
//            Text(text = match.teams, fontSize = 20.sp, color = Color.Blue)
//            Spacer(modifier = Modifier.height(8.dp))
//            Text(text = match.time, fontSize = 14.sp, color = Color.Red)
//        }
//    }
//}
