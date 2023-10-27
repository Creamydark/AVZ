package com.creamydark.avz.presentation.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UploadWordsScreen(
    onUploadButton:(word:String,description:String,example:String)->Unit
){

    /*when(uploadResult){
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
    }*/



    var word by remember {
        mutableStateOf("")
    }
    var description by remember {
        mutableStateOf("")
    }
    var example by remember {
        mutableStateOf("")
    }

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
                word = it
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
            value = description,
            onValueChange = {
                description = it
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
               example = it
            },
            label = {
                Text(text = "Example")
            },
            maxLines = 6
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                onUploadButton(
                    word, description, example
                )
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SomeAlert(title:String,text:String,onDismiss:()->Unit) {
    AlertDialog(
        onDismissRequest = {
            onDismiss()
        },
        confirmButton = {
            TextButton(onClick = { onDismiss() }) {
                Text(text = "Okay")
            }
        },
        title = {
            Text(text = title, style = MaterialTheme.typography.titleSmall)
        },
        text = {
            Text(text = text, style = MaterialTheme.typography.bodySmall)
        },
    )
}