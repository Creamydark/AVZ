package com.creamydark.avz.navgraphs.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.creamydark.avz.datamodels.SomeItem
import com.creamydark.avz.ui.theme.Chewy


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LessonsScreen(navHostController: NavHostController){
    val itemss = arrayListOf<SomeItem>()

    val desccc = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book."

    for (a in 1..5){
        itemss.add(SomeItem(title = "Lesson $a", description = desccc))
    }
    Scaffold(
        Modifier.fillMaxSize()
    ){ innerpadding ->
        Column(Modifier.fillMaxSize()) {
            LazyColumn(
                Modifier.fillMaxWidth().padding(horizontal = 22.dp), contentPadding = PaddingValues(bottom = innerpadding.calculateBottomPadding())){
                items(itemss,key = {
                    it.title
                }){item ->
                    LessonListItem(title = item.title, desc = item.description )
                }
            }
        }
    }
}




@Composable
fun ScrollItem(
    title:String,
    description:String,
    example:String,
    onClick:(Int) -> Unit
){
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Column() {
            Text(
                modifier = Modifier.fillMaxWidth(),
                fontFamily = Chewy,
                text = title,
                textAlign = TextAlign.Center,
                fontSize = 32.sp,
            )
            Spacer(modifier = Modifier.size(32.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                fontFamily = Chewy,
                text = description,
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
            )
            Spacer(modifier = Modifier.size(32.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                fontFamily = Chewy,
                text = example,
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
            )
        }
        Row(Modifier.fillMaxWidth().align(Alignment.BottomCenter),horizontalArrangement = Arrangement.Center) {
            IconButton(onClick = { onClick(0) }) {
                Icon(imageVector = Icons.Default.Share, contentDescription ="share" )
            }
            IconButton(onClick = {
                onClick(1)
            }) {
                Icon(imageVector = Icons.Default.Face, contentDescription ="speech" )
            }
            IconButton(onClick = { onClick(2) }) {
                Icon(imageVector = Icons.Default.Favorite, contentDescription ="like" )
            }
        }
    }
}

@Composable
fun LessonListItem(title:String,desc:String){
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 14.dp)) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = title,
            fontFamily = Chewy,
            fontSize = 18.sp
        )
        Text(
            modifier = Modifier.padding( start = 16.dp, end = 16.dp, bottom = 16.dp),
            text = "\u2022 $desc"
        )
    }
}