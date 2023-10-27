package com.creamydark.avz.presentation.ui.navgraphs

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.creamydark.avz.domain.ResultType
import com.creamydark.avz.presentation.ui.customcomposables.OneTimeUserTypeSelectionDialog
import com.creamydark.avz.presentation.ui.screen.AboutAppScreen
import com.creamydark.avz.presentation.ui.screen.FavoriteScreen
import com.creamydark.avz.presentation.ui.screen.LessonsScreen
import com.creamydark.avz.presentation.ui.screen.ProfileScreen
import com.creamydark.avz.presentation.ui.screen.ScrollScrollKaScreen
import com.creamydark.avz.presentation.ui.screen.UploadWordsScreen
import com.creamydark.avz.presentation.viewmodels.HomeGraphViewModel
import com.creamydark.avz.presentation.viewmodels.ProfileViewModel
import com.creamydark.avz.presentation.viewmodels.WordScrollViewModel
import kotlinx.coroutines.launch


private data class NavigationItemModel(val route: String, val label: String, val icon: ImageVector)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeGraphv2(
){
    val wordScrollViewModel :WordScrollViewModel = hiltViewModel()
    val homeGraphViewModel :HomeGraphViewModel = hiltViewModel()
    val profileViewModel :ProfileViewModel = hiltViewModel()

    val navHostController = rememberNavController()

    val bottomNavigationItems = listOf(
        NavigationItemModel("home", "Home", Icons.Outlined.Home),
        NavigationItemModel("lessons", "Lessons", Icons.Outlined.Email),
        NavigationItemModel("favorites", "Favorites", Icons.Outlined.FavoriteBorder),
        NavigationItemModel("profile", "Profile", Icons.Outlined.Person)
    )


    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val currentRoute = currentRoute(navHostController)

    val uploadResult: ResultType<String> by wordScrollViewModel.uploadResult.collectAsStateWithLifecycle(initialValue = ResultType.loading())
    val addFavoriteResult by wordScrollViewModel.addFavoriteResult.collectAsStateWithLifecycle(initialValue = ResultType.loading())
    val userData by wordScrollViewModel.userData.collectAsStateWithLifecycle()
    when(addFavoriteResult){
        is ResultType.Error -> {
            LaunchedEffect(key1 = addFavoriteResult ){
                val errorMessage = (addFavoriteResult as ResultType.Error).exception.message
                snackbarHostState.showSnackbar(errorMessage?:"Unknown Error", withDismissAction = true)
            }
        }
        ResultType.Loading -> {

        }
        is ResultType.Success -> {

        }
    }
    when(uploadResult){
        is ResultType.Error -> {
            LaunchedEffect(
                uploadResult,
                block = {
                    val message = (uploadResult as ResultType.Error).exception
                    snackbarHostState.showSnackbar(
                        message = message.message ?: "Unknown Error",
                        withDismissAction = true,
                    )
                },
            )
        }
        ResultType.Loading -> {

        }
        is ResultType.Success -> {
            LaunchedEffect(
                key1 = uploadResult,
                block = {
                    val data = (uploadResult as ResultType.Success<String>).data
                    snackbarHostState.showSnackbar(message = data, withDismissAction = true)
                }
            )
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = getTitle(currentRoute)
                    )
                },
                actions = {
                    when(currentRoute){
                        "home" ->{
                            IconButton(
                                onClick = {
                                    navHostController.navigate("announcements_screen"){
                                        launchSingleTop = true
                                    }
                                },
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Notifications,
                                    contentDescription = ""
                                )
                            }
                        }
                    }
                },
                navigationIcon = {
                    val i = arrayOf("home","lessons","favorites","profile")
                    if (!i.contains(currentRoute)){
                        IconButton(
                            onClick = {
                                navHostController.popBackStack()
                            },
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.ArrowBack,
                                contentDescription = ""
                            )
                        }
                    }
                }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        bottomBar = {
            NavigationBar(
                modifier = Modifier.fillMaxWidth(),
                containerColor = Color.Transparent
            ) {
                bottomNavigationItems.forEach {
                        screen->
                    NavigationBarItem(
                        label = {
                            Text(text = screen.label, style = MaterialTheme.typography.labelSmall)
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
            composable("announcements_screen"){
                Box(modifier = Modifier.fillMaxSize()){
                    Text(modifier = Modifier.align(alignment = Alignment.Center), text = "Announcement Screen")
                }
            }
            composable("lessons") {
                LessonsScreen()
            }
            composable("favorites") {
                val favList = userData?.favoriteWords?: emptyList()
                FavoriteScreen(favList = favList)
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
                UploadWordsScreen{
                    word, description, example ->
                    wordScrollViewModel.uploadWordsToFirestore(
                        word, description, example
                    )
                }
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
        "announcements_screen" -> "Announcements"
//        "announcements" -> "Announcements"
        else -> "Loading" // Default title
    }
}