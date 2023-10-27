package com.creamydark.avz.presentation.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.creamydark.avz.presentation.viewmodels.WordScrollViewModel

@Composable
fun FavoriteScreen(favList:List<String>) {

    Scaffold(modifier = Modifier.fillMaxSize()) {
        inner->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.Top
        ){
            if (favList.isNotEmpty()){
                items(items = favList){
                        item->
                    FavItem(title = item)
                    Spacer(modifier = Modifier.size(8.dp))
                }
            }else{
                item {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = "Empty Favorites",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            /*items(
                contentType = {
                    sortedFavList[it]
                },
                count = sortedFavList.size,
                itemContent = {
                    pos->
                    FavItem(title = sortedFavList[pos])
                    Spacer(modifier = Modifier.size(8.dp))
                }
            )*/
        }
    }
}

@Composable
private fun FavItem(
    title:String
) {
    Box(modifier = Modifier.fillMaxWidth()){
        ElevatedCard(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                text = title
            )
        }
    }
}