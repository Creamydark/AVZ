package com.creamydark.avz.presentation.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.creamydark.avz.presentation.ui.customcomposables.InstagramLikePostLayout
import com.creamydark.avz.presentation.viewmodels.AnnouncementsViewModel

@Composable
fun AnnouncementsScreen(
    viewModel: AnnouncementsViewModel
) {
    val postList by viewModel.postList.collectAsStateWithLifecycle()
    LazyColumn(modifier = Modifier.fillMaxSize()){
        if (postList.isEmpty()){
            item {
                Text(text = "No updates available", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
            }
        }
        items(
            postList,
            key = {
                it.timestamp
            },
        ){
                item->
            InstagramLikePostLayout(
                username = item.displayName,
                caption = item.caption,
                timestamp = item.timestamp,
                likesCount = item.likesCount,
                model = item.imagePost,
                photoUrl = item.profilePhoto,
                commentsCount =item.commentsCount
            )
        }
    }
}