package com.creamydark.avz.presentation.ui.navgraphs



sealed class Screen(val route :String){
    object Splash: Screen(route = "splash_screen")
    object Lessons: Screen(route = "lessons_screen")
    object AboutApp: Screen(route = "About_screen")
    object ScrollScroll: Screen(route = "ScrollScroll_screen")
    object UploadWordsScreen: Screen(route = "UploadWordsScreen_screen")
    object UserLoginScreen: Screen(route = "UserLoginScreen_screen")
    object UserRegisterScreen: Screen(route = "UserRegisterScreen_screen")
    object LoginRegisterGraph: Screen(route = "LoginRegisterGraph_screen")
    object HomeGraph: Screen(route = "HomeGraph_screen")
}
