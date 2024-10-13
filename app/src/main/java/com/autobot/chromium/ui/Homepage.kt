package com.autobot.chromium.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.autobot.chromium.R
import com.autobot.chromium.database.MainViewModel
import com.autobot.chromium.theme.ascentGradient
import com.autobot.chromium.theme.element
import com.autobot.chromium.theme.grayGradient
import com.autobot.chromium.theme.myAscent
import com.autobot.chromium.theme.secondaryGradient
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun   HomePage() {
    val pagerState = rememberPagerState(pageCount = { 4 })
    var selectedItem by remember { mutableIntStateOf(0) }
    val coroutineScope = rememberCoroutineScope()

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
            .background(secondaryGradient)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.75f)
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .background(Color.White)
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
    val viewModel: MainViewModel = hiltViewModel()
    val walletMoney by viewModel.walletSummary.collectAsStateWithLifecycle(initialValue = null)

    // Remember the balance, using initial state as 0
    val balance = remember { mutableStateOf(0) }

    // Update balance when wallet summary changes
    LaunchedEffect(walletMoney) {
        walletMoney?.let {
            balance.value = it.totalBalance
        }
    }



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
                    .padding(end = 8.dp, start = 8.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .clickable(
                        onClick = {  },
                    )
                    .padding(8.dp)
            )
        },
        title = {
            Text("Hi, Samridhi", fontSize = 16.sp, fontWeight = FontWeight.W600)
        },
        actions = {
            // Define the gradient brush
            val gradientBrush = Brush.horizontalGradient(
                colors = listOf(Color.Magenta, Color.Cyan) // Use your desired colors here
            )

            Row(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(end = 4.dp, start = 4.dp)
                    .border(2.dp, ascentGradient, shape = RoundedCornerShape(8.dp)) // Apply gradient brush to border
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { /* Handle click */ }
                    .padding(vertical = 4.dp, horizontal = 8.dp),  // Padding inside the clickable area
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    "₹${balance.value}",
                    style = TextStyle(
                        fontWeight = FontWeight.W600,
                        fontSize = 12.sp,
                        brush = ascentGradient
                    ),
                    modifier = Modifier.padding(end = 2.dp)
                )

                Image(
                    painter = painterResource(R.drawable.ic_wallet_gradient),
                    contentDescription = "Star Icon",
                    // Apply the gradient directly as the tint
                )

            }
            Box(
                modifier = Modifier

                    .padding(end = 16.dp) // Set the size of the box to your desired icon size
                    .clip(RoundedCornerShape(64.dp))
                    .clickable(
                        onClick = { /* Handle notification click */ },
                    ).padding(8.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_bell_gradient),
                    contentDescription = "Star Icon",
                     // Apply the gradient directly as the tint
                )
                Box(
                    modifier = Modifier
                        .size(8.dp) // Size of the notification dot
                        .background(element, shape = CircleShape) // Create a circular dot
                        .align(Alignment.TopEnd) // Position at the top end of the wallet icon
                        .padding(end = 6.dp) // Optional padding
                )
            }
        },

        )
}

@Composable
fun BottomNavigationBar(selectedItem: Int, onItemSelected: (Int) -> Unit) {
    NavigationBar(containerColor = Color.White,
        modifier = Modifier.shadow(16.dp, RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))) {
        val items = listOf("Home", "My Matches", "Winners", "My Space")

        // Define the corresponding icons for both selected and unselected states
        val icons = listOf(
            Pair(painterResource(R.drawable.ic_home_outline), painterResource(R.drawable.ic_home_filled)),
            Pair(painterResource(R.drawable.ic_star_outline), painterResource(R.drawable.ic_star_filled)),
            Pair(painterResource(R.drawable.ic_cup_outline), painterResource(R.drawable.ic_cup_filled)),
            Pair(painterResource(R.drawable.ic_user), painterResource(R.drawable.ic_user))
        )


        // Iterate through the items to create NavigationBarItems
        items.forEachIndexed { index, title ->
            NavigationBarItem(

                selected = selectedItem == index,
                onClick = { onItemSelected(index)

                          },
                icon = {
                    // Use the filled icon if selected, otherwise use the outlined icon
                    Icon(
                        painter = if (selectedItem == index) icons[index].second else icons[index].first,
                        contentDescription = title,
                        tint = Color.Unspecified,
                        modifier = Modifier.alpha(0.7f)
                    )
                },
                label = {
                    Text(
                        text = title,
                        style = TextStyle(
                            fontWeight = FontWeight.W900,
                            fontSize = 12.sp,

                        brush = if (selectedItem == index) ascentGradient else grayGradient
                        ),

                    )
                },
                        colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.Transparent,
                unselectedIconColor = Color.Transparent,
                selectedTextColor = Color.Transparent,
                indicatorColor = myAscent.copy(alpha = 0.1f)
            )
            )
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


