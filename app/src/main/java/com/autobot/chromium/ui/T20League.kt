
package com.autobot.chromium.ui
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.autobot.chromium.R
import com.autobot.chromium.database.MainViewModel
import com.autobot.chromium.database.models.MatchOffer
import com.autobot.chromium.theme.lightSecondaryGradient
import com.autobot.chromium.theme.myAscent
import com.autobot.chromium.theme.myPrimary
import com.autobot.chromium.theme.mySecodary
import com.autobot.chromium.theme.myText
import com.autobot.chromium.theme.reverseSecondaryGradient
import com.autobot.chromium.ui.customcomposable.RibbonBadge
import kotlinx.coroutines.delay
import java.time.Duration
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit


@Composable
fun T20LeagueCardRow() {
    val viewModel = hiltViewModel<MainViewModel>()

    val tournaments = viewModel.featuredTournament.collectAsStateWithLifecycle().value?.matchList ?: emptyList()

    Text(
        text = "Indian T20 league",
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 32.dp),
        fontSize = 16.sp,
        color = Color.Black,
        fontWeight = FontWeight.W600
    )
    LazyRow (
        modifier = Modifier,
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp)
    ) {
        items(tournaments) { match ->
                TournamentCard(
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
fun OfferCardT20(offer: MatchOffer) {

    Row(
        modifier = Modifier.fillMaxWidth().height(30.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = offer.offerIconUrl,
            contentDescription = "Offer Icon",
            modifier = Modifier.size(if(offer.offerIconUrl=="") 0.dp else 40.dp)  // Adjust size as needed
        )
        Text(
            text = "${offer.title} ",
            color = myText,
            fontSize = 12.sp,
            fontWeight = FontWeight.W900,
            modifier = Modifier
        )
        Text(
            text = offer.subtext,
            color = myText.copy(alpha = .5f),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
        )

    }
}

@Composable
fun TournamentCard(
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
        modifier = Modifier.padding(8.dp).width(320.dp),
        elevation = CardDefaults.cardElevation(16.dp),
        colors = CardDefaults.cardColors(containerColor = myPrimary)
    ) {

            Column {
                // Top Row with Match Format, Tournament Name, and Status
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                        Row{
                            // Tournament name text
                            Text(
                                text = tournamentName.take(25),
                                color = myPrimary,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                textAlign = TextAlign.Start, // Left-align the text
                                modifier = Modifier // Make the text take available space
                                    .padding(top = 16.dp)
                                    .clip(
                                        shape = RoundedCornerShape(
                                            topEnd = 4.dp,
                                            bottomEnd = 4.dp
                                        )
                                    )
                                    .background(reverseSecondaryGradient)
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                                    .zIndex(1f) // Ensure the text is on top of the ribbon
                            )

                            // Ribbon Badge with a left offset
                            RibbonBadge(
                                text = matchFormat,
                                modifier = Modifier
                                    .offset(x = (-12).dp ,y = (8).dp) // Move the ribbon 12dp to the left
                                    .zIndex(0f) // Ribbon should be below the text
                            )

                    }


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


@Composable
fun AutoSwitchingPager(
    matchOffer: List<MatchOffer>,
) {
   val  autoSwitchIntervalMillis: Long = 3000L // Auto switch every 3 seconds
    // Safeguard: Only proceed if matchOffer is not empty
    if (matchOffer.isNotEmpty()) {
        val pagerState = rememberPagerState(pageCount = { matchOffer.size })

        // LaunchedEffect to automatically switch pages at the specified interval
        LaunchedEffect(Unit) {
            while (true) {
                delay(autoSwitchIntervalMillis)  // Delay for the specified interval
                val nextPage = (pagerState.currentPage + 1) % matchOffer.size
                pagerState.animateScrollToPage(nextPage) // Animate scroll to the next page
            }
        }

        Row(
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
        ) {
            VerticalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
            ) { page ->
                OfferCardT20(matchOffer[page]) // Display the offer card for the current page
            }
        }
    } else {
        OfferCardT20(MatchOffer("", "No offers Available", "")) // Display a placeholder card if matchOffer is empty
    }
}


@Composable
fun CountdownTimer(epochMillis: Long) {
    var remainingTime by remember { mutableStateOf(calculateTimeRemaining(epochMillis)) }

    // Start a coroutine to update the countdown every second
    LaunchedEffect(Unit) {
        while (true) {
            delay(1000) // Update every second
            remainingTime = calculateTimeRemaining(epochMillis)
            if (remainingTime.startsWith("Match started")) break // Stop when match starts
        }
    }

    Text(text = remainingTime)
}

fun calculateTimeRemaining(epochMillis: Long): String {
    val now = System.currentTimeMillis()
    val duration = Duration.ofMillis(epochMillis - now)

    return if (duration.toMillis() > 0) {
        val hours = duration.toHours()
        val minutes = duration.toMinutes() % 60
        val seconds = duration.seconds % 60
        String.format("%02d:%02d:%02d", hours, minutes, seconds)
    } else {
        "Match Started"
    }
}
fun formatStartDate(epochMillis: Long): String {
    val now = Instant.now()
    val matchTime = Instant.ofEpochMilli(epochMillis)
    val daysDifference = ChronoUnit.DAYS.between(now, matchTime)
    val dayOfWeek = matchTime.atZone(ZoneId.systemDefault()).dayOfWeek
    val formattedDayOfWeek = dayOfWeek.name.lowercase().replaceFirstChar { it.uppercase() }

    // Format time with AM/PM in uppercase
    val timeFormatter = DateTimeFormatter.ofPattern("hh:mm a")
        .withZone(ZoneId.systemDefault())

    val formattedTime = timeFormatter.format(matchTime).uppercase() // Ensures AM/PM is uppercase

    return when {
        daysDifference == 0L -> "Today, $formattedTime"
        daysDifference == 1L -> "Tomorrow, $formattedTime"
        daysDifference in 2..6 -> "$formattedDayOfWeek, $formattedTime" // Show day and time
        else -> {
            // If more than 7 days away, return full date with time in AM/PM
            val fullFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a")
                .withZone(ZoneId.systemDefault())
            fullFormatter.format(matchTime).uppercase() // Ensures AM/PM is uppercase
        }
    }
}




@Composable
fun PreviewTournamentCard() {
    TournamentCard(
        startsAt = "30m:15s",
        matchOffer =
        listOf(
            MatchOffer(
                offerIconUrl = "https://example.com/offerIcon.png",
                title = "Offer Title",
                subtext = "Offer Subtext"
            )
        ),
        matchFormat = MatchOffer(
            offerIconUrl = "https://example.com/offerIcon.png",
            title = "Offer Title",
            subtext = "Offer Subtext"
        ).toString(),
        tournamentName = "IPL 2024",
        status = "started",
        teamA = "DC",
        teamB = "MI",
        teamAImageUrl = "https://example.com/teamA.png",
        teamBImageUrl = "https://example.com/teamB.png",
        lineUpOut = true
    )
}
