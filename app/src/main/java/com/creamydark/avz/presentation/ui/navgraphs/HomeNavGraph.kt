package com.creamydark.avz.presentation.ui.navgraphs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.creamydark.avz.domain.ResultType
import com.creamydark.avz.domain.model.UserData
import com.creamydark.avz.presentation.ui.screen.AboutAppScreen
import com.creamydark.avz.presentation.ui.screen.LessonsScreen
import com.creamydark.avz.presentation.ui.screen.RegisterPart2Screen
import com.creamydark.avz.presentation.ui.screen.ScrollScrollKaScreen
import com.creamydark.avz.presentation.ui.screen.UploadWordsScreen
import com.creamydark.avz.presentation.viewmodels.HomeGraphViewModel
import com.creamydark.avz.presentation.viewmodels.RegisterViewModel
import com.creamydark.avz.presentation.viewmodels.RootNavGraphViewModel
import com.creamydark.avz.presentation.viewmodels.WordScrollViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


private data class DrawerItem(
    val title:String,
    val icon:ImageVector
)


private val _drawerItems = listOf(
    DrawerItem("Home", icon = Icons.Default.Home),
    DrawerItem("Lessons", icon = Icons.Default.List),
    DrawerItem("Upload Words", icon = Icons.Default.Send),
    DrawerItem("Favorite", icon = Icons.Default.Favorite),
    DrawerItem("Sign Out", icon = Icons.Default.Close),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun setUpHomeGraph(){


    val viewModel = hiltViewModel<HomeGraphViewModel>()

    val wordsViewModel = hiltViewModel<WordScrollViewModel>()

    val navHostController = rememberNavController()
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val selectedItem = remember { mutableStateOf(_drawerItems[0]) }

    val userData = viewModel._userExtraData.collectAsState().value




    ModalNavigationDrawer(
        drawerContent = {
                        ModalDrawerSheet {
                            Spacer(Modifier.height(12.dp))
                            Column(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(NavigationDrawerItemDefaults.ItemPadding)) {
                                Text(
                                    text = "Hi! ",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(Modifier.height(8.dp))
                                Text(
                                    text = "${userData?.name}",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Normal
                                )
                                Spacer(Modifier.height(18.dp))
                                Text(
                                    text = "Here's an overview of your properties",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Normal,
                                    color = MaterialTheme.colorScheme.onSurface.copy(0.7f)
                                )
                            }
                            Spacer(Modifier.height(12.dp))
                            _drawerItems.forEach { item ->
                                NavigationDrawerItem(
                                    icon = { Icon(item.icon, contentDescription = null) },
                                    label = { Text(item.title) },
                                    selected = item == selectedItem.value,
                                    onClick = {
                                        scope.launch { drawerState.close() }
                                        selectedItem.value = item
                                        when(item.title){
                                            "Home" -> {
                                                navHostController.navigate(Screen.ScrollScroll.route)
                                            }
                                            "Upload Words" -> {
                                                navHostController.navigate(Screen.UploadWordsScreen.route)
                                            }
                                            "Lessons" -> {
                                                navHostController.navigate(Screen.Lessons.route)
                                            }

                                            "Sign Out" -> {
                                                viewModel.signOut()
                                            }
                                        }
                                    },
                                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                                )
                            }
                        }
        },
        drawerState = drawerState
    ) {
        //MainContent

        MaterialTheme{
            Scaffold(Modifier.fillMaxSize(),
                topBar = {
                    TopAppBar(title = { /*TODO*/ }, navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                drawerState.open()
                            }
                        }) {
                            Icon(imageVector = Icons.Default.Menu, contentDescription = "")
                        }
                    })
                }) {
                    innerpadding ->
                NavHost(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = innerpadding.calculateTopPadding()),
                    navController = navHostController,
                    startDestination = Screen.ScrollScroll.route
                ){
                    composable(route = Screen.Lessons.route){
                        LessonsScreen(navHostController)
                    }
                    composable(route = Screen.AboutApp.route){
                        AboutAppScreen(navHostController)
                    }
                    composable(route = Screen.ScrollScroll.route){

                        ScrollScrollKaScreen(navHostController,wordsViewModel)
                    }
                    composable(route = Screen.UploadWordsScreen.route){

                        UploadWordsScreen(navHostController = navHostController, viewmodel = wordsViewModel)
                    }
                }
            }
        }
    }
}
