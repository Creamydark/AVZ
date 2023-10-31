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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.creamydark.avz.presentation.viewmodels.WordScrollViewModel
import com.creamydark.avz.ui.theme.Chewy
import com.creamydark.avz.ui.theme.PoppinsBold
import com.creamydark.avz.ui.theme.PoppinsRegular
import com.creamydark.avz.ui.theme.PoppinsThin


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ScrollScrollKaScreen(viewModel: WordScrollViewModel){

    val yuri by viewModel._wordsList.collectAsStateWithLifecycle()
    val userData by viewModel.userData.collectAsStateWithLifecycle()
    val favList = userData?.favoriteWords?:emptyList()
    val pagerState = rememberPagerState(pageCount = {
        yuri.size
    })

    /*if (pagerState.currentPage==yuri.size){
        viewModel.generateKaseLastPageNa()
    }*/


    Box(modifier = Modifier.fillMaxSize()){
        if (yuri.isNotEmpty()){
            VerticalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 32.dp)
            ) {page ->
                val likeImageState = if(favList.contains(yuri[page].title)){
                    Icons.Outlined.Favorite
                }else{
                    Icons.Outlined.FavoriteBorder
                }
                ScrollItem(
                    title = yuri[page].title,
                    description = yuri[page].description,
                    example = yuri[page].example,
                    likeVectorIconState = likeImageState,
                    onClick = { clickeddd ->
                        when(clickeddd){
                            0->{

                            }
                            1->{
                                viewModel.speak(yuri[page].title)
                            }
                            2->{
                                val title = yuri[page].title
                                viewModel.addFavorites(
                                    title = title
                                )
                            }
                        }
                    }
                )
            }
        }else{
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                text = "Empty Vocabulary",
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun ScrollItem(
    title:String,
    description:String,
    example:String,
    likeVectorIconState:ImageVector,
    onClick:(Int) -> Unit
){
    val typography = MaterialTheme.typography
    Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = title,
            textAlign = TextAlign.Center,
            style = typography.headlineLarge
        )
        Spacer(modifier = Modifier.size(32.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = description,
            textAlign = TextAlign.Center,
            style = typography.bodyLarge
        )
        Spacer(modifier = Modifier.size(32.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = example,
            textAlign = TextAlign.Center,
            style = typography.bodySmall
        )
        Spacer(modifier = Modifier.size(32.dp))
        Row(Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.Center) {
            IconButton(onClick = {
                onClick(0)
            }) {
                Icon(imageVector = Icons.Default.Share, contentDescription ="share" )
            }
            IconButton(onClick = {
                onClick(1)
            }) {
                Icon(imageVector = Icons.Default.Face, contentDescription ="speech" )
            }
            IconButton(onClick = {
                onClick(2)
            }) {
                Icon(imageVector = likeVectorIconState, contentDescription ="like" )
            }
        }
    }
}