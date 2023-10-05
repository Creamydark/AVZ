package com.creamydark.avz.presentation.ui.screen

import android.os.Build
import android.speech.tts.TextToSpeech
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.creamydark.avz.presentation.viewmodels.WordScrollViewModel
import java.util.Locale


lateinit var textToSpeech: TextToSpeech

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ScrollScrollKaScreen(navHostController: NavHostController,myviewModel: WordScrollViewModel = viewModel()){

    val yuri by myviewModel.itemList.collectAsState()

    val pagerState = rememberPagerState(pageCount = {
        yuri.items.size
    })

    textToSpeech = TextToSpeech(LocalContext.current) {
        if (it != TextToSpeech.ERROR) {
            textToSpeech.language = Locale.ENGLISH
        }
    }



    Scaffold() { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()){
            VerticalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = innerPadding.calculateTopPadding(),
                        bottom = innerPadding.calculateBottomPadding()
                    )
            ) {page ->
                ScrollItem(
                    title = yuri.items[page].title,
                    description = yuri.items[page].description,
                    example = yuri.items[page].example,
                    onClick = { clickeddd ->
                        if (clickeddd == 1){
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                textToSpeech.speak(yuri.items[page].title,TextToSpeech.QUEUE_FLUSH,null,null);
                            } else {
                                textToSpeech.speak(yuri.items[page].title, TextToSpeech.QUEUE_FLUSH, null);
                            }
                        }
                    }
                )
            }

        }
    }
}

