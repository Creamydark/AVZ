package com.creamydark.avz.presentation.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    context: Context
):ViewModel() {
    private val googleSignInAccount = GoogleSignIn.getLastSignedInAccount(context)

    private val displaname = MutableStateFlow(googleSignInAccount?.displayName)
    private val email = MutableStateFlow(googleSignInAccount?.email)
    private val profilePhotoUri = MutableStateFlow(googleSignInAccount?.photoUrl)



    val _displaname = displaname.asStateFlow()
    val _email = email.asStateFlow()
    val _profilePhotoUri = profilePhotoUri.asStateFlow()


}