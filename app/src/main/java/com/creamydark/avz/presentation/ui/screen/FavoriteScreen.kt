package com.creamydark.avz.presentation.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.creamydark.avz.domain.model.WordsDataModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(favList:List<String>,wordList: List<WordsDataModel>,editMode:Boolean = false,onDelete:(toDelete:String)->Unit) {
    Scaffold(modifier = Modifier.fillMaxSize()) {
        inner->
        val sheetState = rememberModalBottomSheetState()
        val scope = rememberCoroutineScope()
        var selectedWord by remember {
            mutableStateOf("")
        }
        var selectedWordData by remember {
            mutableStateOf(
                WordsDataModel()
            )
        }
        var showBottomSheet by remember { mutableStateOf(false) }
        if (showBottomSheet) {
            ModalBottomSheet(
                modifier = Modifier.fillMaxHeight(0.5f),
                scrimColor = Color.Transparent,
                onDismissRequest = {
                    showBottomSheet = false
                },
                dragHandle = {
                    BottomSheetDefaults.DragHandle()
                },
                sheetState = sheetState
            ) {
                // Sheet content
//                Text(text = "This is Bottom Sheet ", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = selectedWordData.title,
                        style = MaterialTheme.typography.headlineLarge,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Description",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                    Text(text = selectedWordData.description, textAlign = TextAlign.Center)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Example",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                    Text(text = selectedWordData.example, textAlign = TextAlign.Center)
                }
            }
        }
        LaunchedEffect(key1 = selectedWord){
            wordList.forEach {
                    item->
                if (selectedWord == item.title){
                    selectedWordData = item
                }
            }
        }
        LaunchedEffect(key1 = sheetState){
            scope.launch {
                if (!sheetState.isVisible) {
                    showBottomSheet = false
                }
            }
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.Top
        ){
            if (favList.isNotEmpty()){
                items(items = favList){
                        item->
                    FavItem(title = item, modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            scope.launch {
                                selectedWord = item
                                showBottomSheet = true
                                sheetState.show()
                            }
                        },
                        onEditMode = editMode){
                        onDelete(item)
                    }
                    Spacer(modifier = Modifier.size(8.dp))
                }
            }else{
                item {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            ,
                        text = "Empty Favorites",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }

    }
}

@Composable
private fun FavItem(
    title:String,
    modifier: Modifier,
    onEditMode:Boolean=false,
    deleteBtn:()->Unit
) {
    Card(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
//                    .fillMaxWidth()
                    .padding(16.dp),
                text = title
            )
            if (onEditMode){
                IconButton(
                    modifier=Modifier,
                    onClick = {
                        deleteBtn()
                    },
                ) {
                    Icon(imageVector = Icons.Outlined.Delete, contentDescription = "")
                }
            }
        }
    }
}