package com.autobot.chromium.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.autobot.chromium.R
import com.autobot.chromium.database.models.MatchOffer
import com.autobot.chromium.database.models.UpcomingMatches
import com.autobot.chromium.theme.lightSecondaryGradient
import com.autobot.chromium.theme.myAscent
import com.autobot.chromium.theme.myPrimary
import com.autobot.chromium.theme.mySecodary
import com.autobot.chromium.theme.myText
import com.autobot.chromium.theme.secondary
import kotlinx.coroutines.delay


fun LazyListScope.UpcomingMatches(upcomingMatches: UpcomingMatches?) {
    item{
        Text(
            text = "Upcoming Matches",
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 32.dp, top = 24.dp),
            fontSize = 16.sp,
            color = myText,
            fontWeight = FontWeight.W600
        )
    }
    if (upcomingMatches != null) {
        items(upcomingMatches.matchList.size) {
            val match = upcomingMatches.matchList[it]
            UpcomingMatchCard(
                startsAt = match.startsAt.toString(),
                matchOffer = match.matchOffers,
                matchFormat = match.matchFormat,
                tournamentName = match.tournamentName,
                status = match.status,
                teamA = match.teams.a.code,
                teamB = match.teams.b.code,
                teamAImageUrl = match.teams.a.logoUrl,
                teamBImageUrl = match.teams.b.logoUrl,
                lineUpOut = match.metadata.isLineupOut
            )
        }
    }
}

@Composable
fun UpcomingMatchCard(
    startsAt: String,
    matchOffer: List<MatchOffer>,
    matchFormat: String,
    tournamentName: String,
    status: String,
    teamA: String,
    teamB: String,
    teamAImageUrl: String,
    teamBImageUrl: String,
    lineUpOut: Boolean
) {
    val epochMillis = startsAt.toLong() // Assuming startsAt is in epoch milliseconds
    val formattedDate = formatStartDate(epochMillis)

    // Start a countdown timer for the match
    val countdownState = remember { mutableStateOf(calculateTimeRemaining(epochMillis)) }

    // Launch a coroutine to update the countdown every second
    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            countdownState.value = calculateTimeRemaining(epochMillis)
            if (countdownState.value.startsWith("Match started")) break
        }
    }

    val pagerState = rememberPagerState(pageCount = { matchOffer.size }) // Remember the state of the pager
    Card(
        modifier = Modifier.padding(8.dp).fillMaxWidth().padding(horizontal = 16.dp , vertical = 2.dp),
        elevation = CardDefaults.cardElevation(16.dp),
        colors = CardDefaults.cardColors(containerColor = myPrimary)
    ) {

        Column {
            // Top Row with Match Format, Tournament Name, and Status
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
                        if(lineUpOut){


                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.End,
                                modifier = Modifier.fillMaxWidth().padding(end = 12.dp)
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_jersey),
                                    contentDescription = "Clock Icon",
                                    modifier = Modifier
                                        .padding(horizontal = 4.dp)
                                        .clip(RoundedCornerShape(24.dp))
                                        .background(mySecodary).padding(4.dp)
                                )
                                Text(
                                    text = "LINEUPS OUT",
                                    color = mySecodary,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.W900,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }

                    }
                }
            }

            HorizontalDivider(color = Color.Gray.copy(alpha = .5f), thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))


            // Team A and Team B row
            Row(
                modifier = Modifier.fillMaxWidth().wrapContentHeight()
                    .padding(vertical = 16.dp, horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Team A
                Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    AsyncImage(
                        model = teamAImageUrl,
                        contentDescription = "Team A Logo",
                        modifier = Modifier.size(40.dp)  // Adjust size as needed
                    )
                    Text(
                        teamA,
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier.padding(start = 6.dp)
                    )
                }

                // Countdown and start time display
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = countdownState.value,
                        color = myAscent.copy(alpha = .8f),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W900,
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(myAscent.copy(alpha = .3f))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )

                    // Display formatted date
                    Text(
                        text = formattedDate,
                        color = myText.copy(alpha = .5f),
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )

                }

                // Team B
                Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        teamB,
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier.padding(end = 2.dp)
                    )
                    AsyncImage(
                        model = teamBImageUrl,
                        contentDescription = "Team B Logo",
                        modifier = Modifier.size(40.dp)  // Adjust size as needed
                    )
                }
            }

            // Bottom Row with Offers
            Row(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.background(lightSecondaryGradient).padding(8.dp)
                    .alpha(0.9f)
            ) {
                AutoSwitchingPager(matchOffer)
            }
        }


    }
}
