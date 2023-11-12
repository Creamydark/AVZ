package com.creamydark.avz.presentation.ui.screen


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage
import com.creamydark.avz.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navtoNextScreen:()->Unit){

    var state by remember {
        mutableStateOf(true)
    }
    LaunchedEffect(key1 = state ){
        delay(2000)
        navtoNextScreen()
        state = false
    }

    Box(Modifier.fillMaxSize()){
//        Text(modifier = Modifier.align(androidx.compose.ui.Alignment.Center) , fontSize = 72.sp , fontFamily = Chewy, text = "AVZ")
        AsyncImage(
            modifier = Modifier.align(Alignment.Center),
            model = R.drawable.app_logo_hd_png,
            contentDescription =""
        )
    }
}