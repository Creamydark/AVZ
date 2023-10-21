package com.creamydark.avz.presentation.ui.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.creamydark.avz.domain.ResultType
import com.creamydark.avz.presentation.viewmodels.WordScrollViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UploadWordsScreen(navHostController: NavHostController,viewmodel : WordScrollViewModel){


    val uploadResult by viewmodel._uploadResult.collectAsState()

    val context = LocalContext.current

    when(uploadResult){
        is ResultType.Error -> {
            val message = (uploadResult as ResultType.Error).message
            Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
            Log.d("UploadWordsScreen", "Error:${message} ")
        }
        ResultType.Loading -> {

        }
        is ResultType.Success -> {
            val message = (uploadResult as ResultType.Success<String>).data
            Log.d("UploadWordsScreen", "Success:${message} ")
            Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
        }
    }

    val word by viewmodel._word_tf.collectAsState()
    val desc by viewmodel._desc_tf.collectAsState()
    val example by viewmodel._example_tf.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .weight(1.5f),
            value = word,
            onValueChange = {
                viewmodel.editWordTF(text = it)
            },
            label = {
                Text(text = "Word")
            },
            singleLine = true
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .weight(2f),
            value = desc,
            onValueChange = {
                viewmodel.editDescTF(text = it)
            },
            label = {
                Text(text = "Description")
            },
            maxLines = 3
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .weight(4f),
            value = example,
            onValueChange = {
                viewmodel.editExampleTF(text = it)
            },
            label = {
                Text(text = "Example")
            },
            maxLines = 6
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                viewmodel.uploadWordsToFirestore()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .weight(0.8f)
        ) {
            Text(text = "Upload")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UploadWordsScreenPrev(){
//    UploadWordsScreen()
}