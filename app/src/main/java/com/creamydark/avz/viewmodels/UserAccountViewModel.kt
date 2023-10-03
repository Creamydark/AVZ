package com.creamydark.avz.viewmodels

import androidx.lifecycle.ViewModel
import com.creamydark.avz.repository.UserFirebaseAccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class UserAccountViewModel @Inject constructor(private val repo:UserFirebaseAccountRepository):ViewModel() {




    private fun dialogChangedState(dialogstate:Boolean,dialog:String,message:String){

    }

}