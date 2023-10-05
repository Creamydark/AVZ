package com.creamydark.avz.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creamydark.avz.domain.model.ResponseWordScrollSnapShot
import com.creamydark.avz.domain.model.ScrollableItem
import com.creamydark.avz.data.repository.WordScrollRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WordScrollViewModel:ViewModel() {

    private val repo = WordScrollRepo()

    private val items = MutableStateFlow(ResponseWordScrollSnapShot(emptyList(),""))
    val itemList = items.asStateFlow()

    init {
        viewModelScope.launch {
            repo.getAllWords {
                items.update {
                    reponse ->
                    reponse.copy(items = it.items, errorMessage = "")
                }
            }
        }
    }

    fun getItems()=items



    fun addWords(
        item: ScrollableItem
    ){
        repo.addWords(item)
    }

}