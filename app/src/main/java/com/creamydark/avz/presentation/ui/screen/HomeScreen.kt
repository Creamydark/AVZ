package com.creamydark.avz.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.creamydark.avz.R
import com.creamydark.avz.domain.model.LessonData
import com.creamydark.avz.domain.model.UpdatesPostData
import com.creamydark.avz.domain.model.SomeItem
import com.creamydark.avz.domain.model.WordsDataModel
import com.creamydark.avz.presentation.ui.customcomposables.BigBannerWithAutoScrollLayoutComposable
import com.creamydark.avz.presentation.ui.customcomposables.HomeLessonItemComposable
import com.creamydark.avz.presentation.ui.customcomposables.HomeUpdatesItem
import com.creamydark.avz.presentation.ui.customcomposables.TitleWithSeeAllButtonComposable
import java.util.Date

@Composable
fun HomeScreen(
    wordsList: List<WordsDataModel> = emptyList(),
    updatesList: List<UpdatesPostData> = emptyList(),
    lessonsList: List<LessonData> = emptyList(),
    navHostController: NavHostController,
    lessonOnClicked:(data:LessonData)->Unit
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        item {
            BigBannerWithAutoScrollLayoutComposable(
                list = wordsList,
                onBannerClicked = {
                    navHostController.navigate("vocabulary_screen")
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            TitleWithSeeAllButtonComposable(
                modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
                title = "Updates",
                actionText = "See ALL"
            ) {
                navHostController.navigate("updates_screen") {
                    // Pop up to the start destination of the graph to
                    // avoid building up a large stack of destinations
                    // on the back stack as users select items
                    popUpTo(navHostController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    // Avoid multiple copies of the same destination when
                    // reselecting the same item
                    launchSingleTop = true
                    // Restore state when reselecting a previously selected item
                    restoreState = true
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            if (updatesList.isEmpty()){
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    text = "Empty updates",
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center
                )
            }
            LazyRow(
                contentPadding = PaddingValues(start = 16.dp)
            ){
                items(
                    items = updatesList.reversed(),
                    key = {
                        it.emailUploader+it.timestamp
                    },
                    itemContent = {
                        HomeUpdatesItem(
                            title = it.caption,
                            date = it.timestamp?.time?:0,
                            dpUser = it.profilePhoto,
                            postedBy = it.displayName,
                            postImg = it.imagePostLink
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                    }
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
        item {
            TitleWithSeeAllButtonComposable(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                title = "Lessons",
                actionText = "See ALL"
            ) {
                navHostController.navigate("lessons_screen") {
                    // Pop up to the start destination of the graph to
                    // avoid building up a large stack of destinations
                    // on the back stack as users select items
                    popUpTo(navHostController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    // Avoid multiple copies of the same destination when
                    // reselecting the same item
                    launchSingleTop = true
                    // Restore state when reselecting a previously selected item
                    restoreState = true
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            LazyRow(
                contentPadding = PaddingValues(start = 16.dp)
            ){
                items(
                    items = lessonsList.sortedBy {
                        it.timestamp
                    }.reversed(),
                    key = {
                          it.title+it
                    },
                    itemContent = {
                        HomeLessonItemComposable(
                            title = it.title,
                            date = it.timestamp?: Date(),
                            modifier = Modifier.clickable {
                                lessonOnClicked(it)
                            }
                        )
                        Spacer(modifier = Modifier.width(8.dp))

                    }
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
/*
        item {
            TitleWithSeeAllButtonComposable(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                title = "About",
                actionText = "See ALL"
            ) {
                navHostController.navigate("about_screen") {
                    launchSingleTop = true
                }
            }
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
*/
        /*item {
            LessonListItem(
                modifier = Modifier.padding(horizontal = 16.dp),
                title = "Powered by Jetpack Compose",
                desc = stringResource(id = R.string.powered_by_jetpack_compose)
            )
        }*/
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