package com.autobot.chromium.ui

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.autobot.chromium.R
import com.autobot.chromium.database.ViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(viewModel: ViewModel = hiltViewModel()) {
    val pagerState = rememberPagerState(pageCount = { 4 }) // Number of pages
    var selectedItem by remember { mutableIntStateOf(0) } // Track selected tab
    val coroutineScope = rememberCoroutineScope() // Create a coroutine scope

    LaunchedEffect(pagerState.currentPage) {
        selectedItem = pagerState.currentPage
    }

    Scaffold(
        topBar = { TopBar() },
        bottomBar = {
            BottomNavigationBar(
                selectedItem = selectedItem,
                onItemSelected = { index ->
                    selectedItem = index
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }
            )
        }
    ) { paddingValues ->
        CurvedBackgroundContent(pagerState, paddingValues)
    }
}
@Composable
fun CurvedBackgroundContent(pagerState: PagerState, paddingValues: PaddingValues) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Blue) // Transparent background for the top half
    ) {
        // This is the curved background starting from the middle
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f) // This starts the box from the middle of the screen
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)) // Customize the curve as per your design
                .background(Color.White) // Curved background color
        )

        // Pager content goes above the background
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .align(Alignment.TopCenter) // Ensures the pager content is aligned on top
        ) {
            // Horizontal Pager
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxSize(),
                verticalAlignment = Alignment.Top,
            ) { page ->
                when (page) {
                    0 -> HomeContent() // Home tab
                    1 -> MyMatchesContent() // My Matches tab
                    2 -> WinnersContent() // Winners tab
                    3 -> MySpaceContent() // My Space tab
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    TopAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomEnd = 16.dp, bottomStart = 16.dp))
        ,
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            titleContentColor = Color.Black
        ),
        navigationIcon = {
            Icon(
                painter = painterResource(R.drawable.ic_hambuger),
                contentDescription = "Home",
                modifier = Modifier
                    .padding(end = 8.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .clickable(
                        onClick = {  },
                    )
                    .padding(8.dp)
            )
        },
        title = {
            Text("Hi, Samridhi")
        },
        actions = {
            Row(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(8.dp)
                    .border(2.dp, color = Color.Red, shape = RoundedCornerShape(8.dp))
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { /* Handle wallet click */ }
                    .padding(vertical = 4.dp, horizontal = 8.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "₹50",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.Black
                    ),
                    modifier = Modifier.padding(end = 4.dp)
                )
                Icon(
                    modifier = Modifier,
                    painter = painterResource(R.drawable.ic_wallet),
                    contentDescription = "Wallet"
                )
            }
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(64.dp)) // Set the size of the box to your desired icon size
                    .clickable(
                        onClick = { /* Handle notification click */ },
                    )
                    .padding(8.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_notification),
                    contentDescription = "Notification"
                )
                Box(
                    modifier = Modifier
                        .size(10.dp) // Size of the notification dot
                        .background(Color.Red, shape = CircleShape) // Create a circular dot
                        .align(Alignment.TopEnd) // Position at the top end of the wallet icon
                        .padding(end = 4.dp) // Optional padding
                )
            }
        },

        )
}


@Composable
fun BottomNavigationBar(selectedItem: Int, onItemSelected: (Int) -> Unit) {
    NavigationBar(containerColor = Color.White) {
        val items = listOf("Home", "My Matches", "Winners", "My Space")
        val icons = listOf(Icons.Default.Home, Icons.Default.Star, Icons.Default.Star, Icons.Default.Person)

        items.forEachIndexed { index, title ->
            NavigationBarItem(
                selected = selectedItem == index,
                onClick = { onItemSelected(index) },
                icon = { Icon(icons[index], contentDescription = title) },
                label = { Text(title) }
            )
        }
    }
}

@Composable
fun MatchesTabs() {
    val pagerState = rememberPagerState(pageCount = { 4 })
    Column {
        // Tabs
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = Color.Transparent,
            contentColor = Color.Black
        ) {
            listOf("Home", "My Matches", "Winners", "My Space").forEachIndexed { index, title ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = { /* handle tab click */ },
                    text = { Text(title) }
                )
            }
        }

        // Pager Content
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            when (page) {
                0 -> HomeContent()
                1 -> MyMatchesContent()
                2 -> WinnersContent()
                3 -> MySpaceContent()
            }
        }
    }
}

@Composable
fun MySpaceContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("My Space")
    }
}

@Composable
fun WinnersContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Winners")
    }
}

@Composable
fun MyMatchesContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("My Matches")
    }
}


@Composable
fun DotIndicatorWithAnimation(totalDots: Int, selectedIndex: Int) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
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
            .background(if (isSelected) Color.Magenta else Color.Gray)
    )
}

@Composable
fun MatchCard(league: String, match: String, time: String, offer: String) {
    Card(
        modifier = Modifier.padding(horizontal = 38.dp)
            .fillMaxWidth() // Adjust height as necessa
        , elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(league, color = Color.Blue, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(match, color = Color.Black, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(time, color = Color.Red, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(offer, color = Color.Gray, fontSize = 12.sp)
        }
    }
}
@Composable
fun InfiniteOffersBannerCarousel(modifier: Modifier = Modifier) {
    val offerItems = listOf(
        "Offer 1", "Offer 2", "Offer 3", "Offer 4", "Offer 5"
    )

    val totalPages = offerItems.size
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
                .padding(horizontal = 8.dp) // Spacing between items // Occupies 1/3rd of the width for 3 visible cards
            ,
        ) {
            OfferCard(offerTitle = offerItems[actualPage], description = "Get an amazing discount!")
        }
    }

    // Auto-scroll for infinite loop
    LaunchedEffect(pagerState) {
        coroutineScope.launch {

        }
    }
}


@Composable
fun OfferCard(offerTitle: String, description: String) {
    Card(
        modifier = Modifier.width(400.dp) // Adjust width as needed
            .clip(RoundedCornerShape(16.dp)) // Rounded corners
        , // Add shadow/elevation
        elevation = CardDefaults.cardElevation(8.dp) ,
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE7E8F0)),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(offerTitle, style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold))
            Spacer(modifier = Modifier.height(8.dp))
            Text(description, style = TextStyle(fontSize = 16.sp, color = Color.Gray))
        }
    }
}

fun lerp(start: Float, stop: Float, fraction: Float): Float {
    return start + fraction * (stop - start)
}