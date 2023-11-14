package com.creamydark.avz.presentation.ui.navgraphs

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.creamydark.avz.enozienum.UserAuthenticationState
import com.creamydark.avz.presentation.ui.screen.LoginScreen3
import com.creamydark.avz.presentation.ui.screen.SplashScreen
import com.creamydark.avz.presentation.ui.screen.UserTypeSelectionScreen
import com.creamydark.avz.presentation.viewmodels.RootNavGraphViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import kotlin.math.min


@Composable
fun SetUpNavGraph (modifier: Modifier = Modifier,navHostController: NavHostController, viewmodel: RootNavGraphViewModel){

    val context = LocalContext.current

    val signInLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            if (account != null) {
                viewmodel.signInWithGoogle(account)
            } else {
                // Handle null account
                Toast.makeText(context,"Null Google Account",Toast.LENGTH_SHORT).show()
            }
        } catch (e: ApiException) {
            Toast.makeText(context,"Error Sign in : ${e.message}",Toast.LENGTH_SHORT).show()
        }
    }

    val authState by viewmodel.getAuthState().collectAsStateWithLifecycle()

    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = RootNavScreens.Splash.route,
    ){
        composable(route = RootNavScreens.Splash.route){
           SplashScreen{
               viewmodel.initialize()
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
    val currentDestination = navHostController.currentDestination?.route?:""
    when(authState){
        UserAuthenticationState.Authenticated -> {
            navHostController.navigate(route = RootNavScreens.HomeGraph.route){
                launchSingleTop = true
                popUpTo(route = currentDestination){
                    inclusive = true
                }
            }
        }
        UserAuthenticationState.Unauthenticated -> {
            navHostController.navigate(route = RootNavScreens.UserLoginRootNavScreens.route){
                launchSingleTop = true
                popUpTo(route = currentDestination){
                    inclusive = true
                }
            }
        }
        UserAuthenticationState.Loading -> {

        }
        UserAuthenticationState.Error -> {

        }
        UserAuthenticationState.OnRegisterState -> {
            navHostController.navigate(route = "register_screen"){
                launchSingleTop = true
                popUpTo(route = currentDestination){
                    inclusive = true
                }
            }
        }
    }
}
