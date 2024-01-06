package com.creamydark.avz.presentation.ui.customcomposables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.creamydark.avz.domain.model.WordsDataModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask
import kotlin.random.Random

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun BigBannerWithAutoScrollLayoutComposable(
    modifier: Modifier = Modifier,
    list: List<WordsDataModel> = emptyList(),
    onBannerClicked:()->Unit
) {
    val pagerState = rememberPagerState {
        list.size
    }
    var currentPage by remember { mutableStateOf(0) }


    val coroutineScope = rememberCoroutineScope()
    DisposableEffect(list) {
        // Generate a random number every 5 seconds.
        val job = coroutineScope.launch {
            while (true){
                delay(5000)
                if (pagerState.pageCount > 1 ){
                    currentPage = (currentPage + 1) % pagerState.pageCount
                }
            }
        }
        onDispose {
            job.cancel()
        }
    }

    LaunchedEffect(
        key1 = currentPage,
        block = {
            pagerState.animateScrollToPage(currentPage)
        },
    )

    ElevatedCard(
        onClick = {
            onBannerClicked()
        },
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                modifier = Modifier
                    .background(color = Color(0xff2C2C2C), shape = RoundedCornerShape(16.dp))
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                text = "Vocabulary",
                style = MaterialTheme.typography.titleMedium,
                fontSize = 16.sp,
                color = Color.White.copy(alpha = 0.7f)
            )
            Spacer(modifier = Modifier.height(13.dp))
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopStart){
                if (list.isNotEmpty()){
                    HorizontalPager(
                        state = pagerState,
                        verticalAlignment = Alignment.Top,
                        userScrollEnabled = false,
                    ) {
                            pos->
                        Column(
                        ) {
                            Text(
                                text = list[pos].title,
                                style = MaterialTheme.typography.displayLarge,
                                maxLines = 1
                            )
//                FadingText(text = list[randPos].description)
                            Text(
                                modifier = Modifier,
                                text = list[pos].description,
                                style = MaterialTheme.typography.bodyMedium,
                                maxLines = 3
                            )
                        }
                    }
                }else{
                    Text(text = "Hi! :)", style = MaterialTheme.typography.headlineMedium)
                }
            }

        }
    }
}