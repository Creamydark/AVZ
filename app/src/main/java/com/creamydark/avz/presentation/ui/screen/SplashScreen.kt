package com.creamydark.avz.presentation.ui.screen


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.creamydark.avz.R
import com.creamydark.avz.presentation.ui.navgraphs.Screen
import com.creamydark.avz.ui.theme.Chewy
import com.creamydark.avz.presentation.viewmodels.RootNavGraphViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navHostController: NavHostController){

    Box(Modifier.fillMaxSize()){







//        Text(modifier = Modifier.align(androidx.compose.ui.Alignment.Center) , fontSize = 72.sp , fontFamily = Chewy, text = "AVZ")
        AsyncImage(
            modifier = Modifier.align(Alignment.Center),
            model = R.drawable.app_logo_hd_png,
            contentDescription =""
        )
    }
}