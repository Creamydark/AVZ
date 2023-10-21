package com.creamydark.avz.presentation.ui.screen

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.creamydark.avz.presentation.viewmodels.WordScrollViewModel
import com.creamydark.avz.ui.theme.Chewy
import com.creamydark.avz.ui.theme.PoppinsBold
import com.creamydark.avz.ui.theme.PoppinsRegular
import com.creamydark.avz.ui.theme.PoppinsThin


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ScrollScrollKaScreen(viewModel: WordScrollViewModel){

    val yuri by viewModel._wordsList.collectAsState()



    val pagerState = rememberPagerState(pageCount = {
        yuri.size
    })

    Box(modifier = Modifier.fillMaxSize()){
        VerticalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
        ) {page ->
            ScrollItem(
                title = yuri[page].title,
                description = yuri[page].description,
                example = yuri[page].example,
                onClick = { clickeddd ->
                    if (clickeddd == 1){
                       viewModel.speak(yuri[page].title)
                    }
                }
            )
        }
    }
}

@Composable
private fun ScrollItem(
    title:String,
    description:String,
    example:String,
    onClick:(Int) -> Unit
){
    Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            fontFamily = PoppinsBold,
            text = title,
            textAlign = TextAlign.Center,
            fontSize = 32.sp,
        )
        Spacer(modifier = Modifier.size(32.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            fontFamily = PoppinsRegular,
            text = description,
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
        )
        Spacer(modifier = Modifier.size(32.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            fontFamily = PoppinsRegular,
            text = example,
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
        )
        Spacer(modifier = Modifier.size(32.dp))
        Row(Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.Center) {
            IconButton(onClick = { onClick(0) }) {
                Icon(imageVector = Icons.Default.Share, contentDescription ="share" )
            }
            IconButton(onClick = {
                onClick(1)
            }) {
                Icon(imageVector = Icons.Default.Face, contentDescription ="speech" )
            }
            IconButton(onClick = { onClick(2) }) {
                Icon(imageVector = Icons.Default.FavoriteBorder, contentDescription ="like" )
            }
        }
    }
}