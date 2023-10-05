package com.creamydark.avz.presentation.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.creamydark.avz.domain.model.ScrollableItem
import com.creamydark.avz.presentation.viewmodels.WordScrollViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UploadWordsScreen(navHostController: NavHostController,viewmodel : WordScrollViewModel = viewModel()){
    val word = remember {
        mutableStateOf("")
    }
    val desr = remember {
        mutableStateOf("")
    }
    val example = remember {
        mutableStateOf("")
    }
    Scaffold(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 22.dp)) { innerPadding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(top = innerPadding.calculateTopPadding())){
            Column(modifier = Modifier.align(Alignment.TopCenter)) {
                Text(text = "Word")
                Spacer(modifier = Modifier.size(8.dp))
                OutlinedTextField(modifier = Modifier.fillMaxWidth(),
                    value = word.value,
                    onValueChange = {
                        word.value = it
                    }
                )
                Spacer(modifier = Modifier.size(16.dp))
                Text(text = "Description")
                Spacer(modifier = Modifier.size(8.dp))
                OutlinedTextField(modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.1f),
                    value = desr.value,
                    onValueChange = {
                        desr.value = it
                    }
                )
                Spacer(modifier = Modifier.size(16.dp))
                Text(text = "Example")
                Spacer(modifier = Modifier.size(8.dp))
                OutlinedTextField(modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.3f),
                    value = example.value,
                    onValueChange = {
                        example.value = it
                    }
                )
            }
            Button(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(
                        bottom = innerPadding
                            .calculateBottomPadding()
                            .plus(22.dp)
                    ),
                onClick = {
                    val item = ScrollableItem(
                        title = word.value,
                        description = desr.value,
                        example = example.value,
                        id = 0
                    )
                    viewmodel.addWords(
                        item = item
                    )
                },
            ) {
                Text(text = "Save")
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun UploadWordsScreenPrev(){
//    UploadWordsScreen()
}