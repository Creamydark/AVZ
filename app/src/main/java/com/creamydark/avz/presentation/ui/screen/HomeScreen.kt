package com.creamydark.avz.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.creamydark.avz.R
import com.creamydark.avz.domain.model.AnnouncementPostData
import com.creamydark.avz.domain.model.SomeItem
import com.creamydark.avz.domain.model.WordsDataModel
import com.creamydark.avz.presentation.ui.customcomposables.BigBannerWithAutoScrollLayoutComposable
import com.creamydark.avz.presentation.ui.customcomposables.HomeLessonItemComposable
import com.creamydark.avz.presentation.ui.customcomposables.HomeUpdatesItem

@Composable
fun HomeScreen(
    wordsList: List<WordsDataModel> = emptyList(),
    updatesList: List<AnnouncementPostData> = emptyList(),
    navHostController: NavHostController
) {
    var itemss by remember {
        mutableStateOf<List<SomeItem>>(emptyList())
    }
    val desccc = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book."
    LaunchedEffect(key1 = true) {
        val li = ArrayList<SomeItem>()
        for (a in 1..5){
            li.add(SomeItem(title = "Lesson $a", description = desccc))

        }
        itemss = li
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        item {
            BigBannerWithAutoScrollLayoutComposable(
                modifier = Modifier.padding(horizontal = 16.dp),
                list = wordsList.reversed(),
                onBannerClicked = {
                    navHostController.navigate("vocabulary_screen")
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            Text(

                modifier = Modifier.padding(horizontal = 16.dp),
                text = "Updates",
                style = MaterialTheme.typography.titleSmall
            )
            Spacer(modifier = Modifier.height(8.dp))
            LazyRow(
                contentPadding = PaddingValues(start = 16.dp)
            ){
                items(
                    items = updatesList.reversed(),
                    key = {
                        it.timestamp.toString() + it.emailUploader
                    },
                    itemContent = {
                        HomeUpdatesItem(
                            title = it.caption,
                            date = it.timestamp,
                            dpUser = it.profilePhoto,
                            postedBy = it.displayName,
                            postImg = it.imagePost
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                    }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = "Lessons",
                style = MaterialTheme.typography.titleSmall
            )
            Spacer(modifier = Modifier.height(8.dp))
            LazyRow(
                contentPadding = PaddingValues(start = 16.dp)
            ){
                items(
                    items = itemss.reversed(),
                    key = {
                          it.title+it
                    },
                    itemContent = {
                        HomeLessonItemComposable(
                            title = it.title
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = "About",
                style = MaterialTheme.typography.titleSmall
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(16.dp),
                style = MaterialTheme.typography.bodyMedium,
                text = stringResource(id = R.string.app_capstone_title)
            )
            Spacer(modifier = Modifier.height(16.dp))

        }
        item {
            LessonListItem(
                modifier = Modifier.padding(horizontal = 16.dp),
                title = "Powered by Jetpack Compose",
                desc = stringResource(id = R.string.powered_by_jetpack_compose)
            )
        }
        /*item {
            Text(
                modifier = Modifier.fillMaxWidth()
                    .padding(16.dp),
                textAlign = TextAlign.Center,
                text = "DevelopBy : CreamyDark",
                style = MaterialTheme.typography.titleSmall
            )
        }*/
    }
}