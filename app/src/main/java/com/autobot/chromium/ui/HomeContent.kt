package com.autobot.chromium.ui
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.autobot.chromium.database.MainViewModel

@Composable
fun HomeContent() {
    val viewModel: MainViewModel = hiltViewModel()
    val upcomingMatches = viewModel.upcomingMatches.collectAsStateWithLifecycle().value
    viewModel.userMatches
    val userMatches = viewModel.userMatches.collectAsStateWithLifecycle().value
    val featuredTournament = viewModel.featuredTournament.collectAsStateWithLifecycle().value
    val offerBanners = viewModel.currentOffers.collectAsStateWithLifecycle().value

    LazyColumn(
        modifier = Modifier
    ) {
        item {
            if (userMatches != null) {
                if(userMatches.match_list.isNotEmpty()){
                    Crousal()
                }
            }
        }

        item {
            if (offerBanners != null) {
                if(offerBanners.offer_list.isNotEmpty()){
                    InfiniteOffersBannerCarousel(modifier = Modifier.height(200.dp)) // Ensure this has a fixed height
                }
            }
        }
        item{
            if (featuredTournament != null) {
                if(featuredTournament.matchList.isNotEmpty()){
                    T20LeagueCardRow()
                }
            }
        }

        UpcomingMatches(upcomingMatches = upcomingMatches)

    }
}




