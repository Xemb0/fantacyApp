//
//package com.autobot.chromium.ui
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.PaddingValues
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.layout.wrapContentHeight
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.LazyRow
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardDefaults
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.alpha
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextOverflow
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.hilt.navigation.compose.hiltViewModel
//import androidx.lifecycle.compose.collectAsStateWithLifecycle
//import coil.compose.AsyncImage
//import com.autobot.chromium.R
//import com.autobot.chromium.database.MainViewModel
//import com.autobot.chromium.database.models.MatchOffer
//import com.autobot.chromium.theme.lightSecondaryGradient
//import com.autobot.chromium.theme.myAscent
//import com.autobot.chromium.theme.myPrimary
//import com.autobot.chromium.theme.mySecodary
//import com.autobot.chromium.theme.myText
//import com.autobot.chromium.theme.reverseSecondaryGradient
//
//
//@Composable
//fun T20LeagueCardRow() {
//    val viewModel = hiltViewModel<MainViewModel>()
//
//    val tournaments = viewModel.featuredTournament.collectAsStateWithLifecycle().value?.tournament_list ?: emptyList()
//
//    LazyColumn (
//        modifier = Modifier
//            .fillMaxWidth() // Full width for the row
//            .padding(vertical = 16.dp),
//        contentPadding = PaddingValues(start = 16.dp, end = 16.dp)
//    ) {
//        items(tournaments) { tournament ->
//            tournament.match_list.forEach { match ->
//                TournamentCard(
//                    startsAt = match.startsAt.toString(),
//                    matchOffer = match.matchOffers,
//                    matchFormat = match.matchFormat,
//                    tournamentName = tournament.tournament_name,
//                    status = match.status,
//                    teamA = match.teams.a.code,
//                    teamB = match.teams.b.code,
//                    teamAImageUrl = match.teams.a.logoUrl,
//                    teamBImageUrl = match.teams.b.logoUrl
//                )
//            }
//        }
//
//    }
//}
//
//@Composable
//fun TournamentCard(
//    startsAt: String,
//    matchOffer: List<MatchOffer>,
//    matchFormat: String,
//    tournamentName: String,
//    status: String,
//    teamA: String,
//    teamB: String,
//    teamAImageUrl: String,  // URL for team A's logo
//    teamBImageUrl: String,  // URL for team B's logo
//) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth(),
//        elevation = CardDefaults.cardElevation(16.dp),
//        colors = CardDefaults.cardColors(
//            containerColor = myPrimary
//
//        )
//    ) {
//        Column {
//
//            // Top Row with Match Format, Tournament Name, and Status
//            Row(
//                verticalAlignment = Alignment.CenterVertically,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 12.dp, horizontal = 16.dp)
//            ) {
//                Spacer(modifier = Modifier.width(48.dp))
//                Row(
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.SpaceBetween,
//                    modifier = Modifier.fillMaxWidth().wrapContentHeight()
//                ) {
//                    Text(
//                        text = tournamentName.take(20), // Take only the first 30 characters
//                        color = myPrimary,
//                        fontSize = 12.sp,
//                        fontWeight = FontWeight.Bold,
//                        maxLines = 1, // Limit to 1 line
//                        overflow = TextOverflow.Ellipsis // Add ellipsis if text overflows,
//                        , modifier = Modifier
//                            .clip(shape = RoundedCornerShape(topStart = 4.dp, bottomStart = 4.dp))
//                            .background(reverseSecondaryGradient)
//                            .padding(vertical = 4.dp, horizontal = 16.dp)
//                    )
//                    Row(
//                        horizontalArrangement = Arrangement.SpaceBetween,
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Image(
//                            painter = painterResource(id = R.drawable.ic_jersey),
//                            contentDescription = "Clock Icon", modifier = Modifier
//                                .padding(horizontal = 8.dp)
//                                .clip(RoundedCornerShape(24.dp))
//                                .background(mySecodary).padding(4.dp)
//                        )
//                        Text(
//                            text = status, // Take only the first 30 characters
//                            color = myText,
//                            fontSize = 14.sp,
//                            fontWeight = FontWeight.Bold,
//                            maxLines = 1, // Limit to 1 line
//                            overflow = TextOverflow.Ellipsis
//                        ) // Add ellipsis if text overflows)
//                    }
//                }
//            }
//            Row(
//                modifier = Modifier.fillMaxWidth().wrapContentHeight()
//                    .padding(vertical = 16.dp, horizontal = 16.dp),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//
//            ) {
//                Row(
//                    horizontalArrangement = Arrangement.SpaceBetween,
//                    verticalAlignment = Alignment.CenterVertically
//
//
//                ) {
//                    AsyncImage(
//                        model = teamAImageUrl,
//                        contentDescription = "Team A Logo",
//                        modifier = Modifier.size(70.dp)  // Adjust size as needed
//                    )
//                    Text(
//                        teamA,
//                        color = Color.Black,
//                        fontSize = 26.sp,
//                        fontWeight = FontWeight.ExtraBold,
//                        modifier = Modifier.padding(start = 6.dp)
//                    )
//                }
//                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
//                    Text(startsAt, color = myAscent.copy(alpha = .8f), fontSize = 14.sp, fontWeight = FontWeight.W900, modifier = Modifier.padding(vertical = 8.dp)
//                        .clip(RoundedCornerShape(4.dp))
//                        .background(myAscent.copy(alpha = .3f))
//                        .padding(horizontal = 8.dp, vertical = 4.dp)
//                    )
//                    Text(startsAt, color = myText.copy(alpha = .5f), fontSize = 12.sp, fontWeight = FontWeight.Bold,)
//
//                }
//
//
//                Row(
//                    horizontalArrangement = Arrangement.SpaceBetween,
//                    verticalAlignment = Alignment.CenterVertically
//
//
//                ) {
//                    Text(
//                        teamB,
//                        color = Color.Black,
//                        fontSize = 24.sp,
//                        fontWeight = FontWeight.ExtraBold,
//                        modifier = Modifier.padding(end = 2.dp)
//                    )
//                    AsyncImage(
//                        model = teamBImageUrl,
//                        contentDescription = "Team B Logo",
//                        modifier = Modifier.size(70.dp)  // Adjust size as needed
//                    )
//                }
//            }
//
//            // Bottom Row with Joinings, Contests, and Rank
//            Row(
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.SpaceBetween,
//                modifier = Modifier
//                    .fillMaxWidth().background(lightSecondaryGradient)
//                    .padding(vertical = 12.dp, horizontal = 16.dp).alpha(0.9f)
//            ) {
//
//
//                LazyRow (
//                    modifier = Modifier.fillMaxWidth().wrapContentHeight(),
//                ) {
//                    items(matchOffer) { offer ->
//                        OfferCard(offer)  // Display each offer using OfferCard composable
//                    }
//                }
//            }
//        }
//
//    }
//    Box {
//        Card(
//            shape = RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp),  // Rounded corners
//            elevation = CardDefaults.cardElevation(24.dp),  // Elevation
//            modifier = Modifier.padding(vertical = 8.dp)  // External padding
//        ) {
//            Row(
//                Modifier
//                    .background(reverseSecondaryGradient)  // Gradient background
//                    .padding(vertical = 4.dp, horizontal = 16.dp)  // Internal padding
//            ) {
//                Text(
//                    text = tournamentName,
//                    color = myPrimary,  // Text color
//                    fontSize = 12.sp,
//                    fontWeight = FontWeight.Bold,
//                )
//            }
//        }
//    }
//}
//
//@Composable
//fun OfferCard(offer: MatchOffer) {
//
//    Row(
//        modifier = Modifier.padding(8.dp),
//        horizontalArrangement = Arrangement.spacedBy(8.dp),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        AsyncImage(
//            model = offer.offerIconUrl,
//            contentDescription = "Offer Icon",
//            modifier = Modifier.size(30.dp)
//        )
//        Text(
//            text = "${offer.title} :" ,
//            color = myText,
//            fontSize = 12.sp,
//            fontWeight = FontWeight.Bold,
//            modifier = Modifier
//        )
//        Text(
//            text = offer.subtext,
//            color = myText.copy(alpha = .5f),
//            fontSize = 12.sp,
//            fontWeight = FontWeight.Bold,
//            modifier = Modifier
//        )
//    }
//}
//
//
//@Preview
//@Composable
//fun PreviewTournamentCard() {
//    TournamentCard(
//        startsAt = "30m:15s",
//        matchOffer =
//        listOf(
//            MatchOffer(
//                offerIconUrl = "https://example.com/offerIcon.png",
//                title = "Offer Title",
//                subtext = "Offer Subtext"
//            )
//        ),
//        matchFormat = MatchOffer(
//            offerIconUrl = "https://example.com/offerIcon.png",
//            title = "Offer Title",
//            subtext = "Offer Subtext"
//        ).toString(),
//        tournamentName = "IPL 2024",
//        status = "started",
//        teamA = "DC",
//        teamB = "MI",
//        teamAImageUrl = "https://example.com/teamA.png",
//        teamBImageUrl = "https://example.com/teamB.png"
//    )
//}