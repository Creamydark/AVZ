package com.creamydark.avz.presentation.ui.navgraphs



sealed class RootNavScreens(val route :String){
    object Splash: RootNavScreens(route = "splash_screen")
    object UserLoginRootNavScreens: RootNavScreens(route = "UserLoginScreen_screen")
    object HomeGraph: RootNavScreens(route = "HomeGraph_screen")
}
