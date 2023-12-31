package com.creamydark.avz.presentation.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.creamydark.avz.domain.model.WordsDataModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UploadWordsScreen(
    data: WordsDataModel = WordsDataModel(),
    onUploadButton:(data:WordsDataModel)->Unit
){
    var word by remember {
        mutableStateOf(data.title)
    }
    var description by remember {
        mutableStateOf(data.description)
    }
    var selectedWordType by remember {
        mutableStateOf(data.example)
    }
    val a = LocalSoftwareKeyboardController.current
    val wordTypes = listOf("Noun", "Pronoun", "Verb", "Adjective", "Adverb", "Preposition", "Conjunction", "Interjection")

    var expanded by remember { mutableStateOf(false) }
//    var selectedWordType by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .wrapContentSize(align = Alignment.TopCenter)
            .padding(16.dp)
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = word,
            onValueChange = {
                word = it
            },
            label = {
                Text(text = "Word")
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    // Dismiss the keyboard on Done button click
//                    LocalSoftwareKeyboardController.current?.hide()
                    a?.hide()
                }
            ),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = description,
            onValueChange = {
                description = it
            },
            label = {
                Text(text = "Description")
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    // Dismiss the keyboard on Done button click
//                    LocalSoftwareKeyboardController.current?.hide()
                    a?.hide()
                }
            ),
            maxLines = 4
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            modifier = Modifier
                .clickable { expanded = true }
                .fillMaxWidth(),
            value = selectedWordType,
            onValueChange = {
                selectedWordType = it
            },
            readOnly = true,
            placeholder = {
                Text(text = "Noun")
            },
            label = {
                Text(text = "Parts of speech")
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    // Dismiss the keyboard on Done button click
//                    LocalSoftwareKeyboardController.current?.hide()
                    a?.hide()
                }
            ),
            maxLines = 6,
            trailingIcon = {
                IconButton(onClick = { expanded = true }) {
                    Icon(imageVector = Icons.Outlined.ArrowDropDown, contentDescription = "")
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.width(150.dp),
                ) {
                    wordTypes.forEach { wordType ->
                        DropdownMenuItem(
                            onClick = {
                                expanded = false
                                selectedWordType = wordType
                            },
                            text = {
                                Text(text = wordType)
                            },
                        )
                    }
                }
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                onUploadButton(
                    data.copy(
                        title = word,
                        description = description,
                        example = selectedWordType
                    )
                )
            },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(text = "Done")
        }
    }
}