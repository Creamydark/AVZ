package com.creamydark.avz.presentation.ui.navgraphs

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.launch
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.creamydark.avz.domain.ResultType
import com.creamydark.avz.domain.some_api.KimChaewonAPI
import com.creamydark.avz.presentation.ui.screen.AboutAppScreen
import com.creamydark.avz.presentation.ui.screen.AnnouncementsScreen
import com.creamydark.avz.presentation.ui.screen.FavoriteScreen
import com.creamydark.avz.presentation.ui.screen.HomeScreen
import com.creamydark.avz.presentation.ui.screen.LessonsScreen
import com.creamydark.avz.presentation.ui.screen.ProfileScreen
import com.creamydark.avz.presentation.ui.screen.ScrollScrollKaScreen
import com.creamydark.avz.presentation.ui.screen.UploadPostScreen
import com.creamydark.avz.presentation.ui.screen.UploadWordsScreen
import com.creamydark.avz.presentation.ui.screen.WordsSearchScreen
import com.creamydark.avz.presentation.viewmodels.AnnouncementsViewModel
import com.creamydark.avz.presentation.viewmodels.HomeGraphViewModel
import com.creamydark.avz.presentation.viewmodels.ProfileViewModel
import com.creamydark.avz.presentation.viewmodels.WordScrollViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.launch


