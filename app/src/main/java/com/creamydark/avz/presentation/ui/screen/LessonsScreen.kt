package com.creamydark.avz.presentation.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.creamydark.avz.domain.model.LessonData
import com.creamydark.avz.enozItools.YenaTools
import com.creamydark.avz.presentation.ui.customcomposables.LessonListItemComposable
import com.creamydark.avz.presentation.viewmodels.LessonsViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LessonsScreen(
    navHostController: NavHostController,
    viewModel: LessonsViewModel
){
    val lessonList by viewModel.lessonList.collectAsStateWithLifecycle()
    LazyColumn(
        contentPadding = PaddingValues(16.dp),

    ){
        item {
            if (lessonList.isEmpty()){
                Text(modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center, text = "Empty Lessons")
            }
        }
        items(
            items = lessonList.sortedBy { it.timestamp }.reversed(),
            key = {
                item: LessonData ->
                item.id
            }
        ){
            item ->
            Spacer(modifier = Modifier.height(16.dp))
            LessonListItemComposable(
                title = item.title,
                description = YenaTools().simpleDateFormatter(item.timestamp?.time?:0)
            ) {
                viewModel.selectLesson(
                    item
                ).also {
                    navHostController.navigate("lessons_detail_screen"){
                        launchSingleTop = true
                    }
                }
            }
        }
    }
}

@Composable
fun LessonListItem(title:String,desc:String,modifier: Modifier = Modifier){
    Card(modifier = modifier
        .fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = desc,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}