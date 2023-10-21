package com.creamydark.avz.presentation.ui.navgraphs

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.creamydark.avz.presentation.ui.screen.LoginScreen3
import com.creamydark.avz.presentation.ui.screen.RegisterPart2Screen
import com.creamydark.avz.presentation.ui.screen.SplashScreen
import com.creamydark.avz.presentation.viewmodels.RegisterViewModel
import com.creamydark.avz.presentation.viewmodels.RootNavGraphViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import kotlinx.coroutines.delay
import javax.inject.Inject


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

    val googleAccountDataModel by viewmodel._googleAccountDataModel.collectAsState()

    val errorSignIn by viewmodel._errorSignInWithCreds.collectAsState()


    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navHostController,
        startDestination = Screen.Splash.route
    ){
        composable(route = Screen.Splash.route){
           SplashScreen(navHostController){
           }
        }
        composable(route = Screen.UserLoginScreen.route){
            LoginScreen3{
                signInLauncher.launch(viewmodel.googleSignInAccount().signInIntent)
            }
        }
        composable(route = Screen.HomeGraph.route){
            HomeGraphv2()
        }
        composable(route = Screen.UserRegisterScreen.route){
            val viewModel = hiltViewModel<RegisterViewModel>()
            RegisterPart2Screen(navHostController,viewModel)
        }
    }

    if (isAuthentacated) {
        // User is logged in, check registration status
//        val isRegistered = firestoreHelper.checkRegistrationStatus(user.uid)
        val isRegistered = viewmodel._userdataExistState.collectAsState().value
        if (isRegistered) {
            // User is registered, navigate to the home screen
//            HomeScreen()
            navHostController.navigate(route = Screen.HomeGraph.route){
                launchSingleTop = true
                popUpTo(route = Screen.Splash.route){
                    inclusive = true
                }
            }
        } else {
            // User is not registered, navigate to the registration screen
//            RegistrationScreen()
            navHostController.navigate(route = Screen.UserRegisterScreen.route){
                launchSingleTop = true
                popUpTo(route = Screen.Splash.route){
                    inclusive = true

                }
            }
        }
    } else {
        // User is not authenticated, show a login screen or other authentication options.
        navHostController.navigate(route = Screen.UserLoginScreen.route){
            launchSingleTop = true
            popUpTo(route = Screen.Splash.route){
                inclusive = true

            }
        }
    }
}