private data class NavigationItemModel(val route: String, val label: String, val icon: ImageVector)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun HomeGraphv2(
){
    val wordScrollViewModel :WordScrollViewModel = hiltViewModel()
    val homeGraphViewModel :HomeGraphViewModel = hiltViewModel()
    val profileViewModel :ProfileViewModel = hiltViewModel()
    val announcementsViewModel : AnnouncementsViewModel = hiltViewModel()

    val navHostController = rememberNavController()

    val bottomNavigationItems = listOf(
        NavigationItemModel("home_screen", "Home", Icons.Outlined.Home),
        NavigationItemModel("lessons_screen", "Lessons", Icons.Outlined.Email),
//        NavigationItemModel("favorites_screen", "Favorites", Icons.Outlined.FavoriteBorder),
        NavigationItemModel("updates_screen", "Updates", Icons.Outlined.Notifications),
        NavigationItemModel("profile_screen", "Profile", Icons.Outlined.Person)
    )

    val snackbarHostState = remember { SnackbarHostState() }

    val currentRoute = currentRoute(navHostController)

    val scope =rememberCoroutineScope()
    val uploadPostResult by announcementsViewModel.resultUpload.collectAsStateWithLifecycle()
    val uploadResult by wordScrollViewModel.uploadResult.collectAsStateWithLifecycle(initialValue = ResultType.loading())
    val addFavoriteResult by wordScrollViewModel.addFavoriteResult.collectAsStateWithLifecycle(initialValue = ResultType.loading())

    val deletePostResult by announcementsViewModel.deleteResult.collectAsStateWithLifecycle()
    when(deletePostResult){
        is ResultType.Error -> {
            val message = (deletePostResult as ResultType.Error)
            LaunchedEffect(
                key1 = message,
                block = {
                    snackbarHostState.showSnackbar(message = message.exception.message?:"Unknown Error", withDismissAction = true)
                },
            )
        }
        ResultType.Loading -> {

        }
        is ResultType.Success -> {
            val message = (deletePostResult as ResultType.Success<String>).data
            LaunchedEffect(
                key1 = message,
                block = {
                    snackbarHostState.showSnackbar(message = message, withDismissAction = true)
                },
            )
        }
    }
    val userData by wordScrollViewModel.userData.collectAsStateWithLifecycle()
    val voicePermissionState = rememberPermissionState(
        android.Manifest.permission.RECORD_AUDIO
    )
    var searchText by remember {
        mutableStateOf("")
    }

    val voiceInputLauncher = rememberLauncherForActivityResult(KimChaewonAPI()) { result ->
        if (result != null) {
//            capturedSpeech = result
            searchText = result
            wordScrollViewModel.editSearch(result)
        }
    }

    var favoriteOnEditMode by remember {
        mutableStateOf(false)
    }

    when(addFavoriteResult){
        is ResultType.Error -> {
            LaunchedEffect(key1 = addFavoriteResult){
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
    when(uploadPostResult){
        is ResultType.Error -> {
            LaunchedEffect(key1 = uploadPostResult){
                snackbarHostState.showSnackbar(message = (uploadPostResult as ResultType.Error).exception.message?:"Unknown Error", withDismissAction = true)
            }
        }
        ResultType.Loading -> {

        }
        is ResultType.Success -> {
            LaunchedEffect(key1 = uploadPostResult){
                snackbarHostState.showSnackbar(message = (uploadPostResult as ResultType.Success<String>).data, withDismissAction = true)
            }
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
                        "vocabulary_screen" ->{
                            IconButton(
                                onClick = {
                                    navHostController.navigate("favorites_screen"){
                                        launchSingleTop = true
                                    }
                                },
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.FavoriteBorder,
                                    contentDescription = ""
                                )
                            }
                            IconButton(
                                onClick = {
//                                    homeGraphViewModel.startListening()
                                          /*if (voicePermissionState.status.isGranted){
                                              voiceInputLauncher.launch()
                                          }else{
                                              voicePermissionState.launchPermissionRequest()
                                          }*/
                                    navHostController.navigate("search_word_screen")
                                },
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Search,
                                    contentDescription = ""
                                )
                            }
                        }
                        "favorites_screen"->{
                            val icon = if (favoriteOnEditMode){
                                Icons.Outlined.Close
                            }else{
                                Icons.Outlined.Edit
                            }
                            IconButton(
                                onClick = {
                                    favoriteOnEditMode = !favoriteOnEditMode
                                },
                            ) {
                                Icon(
                                    imageVector = icon,
                                    contentDescription = ""
                                )
                            }
                        }
                        "home_screen" -> {
                            val user by profileViewModel.currentFirebaseUser.collectAsStateWithLifecycle()
                            IconButton(
                                onClick = {
                                    navHostController.navigate("profile_screen") {
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
                                }
                            ) {
                                AsyncImage(
                                    modifier = Modifier
                                        .clip(CircleShape),
                                    model = user?.photoUrl,
                                    contentDescription = "",
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                    }
                },
                navigationIcon = {
                    val i = arrayOf("home_screen","lessons_screen","updates_screen","profile_screen")
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
            startDestination = "home_screen",
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = inner.calculateBottomPadding(), top = inner.calculateTopPadding())
        ) {
            composable("home_screen") {
//                ScrollScrollKaScreen(viewModel = wordScrollViewModel)
                val updatesList by announcementsViewModel.postList.collectAsStateWithLifecycle()
                val wordList by wordScrollViewModel._wordsList.collectAsStateWithLifecycle()
                HomeScreen(
                    updatesList = updatesList,
                    wordsList = wordList,
                    navHostController = navHostController
                )
            }
            composable("updates_screen"){
                //announcementsViewModel
                AnnouncementsScreen(viewModel = announcementsViewModel)
            }
            composable("lessons_screen") {
                LessonsScreen()
            }
            composable("favorites_screen") {
                val favList = userData?.favoriteWords?: emptyList()
                val wordsList by wordScrollViewModel._wordsList.collectAsStateWithLifecycle()
                FavoriteScreen(favList = favList, wordList = wordsList,editMode = favoriteOnEditMode){
                    toDelete ->
                    scope.launch {
//                        snackbarHostState.showSnackbar(toDelete)
                        wordScrollViewModel.addFavorites(toDelete)
                    }
                }
            }
            composable("profile_screen") {
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
                            navHostController.navigate(route = "upload_post_screen"){
                                launchSingleTop = true
                            }
                        }
                        2 -> {
                            navHostController.navigate(route = "about_screen"){
                                launchSingleTop = true
                            }
                        }
                        3 -> {
                            homeGraphViewModel.signOut()
                        }
                    }
                }
            }
            composable("upload_words_screen"){
                UploadWordsScreen{
                    word, description, example ->
                    wordScrollViewModel.uploadWordsToFirestore(
                        word, description, example
                    )
                }
            }
            composable("search_word_screen"){

                val searchResult by wordScrollViewModel.searchResultList.collectAsStateWithLifecycle()
                WordsSearchScreen(
                    searchResultList = searchResult,
                    searchText = searchText,
                    onSearchText = {
                        searchText = it
                        if (it.isNotBlank()){
                            wordScrollViewModel.editSearch(it)
                        }
                    },
                    voiceBtn = {
                        if (voicePermissionState.status.isGranted){
                            voiceInputLauncher.launch()
                        }else{
                            voicePermissionState.launchPermissionRequest()
                        }
                    }
                )
            }
            composable("upload_post_screen") {
                UploadPostScreen{
                    caption, image ->
                    announcementsViewModel.post(caption, image)
                }
            }
            composable("about_screen") {
                AboutAppScreen()
            }
            composable("vocabulary_screen") {
                ScrollScrollKaScreen(wordScrollViewModel)
            }
        }

    }
}
/*@Composable
@OptIn(ExperimentalPermissionsApi::class)
private fun LaunchPermissionRequest(
    context: Context,
    permissionState: PermissionState
) {
    val requestPermissionLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission() ){
        permissionState.launchPermissionRequest()
    }
    lifecycleOwner.lifecycleScope.launch {
        val result = requestPermissionLauncher.launch(permissionState.permission)
        permissionState.updatePermissionResult(result)
    }
}*/

@Composable
fun currentRoute(navController: NavHostController): String {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    return currentRoute ?: ""
}

fun getTitle(route: String?): String {
    return when (route) {
        "home_screen" -> "Home"
        "lessons_screen" -> "Check your lessons"
        "favorites_screen" -> "Your Favorites"
        "vocabulary_screen" -> "Vocabulary"
        "profile_screen" -> "My Profile"
        "upload_words_screen" -> "Upload Words"
        "about_screen" -> "About"
        "updates_screen" -> "Updates"
        "upload_post_screen" -> "New Post"
        "search_word_screen" -> "Search Words"
//        "announcements" -> "Announcements"
        else -> "Loading" // Default title
    }
}