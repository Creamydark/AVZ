package com.creamydark.avz.presentation.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutAppScreen(){
    Scaffold (modifier = Modifier.fillMaxSize()){ _ ->
        Column(Modifier.fillMaxSize()) {
            LazyColumn(modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 22.dp)){
                item {
                    LessonListItem(title = "Title Approval", desc = "Name : Joshua O. Aquino\nDanilo V. Villoso JR.\nMark C. Zoleta")
                    Spacer(modifier = Modifier.size(16.dp))
                    LessonListItem(title = "Description", desc = "AVZ : AN ENGLISH  VOCABOLARY  LEARNING ANDROID APPLICATION FOR GRADE 7 STUDENTS AT A. FERRER JR. EAST NATIONAL HIGHSCHOOL ")
                }
            }
        }
    }
}
