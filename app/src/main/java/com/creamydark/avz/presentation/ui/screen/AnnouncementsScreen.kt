package com.creamydark.avz.presentation.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.creamydark.avz.domain.model.ResultType
import com.creamydark.avz.domain.model.UpdatesPostData
import com.creamydark.avz.presentation.ui.customcomposables.InstagramLikePostLayout
import com.creamydark.avz.presentation.viewmodels.AnnouncementsViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnnouncementsScreen(
    viewModel: AnnouncementsViewModel
) {
    val postList by viewModel.postList.collectAsStateWithLifecycle()
    val userData by viewModel.userData.collectAsStateWithLifecycle()
    var showBottomSheet by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    var selectedPostData by remember {
        mutableStateOf(UpdatesPostData())
    }
    val editUpdateResult by viewModel.editPostResult.collectAsStateWithLifecycle(initialValue = ResultType.loading())

    when(editUpdateResult){
        is ResultType.Error -> {

        }
        ResultType.Loading -> {

        }
        is ResultType.Success -> {


        }
    }

    LazyColumn(modifier = Modifier.fillMaxSize()){
        if (postList.isEmpty()){
            item {
                Text(text = "No updates available", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
            }
        }
        items(
            postList.reversed(),
            key = {
                it.timestamp?.time?:0
            },
        ){
                item->
            InstagramLikePostLayout(
                username = item.displayName,
                caption = item.caption,
                timestamp = item.timestamp?.time?:0,
                model = item.imagePostLink,
                photoUrl = item.profilePhoto,
                userType = userData?.student?:true
            ){
                    pos->
                when(pos){
                    0->{
                        selectedPostData = item
                        showBottomSheet = true
//                        Log.d("AnnouncementsScreen", "showBottomSheet: $showBottomSheet")
                    }
                    1->{
                        viewModel.deletePost(
                            item
                        )
                    }
                }
            }
        }
    }
    if (showBottomSheet){
        ModalBottomSheet(
            modifier = Modifier.imePadding(),
            sheetState = sheetState,
            onDismissRequest = {
                showBottomSheet = false
            }
        ) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)) {
                var caption by remember {
                    mutableStateOf(selectedPostData.caption)
                }
                Text(text = "Captions", style = MaterialTheme.typography.labelLarge)
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = caption,
                    onValueChange = {
                        caption = it
                    },
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        viewModel.editPost(selectedPostData.copy(caption = caption))
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            showBottomSheet = false
                        }
                    },
                ) {
                    Text(text = "Done")

                }
            }
        }
    }
}