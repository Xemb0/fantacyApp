package com.autobot.chromium.ui
import ShimmerEffect
import androidx.compose.foundation.layout.fillMaxSize
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
    val userMatches = viewModel.userMatches.collectAsStateWithLifecycle().value
    val featuredTournament = viewModel.featuredTournament.collectAsStateWithLifecycle().value
    val offerBanners = viewModel.currentOffers.collectAsStateWithLifecycle().value
    val isLoading = viewModel.isLoading.collectAsStateWithLifecycle().value

    LazyColumn(
        modifier = Modifier.fillMaxSize() // Fill the maximum size available
    ) {
        // Display a loading indicator if the data is still loading
        if (isLoading) {
            item {
                ShimmerEffect()
            }
        } else {
            // Only show content when it's loaded
            item {
                if (userMatches?.match_list?.isNotEmpty() == true) {
                    Crousal()
                }
            }

            item {
                if (offerBanners?.offer_list?.isNotEmpty() == true) {
                    InfiniteOffersBannerCarousel(modifier = Modifier.height(200.dp))
                }
            }

            item {
                if (featuredTournament?.matchList?.isNotEmpty() == true) {
                    T20LeagueCardRow()
                }
            }


                UpcomingMatches(upcomingMatches = upcomingMatches, isLoading)

        }
    }
}

