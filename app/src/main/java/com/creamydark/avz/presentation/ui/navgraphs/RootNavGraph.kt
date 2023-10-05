package com.creamydark.avz.presentation.ui.navgraphs

import androidx.compose.foundation.layout.fillMaxSize
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
import com.creamydark.avz.presentation.ui.screen.LoginScreen3
import com.creamydark.avz.presentation.ui.screen.RegisterPart2Screen
import com.creamydark.avz.presentation.ui.screen.SplashScreen
import com.creamydark.avz.presentation.viewmodels.RootNavGraphViewModel
import kotlinx.coroutines.delay


@Composable
fun SetUpNavGraph(navHostController: NavHostController){

    val viewmodel : RootNavGraphViewModel = hiltViewModel()

    val _googleClientUser = viewmodel._googleClientUser.collectAsState().value
    val _userExtraDataExist = viewmodel._userExtraDataExist.collectAsState().value

    NavHost(modifier = Modifier.fillMaxSize(),navController = navHostController, startDestination = Screen.Splash.route ){
        composable(route = Screen.Splash.route){
            SplashScreen(navHostController)
        }
        someGraph(navHostController)
        composable(route = Screen.HomeGraph.route){
            setUpHomeGraph()
        }
    }

    if (_googleClientUser){
        if (_userExtraDataExist){
            navHostController.navigate(route = Screen.HomeGraph.route){
                launchSingleTop = true
                popUpTo(route = Screen.LoginRegisterGraph.route){
                    inclusive = true
                }
            }
        }else{
            navHostController.navigate(route = Screen.UserRegisterScreen.route){
                launchSingleTop = true
                popUpTo(route = Screen.LoginRegisterGraph.route){
                    inclusive = true
                }
            }
        }
    }else{
        navHostController.navigate(route = Screen.LoginRegisterGraph.route){
            launchSingleTop = true
            popUpTo(route = Screen.Splash.route){
                inclusive = true
            }
        }
    }
}

fun NavGraphBuilder.someGraph(navHostController: NavHostController){
    navigation(startDestination = Screen.UserLoginScreen.route, route = Screen.LoginRegisterGraph.route){
        composable(route = Screen.UserLoginScreen.route){
            val viewModel = hiltViewModel<RootNavGraphViewModel>()
            LoginScreen3(navHostController, viewModel = viewModel)
        }
        composable(route = Screen.UserRegisterScreen.route){
//            UserRegisterScreen(navHostController = navHostController)
            RegisterPart2Screen()
        }
    }
}