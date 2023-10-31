package com.creamydark.avz.presentation.ui.screen

import android.net.Uri
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.creamydark.avz.domain.ResultType
import com.creamydark.avz.domain.model.AnnouncementPostData
import com.creamydark.avz.presentation.ui.customcomposables.InstagramLikePostLayout
import com.creamydark.avz.presentation.viewmodels.AnnouncementsViewModel

@Composable
fun AnnouncementsScreen(
    viewModel: AnnouncementsViewModel
) {
    val postList by viewModel.postList.collectAsStateWithLifecycle()
    LazyColumn(modifier = Modifier.fillMaxSize()){
        items(
            postList,
            key = {
                it.timestamp
            },
        ){
                item->
            var image by remember {
                mutableStateOf<Uri?>(null)
            }
            val ref = "POSTS-ANNOUNCEMENTS/${item.emailUploader}/${item.timestamp}/image-post.jpg"
            LaunchedEffect(key1 = item.timestamp){
                viewModel.getPostImageUseCase().invoke(ref).apply {
                    when(this){
                        is ResultType.Error -> {

                        }
                        ResultType.Loading -> {

                        }
                        is ResultType.Success -> {
                            image = this.data
                        }
                    }
                }
            }
            InstagramLikePostLayout(
                username = item.displayName,
                caption = item.caption,
                timestamp = item.timestamp,
                likesCount = item.likesCount,
                model = image,
                photoUrl = item.profilePhoto,
                commentsCount =item.commentsCount
            )
        }
    }
}