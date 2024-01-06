package com.creamydark.avz.presentation.ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.creamydark.avz.R
import com.creamydark.avz.domain.model.WordsDataModel
import com.creamydark.avz.presentation.viewmodels.WordScrollViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ScrollScrollKaScreen(viewModel: WordScrollViewModel){

    val userdata by viewModel.userData.collectAsStateWithLifecycle()
    val yuri by viewModel._wordsList.collectAsStateWithLifecycle()
    val favList = userdata?.favoriteWords?: emptyList()
    val wordList by remember(yuri) {
        derivedStateOf {
            yuri.shuffled()
        }
    }
    val pagerState = rememberPagerState(pageCount = {
        wordList.size
    })
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showBottomSheet by remember { mutableStateOf(false) }
    var selectedWord by remember {
        mutableStateOf(WordsDataModel())
    }
    val scope = rememberCoroutineScope()

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
                val likeImageState = if(favList.contains(wordList[page].title)){
                    Icons.Outlined.Favorite
                }else{
                    Icons.Outlined.FavoriteBorder
                }
                ScrollItem(
                    userType = userdata?.student?:true,
                    title = wordList[page].title,
                    description = wordList[page].description,
                    example = wordList[page].example,
                    likeVectorIconState = likeImageState,
                    onClick = { clickeddd ->
                        when(clickeddd){
                            0->{

                            }
                            1->{
                                viewModel.speak(wordList[page].title)
                            }
                            2->{
                                val title = wordList[page].title
                                viewModel.addFavorites(
                                    title = title
                                )
                            }
                            3->{
                                selectedWord = wordList[page]
                                showBottomSheet = true
                            }
                            4->{
                                viewModel.deleteWord(
                                    wordList[page]
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
        if (showBottomSheet){
            ModalBottomSheet(
                modifier = Modifier.imePadding(),
                sheetState = sheetState,
                onDismissRequest = {
                    showBottomSheet = false
                }
            ) {
                UploadWordsScreen(
                    data = selectedWord
                ){
                    data ->
                    viewModel.updateWord(data)
                    scope.launch {
                        sheetState.hide()
                    }.invokeOnCompletion {
                        showBottomSheet = false
                    }
                }
            }
        }
    }

}

@Composable
private fun ScrollItem(
    userType:Boolean=true,
    title:String,
    description:String,
    example:String,
    likeVectorIconState:ImageVector,
    onClick:(Int) -> Unit
){
    val typography = MaterialTheme.typography
    var dropDownExpeded by remember {
        mutableStateOf(false)
    }
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
            IconButton(
                onClick = {
                    onClick(1)
                },
            ) {
                Icon(painter = painterResource(id = R.drawable.outline_volume_up_24), contentDescription ="speech" )
            }
            IconButton(
                onClick = {
                    onClick(2)
                },
            ) {
                Icon(imageVector = likeVectorIconState, contentDescription ="like" )
            }
            if (!userType){
                IconButton(
                    onClick = {
                        dropDownExpeded = true
                    },
                ) {
                    Icon(imageVector = Icons.Outlined.Edit, contentDescription ="like" )
                    DropdownMenu(
                        expanded = dropDownExpeded,
                        onDismissRequest = {
                            dropDownExpeded = false
                        }
                    ) {
                        DropdownMenuItem(
                            leadingIcon = {
                                Icon(imageVector = Icons.Outlined.Edit, contentDescription = "")
                            },
                            text = {
                                Text(text = "Edit")
                            },
                            onClick = {
                                dropDownExpeded = false
                                onClick(3)

                            }
                        )
                        DropdownMenuItem(
                            leadingIcon = {
                                Icon(imageVector = Icons.Outlined.Delete, contentDescription = "")
                            },
                            text = {
                                Text(text = "Delete")
                            },
                            onClick = {
                                dropDownExpeded = false
                                onClick(4)
                            }
                        )
                    }
                }
            }
        }
    }
}