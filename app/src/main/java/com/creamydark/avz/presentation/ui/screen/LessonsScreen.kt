package com.creamydark.avz.presentation.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.creamydark.avz.domain.model.SomeItem


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LessonsScreen(
){
    val itemss = arrayListOf<SomeItem>()

    val desccc = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book."

    SideEffect {
        for (a in 1..5){
            itemss.add(SomeItem(title = "Lesson $a", description = desccc))
        }
    }
    Scaffold(
        Modifier.fillMaxSize()
    ){ innerpadding ->
        LazyColumn(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues()
        ){
            items(itemss,key = {
                it.title
            }){item ->
                LessonListItem(title = item.title, desc = item.description )
                Spacer(modifier = Modifier.size(16.dp))
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