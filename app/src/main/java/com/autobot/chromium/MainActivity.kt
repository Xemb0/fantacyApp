package com.autobot.chromium

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.autobot.chromium.database.ViewModel
import com.autobot.chromium.theme.MyAppThemeComposable
import com.autobot.chromium.ui.HomePage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: ViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyAppThemeComposable {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                    ,
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = HomePage
                    ) {


                        composable<HomePage> {HomePage(
                                viewModel = mainViewModel
//                                onSignUpClick = { userData ->
//                                    navController.navigate(
//                                        NavScreenCreateRoom(
//                                            userId = userData.userId?:"",
//                                            username = userData.username,
//                                            profilePictureUrl = userData.profilePictureUrl
//                                        )
//                                    )
//                                }
                            )
                        }

                }
            }
        }
    }







    }

}



//@Serializable
//data class HomePage(
//    val userId: String,
//    val username: String?,
//    val profilePictureUrl: String?
//)

@Serializable
data object HomePage