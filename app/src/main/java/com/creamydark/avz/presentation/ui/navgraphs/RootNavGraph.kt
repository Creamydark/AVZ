package com.creamydark.avz.presentation.ui.navgraphs

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.creamydark.avz.presentation.ui.screen.LoginScreen3
import com.creamydark.avz.presentation.ui.screen.SplashScreen
import com.creamydark.avz.presentation.ui.screen.UserTypeSelectionScreen
import com.creamydark.avz.presentation.viewmodels.RootNavGraphViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException


@Composable
fun SetUpNavGraph (navHostController: NavHostController, viewmodel: RootNavGraphViewModel){

    val context = LocalContext.current

    val signInLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            if (account != null) {
                viewmodel.signInWithGoogle(account)
            } else {
                // Handle null account
                Log.d("signInWithGoogle", ": Handle null account")
                Toast.makeText(context,"Null Google Account",Toast.LENGTH_SHORT).show()
            }
        } catch (e: ApiException) {
            Log.d("signInWithGoogle", "LoginScreen2 ${e.statusCode}")
            Toast.makeText(context,"Error Sign in : ${e.message}",Toast.LENGTH_SHORT).show()
        }
    }

    val isAuthentacated by viewmodel._isAuthenticated.collectAsState()
    val userData by viewmodel._userData.collectAsState()


    val errorSignIn by viewmodel._errorSignInWithCreds.collectAsState()


    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navHostController,
        startDestination = RootNavScreens.Splash.route
    ){
        composable(route = RootNavScreens.Splash.route){
           SplashScreen(){

           }
        }
        composable(route = "register_screen"){
           UserTypeSelectionScreen(
               onUploadCliced = {
//                   Log.d("SetUpNavGraph", "SetUpNavGraph: $it")
                   viewmodel.uploadDataToFirestore(it)
               }
           )
        }
        composable(route = RootNavScreens.UserLoginRootNavScreens.route){
            LoginScreen3{
                signInLauncher.launch(viewmodel.googleSignInAccount().signInIntent)
            }
        }
        composable(route = RootNavScreens.HomeGraph.route){
            HomeGraphv2()
        }
    }
    if (isAuthentacated){
        if (userData!=null){
            navHostController.navigate(route = RootNavScreens.HomeGraph.route){
                launchSingleTop = true
                popUpTo(route = RootNavScreens.UserLoginRootNavScreens.route){
                    inclusive = true
                }
            }
        }else{
            navHostController.navigate(route = "register_screen"){
                launchSingleTop = true
                popUpTo(route = RootNavScreens.UserLoginRootNavScreens.route){
                    inclusive = true
                }
            }
        }
    }else{
        navHostController.navigate(route = RootNavScreens.UserLoginRootNavScreens.route){
            launchSingleTop = true
            popUpTo(route = RootNavScreens.Splash.route){
                inclusive = true
            }
        }
    }
}
