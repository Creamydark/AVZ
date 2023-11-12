package com.creamydark.avz.presentation.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.creamydark.avz.R
import com.creamydark.avz.domain.model.WordsDataModel
import com.creamydark.avz.presentation.ui.customcomposables.SearchResultItemComposable

@Composable
fun WordsSearchScreen(
    searchResultList:List<WordsDataModel> = emptyList(),
    searchText:String,
    onSearchText:(text:String)->Unit,
    voiceBtn:()->Unit
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)) {
//        Text(text = "This is Search Screen")
        OutlinedTextField(
            modifier= Modifier.fillMaxWidth(),
            label = {
                Text(text = "Search")
            },
            placeholder = {
                Text(text = "Kotlin")
            },
            trailingIcon = {
                IconButton(onClick = {
                    voiceBtn()
                }) {
                    Icon(painter = painterResource(id = R.drawable.outline_keyboard_voice_24), contentDescription = "")
                }
            },
            singleLine = true,
            value = searchText,
            onValueChange = {
                onSearchText(it)
            }
        )
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 16.dp)
        ){
            items(
                items = searchResultList,
                key = {
                    it.title
                },
                itemContent = {
                    item ->
                    //Text(modifier = Modifier.fillMaxWidth().padding(16.dp), textAlign = TextAlign.Center, text = item.title)
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()

                    ) {
                        SearchResultItemComposable(
                            modifier = Modifier
                                .clickable {

                                }
                                .padding(16.dp)
                                .fillMaxWidth(),
                            title = item.title,
                            description = truncateAndAddDots(input = item.description, maxLength = 48)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }
            )
        }
    }
}

fun truncateAndAddDots(input: String, maxLength: Int): String {
    return if (input.length <= maxLength) {
        input
    } else {
        input.substring(0, maxLength - 3) + "..."
    }
}