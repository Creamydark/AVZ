package com.creamydark.avz.navgraphs.screens

import android.widget.DatePicker
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.creamydark.avz.navgraphs.Screen
import com.creamydark.avz.ui.theme.Chewy

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutAppScreen(navHostController: NavHostController){
    Scaffold (modifier = Modifier.fillMaxSize()){ innerPadding ->
        Column(Modifier.fillMaxSize()) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(top = innerPadding.calculateTopPadding())){
                Text(
                    modifier = Modifier
                        .align(alignment = Alignment.CenterStart)
                        .padding(22.dp),
                    text = "About",
                    fontFamily = Chewy,
                    fontSize = 22.sp
                )
            }
            LazyColumn(modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 22.dp), contentPadding = PaddingValues(top = innerPadding.calculateTopPadding())){
                item {
                    LessonListItem(title = "Title Approval", desc = "Name : Joshua O. Aquino\nDanilo V. Villoso JR.\nMark C. Zoleta")
                }
                item {
                    LessonListItem(title = "Title", desc = "AVZ : AN ENGLISH  VOCABOLARY  LEARNING ANDROID APPLICATION FOR GRADE 7 STUDENTS AT A. FERRER JR. EAST NATIONAL HIGHSCHOOL ")
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun someprev(){
}