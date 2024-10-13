package com.autobot.chromium.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.autobot.chromium.R
import com.autobot.chromium.database.MainViewModel
import kotlinx.coroutines.launch

@Composable
fun InfiniteOffersBannerCarousel(modifier: Modifier = Modifier) {
    val viewModel = hiltViewModel<MainViewModel>()
    val currentOffers = viewModel.currentOffers.collectAsStateWithLifecycle().value?.offer_list ?: emptyList()

    // Add this line to avoid divide by zero
    if (currentOffers.isEmpty()) return // Exit early if there are no offers

    val totalPages = currentOffers.size
    val infinitePageCount = Int.MAX_VALUE // Simulated infinite number of pages
    val pagerState = rememberPagerState(
        pageCount = { infinitePageCount },
        initialPage = infinitePageCount / 2 // Start in the middle
    )
    val coroutineScope = rememberCoroutineScope()

    HorizontalPager(
        state = pagerState,
        contentPadding = PaddingValues(horizontal = 48.dp), // Adjust for 3 visible items
        modifier = Modifier
            .fillMaxWidth() // Adjust the height of the carousel
            .padding(vertical = 16.dp, horizontal = 0.dp)
    ) { index ->
        // Use modulo to calculate the actual page index in the offerItems list
        val actualPage = index % totalPages
        val pageOffset = pagerState.currentPageOffsetFraction

        // Calculate scaling for central and side items
        val scale = lerp(
            start = 0.8f, // Scale for non-centered items
            stop = 1.0f,  // Scale for the centered item
            fraction = 1f - kotlin.math.abs(pageOffset)
        )

        Box(
            modifier = Modifier
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                    alpha = lerp(0.5f, 1f, scale) // Optional: adjust alpha as well
                }
                .padding(horizontal = 8.dp) // Spacing between items
        ) {
            OfferCard(offerTitle = currentOffers[actualPage].type, offerUrl = currentOffers[actualPage].offer_banner_url)
        }
    }

    // Auto-scroll for infinite loop
    LaunchedEffect(pagerState) {
        coroutineScope.launch {
            // Add your auto-scroll logic here if needed
        }
    }
}



@Composable
fun OfferCard(offerTitle: String, offerUrl: String) {
    Card(
        modifier = Modifier
            .width(400.dp) // Adjust width as needed
            .clip(RoundedCornerShape(16.dp)), // Rounded corners
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE7E8F0)),
    ) {
        // Load image using Coil with loading and error handling
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(offerUrl)
                .error(R.drawable.ic_clock) // Add an error drawable
                .build(),
            contentDescription = offerTitle,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth()
        )
    }
}


fun lerp(start: Float, stop: Float, fraction: Float): Float {
    return start + fraction * (stop - start)
}