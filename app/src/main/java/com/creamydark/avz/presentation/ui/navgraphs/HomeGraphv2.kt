package com.creamydark.avz.presentation.ui.navgraphs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.creamydark.avz.R
import com.creamydark.avz.domain.model.GoogleAccountDataModel
import com.creamydark.avz.presentation.ui.screen.AboutAppScreen
import com.creamydark.avz.presentation.ui.screen.LessonsScreen
import com.creamydark.avz.presentation.ui.screen.ProfileScreen
import com.creamydark.avz.presentation.ui.screen.ScrollScrollKaScreen
import com.creamydark.avz.presentation.ui.screen.UploadWordsScreen
import com.creamydark.avz.presentation.viewmodels.HomeGraphViewModel
import com.creamydark.avz.presentation.viewmodels.ProfileViewModel
import com.creamydark.avz.presentation.viewmodels.WordScrollViewModel
import com.creamydark.avz.ui.theme.PoppinsBold
import com.creamydark.avz.ui.theme.PoppinsRegular


private data class NavigationItemModel(val route: String, val label: String, val icon: ImageVector)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeGraphv2(
){

    val wordScrollViewModel :WordScrollViewModel = hiltViewModel()
    val homeGraphViewModel :HomeGraphViewModel = hiltViewModel()
    val profileViewModel :ProfileViewModel = hiltViewModel()

    val userData by homeGraphViewModel._userData.collectAsState()

    val navHostController = rememberNavController()
    val bottomNavigationItems = listOf(
        NavigationItemModel("home", "Home", Icons.Default.Home),
        NavigationItemModel("lessons", "Lessons", Icons.Default.Email),
        NavigationItemModel("favorites", "Favorites", Icons.Default.Favorite),
        NavigationItemModel("profile", "Profile", Icons.Default.Person),
    )

    val currentRoute = currentRoute(navHostController)
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = { Text(
                text = getTitle(currentRoute),
                fontFamily = PoppinsBold,
                fontSize = 20.sp
            ) })
        },
        bottomBar = {

            
            NavigationBar(modifier = Modifier.fillMaxWidth()) {
                bottomNavigationItems.forEach {
                        screen->
                    NavigationBarItem(
                        label = {
                            Text(text = screen.label, fontFamily = PoppinsRegular)
                        },
                        selected = currentRoute == screen.route,
                        onClick = {
                            navHostController.navigate(screen.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(navHostController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(imageVector = screen.icon, contentDescription = "")
                        }
                    )
                }
            }
        }
    ) {
        inner ->
        //MainContent

        NavHost(
            navController = navHostController,
            startDestination = "home",
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = inner.calculateBottomPadding(), top = inner.calculateTopPadding())
        ) {
            composable("home") {
                ScrollScrollKaScreen(viewModel = wordScrollViewModel)
            }
            composable("lessons") {
                LessonsScreen()
            }
            composable("favorites") {
//                LessonsScreen()
                Box(modifier = Modifier.fillMaxSize()){
                    Text(modifier = Modifier.align(Alignment.Center), text = "Favorite Screen", fontFamily = PoppinsRegular)
                }
            }
            composable("profile") {
                ProfileScreen(
                    profileViewModel
                ){
                    clicked ->
                    when(clicked){
                        0 ->{
                            navHostController.navigate(route = "upload_words_screen"){
                                launchSingleTop = true
                            }
                        }
                        1 -> {
                            navHostController.navigate(route = "about_screen"){
                                launchSingleTop = true
                            }
                        }
                        2 -> {
                            homeGraphViewModel.signOut()
                        }
                    }
                }
            }
            composable(route = "upload_words_screen"){
                UploadWordsScreen(navHostController = navHostController, viewmodel = wordScrollViewModel)
            }
            composable("about_screen") {
                AboutAppScreen()
            }
        }

    }
}


@Composable
fun currentRoute(navController: NavHostController): String {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    return currentRoute ?: ""
}

fun getTitle(route: String?): String {
    return when (route) {
        "home" -> "Vocabulary"
        "lessons" -> "Check your lessons"
        "favorites" -> "Your Favorites"
        "profile" -> "My Profile"
        "upload_words_screen" -> "Upload Words"
        "about_screen" -> "About"
        else -> "Loading" // Default title
    }
}