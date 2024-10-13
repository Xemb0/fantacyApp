package com.autobot.chromium.ui

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.autobot.chromium.R
import com.autobot.chromium.database.MainViewModel
import com.autobot.chromium.theme.element
import com.autobot.chromium.theme.myAscent
import com.autobot.chromium.theme.myPrimary
import com.autobot.chromium.theme.mySecodary
import com.autobot.chromium.theme.myText
import com.autobot.chromium.theme.secondary
import kotlinx.coroutines.delay

@Composable
fun Crousal() {
    val viewModel = hiltViewModel<MainViewModel>()
    val userMatches = viewModel.userMatches.collectAsStateWithLifecycle()

    // Define a height for the HorizontalPager to avoid infinite height issues
    val pagerHeight = 150.dp // Adjust the height based on your design
    val totalPages = userMatches.value?.match_list?.size ?: 1

    // Use totalPages for the pageCount
    val pagerState = rememberPagerState(
        pageCount = { if (totalPages > 0) totalPages else 1 }, // Use totalPages or default to 1
        initialPage = 0
    )

    LaunchedEffect(pagerState) {
        while (true) {
            delay(3000L)
            val nextPage = (pagerState.currentPage + 1) % totalPages // Loop through pages
            pagerState.animateScrollToPage(nextPage)
        }
    }
Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth().padding(top = 24.dp, bottom = 8.dp)) {
    Text(
        textAlign = TextAlign.Center,
        text = "My Matches",
        color = Color.White,
        style = TextStyle(fontSize = 16.sp),
        fontWeight = FontWeight.W600,
        modifier = Modifier
    )
}

    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxWidth()
    ) { page ->
        // Calculate the actual page index based on the current page
        val actualPage = page % totalPages

        // Render your MatchCard here based on the actual page

        userMatches.value?.match_list?.get(actualPage)?.let { match ->
            MatchCard(
                matchFormat = match.match.matchFormat,
                tournamentName = match.match.tournamentName,
                status = match.match.status,
                teamA = match.match.teams.a.code,
                teamB = match.match.teams.b.code,
                teamAImageUrl = match.match.teams.a.logoUrl,
                teamBImageUrl = match.match.teams.b.logoUrl,
                joinings = match.user_teams.toString(),
                contests = match.user_contests.toString(),
                topRunningRank = match.top_running_rank.toString()
            )
        }
    }

    // Indicator for the currently selected page
    DotIndicatorWithAnimation(
        totalDots = totalPages,
        selectedIndex = pagerState.currentPage % totalPages
    )
}

@Composable
fun MatchCard(
    matchFormat: String,
    tournamentName: String,
    status: String,
    teamA: String,
    teamB: String,
    teamAImageUrl: String,  // URL for team A's logo
    teamBImageUrl: String,  // URL for team B's logo
    joinings: String,       // Number of joinings
    contests: String,       // Contest details
    topRunningRank: String  // Top running rank
) {
    Card(
        modifier = Modifier
            .fillMaxWidth().padding(horizontal = 40.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = myPrimary

        )
    ) {
            // Top Row with Match Format, Tournament Name, and Status
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp , horizontal = 16.dp)
            ) {
                Text(matchFormat, color = secondary, fontSize = 12.sp, fontWeight = FontWeight.W900)
                Spacer(modifier = Modifier.width(8.dp))
                VerticalDivider(color = Color.Gray.copy(alpha = .8f), thickness = 1.dp, modifier = Modifier.height(16.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth().wrapContentHeight()
                ) {
                    Text(
                        text = tournamentName.take(30), // Take only the first 30 characters
                        color = myText.copy(alpha = .7f),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W900,
                        maxLines = 1, // Limit to 1 line
                        overflow = TextOverflow.Ellipsis // Add ellipsis if text overflows
                    )
                    Row (horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        if(status=="started") {
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 8.dp)
                                    .size(10.dp)
                                    .clip(shape = RoundedCornerShape(24.dp))
                                    .background(myAscent)
                            )

                            Text( text =  "Live", // Take only the first 30 characters
                                color = myAscent,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.W900,
                                maxLines = 1, // Limit to 1 line
                                overflow = TextOverflow.Ellipsis) // Add ellipsis if text overflows)
                        }else {
                            Text( text = status, // Take only the first 30 characters
                                color = mySecodary,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.W900,
                                maxLines = 1, // Limit to 1 line
                                overflow = TextOverflow.Ellipsis) // Add ellipsis if text overflows)
                        }

                    }
                }
            }

            HorizontalDivider(color = Color.Gray.copy(alpha = .5f), thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))

            // Middle Row with Teams
            Row(
                modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(vertical = 16.dp, horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically

            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically


                ) {
                    AsyncImage(
                        model = teamAImageUrl,
                        contentDescription = "Team A Logo",
                        modifier = Modifier.size(40.dp)  // Adjust size as needed
                    )
                    Text(teamA, color = Color.Black, fontSize = 16.sp, fontWeight = FontWeight.ExtraBold,modifier = Modifier.padding(start = 6.dp))
                }
                Text("VS", color = secondary, fontSize = 14.sp, fontWeight = FontWeight.W900,)
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically


                ) {
                    Text(teamB, color = Color.Black, fontSize = 16.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier.padding(end = 2.dp))
                    AsyncImage(
                        model = teamBImageUrl,
                        contentDescription = "Team B Logo",
                        modifier = Modifier.size(40.dp)  // Adjust size as needed
                    )
                }
            }

            // Bottom Row with Joinings, Contests, and Rank
            Row(
                verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth().background(Color.Gray.copy(alpha = .15f))
                    .padding(vertical = 12.dp , horizontal = 16.dp)
            ) {
                Row {
                Text("JOININGS", color = Color.Gray, fontSize = 9.sp, fontWeight = FontWeight.W900)
                Text("($joinings)", color = secondary, fontSize = 9.sp, fontWeight = FontWeight.W900)
                Spacer(modifier = Modifier.width(8.dp))
                Text("CONTESTS", color = Color.Gray, fontSize = 9.sp, fontWeight = FontWeight.W900)
                Text("($contests)", color = secondary, fontSize = 9.sp, fontWeight = FontWeight.W900)
                }

                Row(
                    horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_badge),
                        contentDescription = "Rank Icon",
                    )
                    Text(" TOP RUNNING RANK : ", color = Color.Gray, fontSize = 9.sp, fontWeight = FontWeight.W900 ,maxLines = 1, overflow = TextOverflow.Ellipsis)
                    Text(
                        text = if (topRunningRank.length > 4) "${topRunningRank.take(4)}..." else topRunningRank,
                        color = element,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.W900,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }




@Composable
fun DotIndicatorWithAnimation(totalDots: Int, selectedIndex: Int) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth().padding(top = 8.dp, bottom = 16.dp)
    ) {
        for (i in 0 until totalDots) {
            IndicatorSingleDot(isSelected = i == selectedIndex)
        }
    }
}


@Composable
fun IndicatorSingleDot(isSelected: Boolean) {
    val width by animateDpAsState(
        targetValue = if (isSelected) 8.dp else 8.dp,
        label = "Dot width animation"
    )

    Box(
        modifier = Modifier
            .padding(horizontal = 2.dp)
            .height(8.dp)
            .width(width)
            .clip(CircleShape)
            .background(if (isSelected) myAscent else Color.Gray.copy(alpha = .3f))
    )
}


@Composable
@Preview
fun Prevmatch() {
}
