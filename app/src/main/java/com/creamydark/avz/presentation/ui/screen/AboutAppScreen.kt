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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.creamydark.avz.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutAppScreen(){
    Scaffold (modifier = Modifier.fillMaxSize()){ _ ->
        Column(Modifier.fillMaxSize()) {
            LazyColumn(modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)){
                item {
                    LessonListItem(title = "Title Approval", desc = "Joshua O. Aquino\nDanilo V. Villoso JR.\nMark C. Zoleta")
                    Spacer(modifier = Modifier.size(16.dp))
                    LessonListItem(
                        title = "Description",
                        desc = stringResource(id = R.string.app_capstone_title)
                    )
                    /*Spacer(modifier = Modifier.size(16.dp))

                    LessonListItem(
                        title = "Exclusive to Android",
                        desc = stringResource(id = R.string.exclusive_to_android)
                    )
                    Spacer(modifier = Modifier.size(16.dp))

                    LessonListItem(
                        title = "Powered by Jetpack Compose",
                        desc = stringResource(id = R.string.powered_by_jetpack_compose)
                    )*/
                }
            }
        }
    }
}
