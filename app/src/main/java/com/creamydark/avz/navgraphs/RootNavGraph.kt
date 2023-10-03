package com.creamydark.avz.navgraphs

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.creamydark.avz.navgraphs.screens.LoginScreen2
import com.creamydark.avz.navgraphs.screens.SplashScreen
import com.creamydark.avz.navgraphs.screens.UserRegisterScreen2
import com.creamydark.avz.viewmodels.RootNavGraphViewModel
import kotlinx.coroutines.delay


@Composable
fun SetUpNavGraph(navHostController: NavHostController){

    val viewmodel : RootNavGraphViewModel = hiltViewModel()

    val userState_ = viewmodel._userState.collectAsState().value



    //awdadaddawdsawdsawd

    NavHost(modifier = Modifier.fillMaxSize(),navController = navHostController, startDestination = Screen.Splash.route ){
        composable(route = Screen.Splash.route){
            SplashScreen(navHostController)
        }
        someGraph(navHostController)
        composable(route = Screen.HomeGraph.route){
            setUpHomeGraph()
        }
    }
    if (userState_){
        LaunchedEffect(true){
            delay(3000)
            navHostController.navigate(route = Screen.HomeGraph.route){
                launchSingleTop = true
                popUpTo(route = Screen.LoginRegisterGraph.route){
                    inclusive = true
                }
            }
        }
    }else if (navHostController.currentDestination?.route == Screen.HomeGraph.route){
        navHostController.navigate(route = Screen.LoginRegisterGraph.route){
            launchSingleTop = true
            popUpTo(route = Screen.HomeGraph.route){
                inclusive = true
            }
        }
    }else{
        LaunchedEffect(true){
            delay(3000)
            navHostController.navigate(route = Screen.LoginRegisterGraph.route){
                launchSingleTop = true
                popUpTo(route = Screen.Splash.route){
                    inclusive = true
                }
            }
        }
    }
}

fun NavGraphBuilder.someGraph(navHostController: NavHostController){
    navigation(startDestination = Screen.UserLoginScreen.route, route = Screen.LoginRegisterGraph.route){
        composable(route = Screen.UserLoginScreen.route){
            //UserLoginScreen(navHostController)
            LoginScreen2(navHostController)
        }
        composable(route = Screen.UserRegisterScreen.route){
//            UserRegisterScreen(navHostController = navHostController)
            UserRegisterScreen2()
        }
    }
}