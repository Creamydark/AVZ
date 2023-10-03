package com.creamydark.avz.navgraphs.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.creamydark.avz.R
import com.creamydark.avz.navgraphs.Screen
import com.creamydark.avz.viewmodels.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen2(navHostController: NavHostController) {

    val viewModel = hiltViewModel<LoginViewModel>()

    val signInResult by viewModel._signInResult.collectAsState()

    val email by viewModel._email.collectAsStateWithLifecycle()

    val emailErrorState by viewModel._emailErrorState.collectAsStateWithLifecycle()

    val password by viewModel._password.collectAsStateWithLifecycle()

    val showPassword by viewModel._showPassword.collectAsStateWithLifecycle()

    val loginBtnState by viewModel._loginBtnState.collectAsStateWithLifecycle()

    val alertDialogState by viewModel._alertDialogState.collectAsStateWithLifecycle()

    val dialogMessage = if (signInResult.error.isNotEmpty()){
        signInResult.error
    }else if (signInResult.isSuccessful.isNotEmpty()){
        signInResult.isSuccessful
    }else{
        ""
    }

    if (dialogMessage.isNotEmpty()){
        viewModel.alertDialogState(true)
    }else{
        viewModel.alertDialogState(false)
    }

    when{
        alertDialogState ->{
            CustomAlertDialog(title = "Details", message = dialogMessage) {
                viewModel.alertDialogState(false)
            }
        }
    }

    Scaffold(Modifier.fillMaxSize()) { innerPadding->
        Box(modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(top = innerPadding.calculateTopPadding())
            .fillMaxSize()) {
            Column(Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.size(48.dp))
                IntroComponent(
                    title = "Welcome Back",
                    message = stringResource(id = R.string.login_below_title_text)
                )
                Spacer(modifier = Modifier.size(48.dp))
                EmailCustomTextField(
                    errorState = emailErrorState,
                    value = email,
                    onTextChanged = {
                        viewModel.editEmail(it)
                    }
                )
                Spacer(modifier = Modifier.size(16.dp))
                PasswordCustomTextField(
                    modifier = Modifier.fillMaxWidth(),
                    showPassword = showPassword,
                    value = password,
                    onTextChanged = {
                        viewModel.editPassword(it)
                    },
                    showBtn = {
                        viewModel.showBtnPassword(it)
                    }
                )
                Spacer(modifier = Modifier.size(16.dp))

                Row(modifier = Modifier.clickable {
                    navHostController.navigate(route = Screen.UserRegisterScreen.route)
                }) {
                    Text(
                        text = "New user? Let's ",
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                    Text(
                        text = "Sign Up",
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.primary.copy()
                    )
                }
            }
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        bottom = innerPadding
                            .calculateBottomPadding()
                            .plus(16.dp)
                    )
                    .align(Alignment.BottomCenter),
                onClick = {
                    viewModel.signIn()
                },
                enabled = loginBtnState
            ) {
                Text(text = "Login")
            }
        }
    }
}

@Composable
fun CustomAlertDialog(
    title:String,
    message:String,
    onDismiss:()->Unit
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Text(
                modifier = Modifier.clickable {
                    onDismiss()
                },
                text = "OKAY",
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Text(
                text = message,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        title = {
            Text(text = title)
        },
        icon = {
            Icon(imageVector = Icons.Default.Info, contentDescription = "")
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailCustomTextField(errorState:Boolean,value: String,onTextChanged: (String) -> Unit) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        leadingIcon = {
            Icon(imageVector = Icons.Default.Email, contentDescription = "")
        },
        label = {
            Text(text = "Email")
        },
        placeholder = {
            Text(text = "choiyena@gmail.com")
        },
        isError = errorState,
        value = value,
        onValueChange = {
            onTextChanged(it)
        }
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordCustomTextField(modifier: Modifier,showPassword:Boolean,value :String,onTextChanged:(String)->Unit,showBtn:(Boolean)->Unit) {
    OutlinedTextField(
        modifier = modifier,
        singleLine = true,
        leadingIcon = {
            Icon(imageVector = Icons.Default.Lock, contentDescription = "")
        },
        trailingIcon = {
            if (showPassword){
                IconButton(onClick = {
                    showBtn(false)
                }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.baseline_visibility_24),
                        contentDescription = "hide_password"
                    )
                }
            }else{
                IconButton(onClick = {
                    showBtn(true)
                }) {
                    Icon(
                        ImageVector.vectorResource(R.drawable.baseline_visibility_off_24),
                        contentDescription = "hide_password"
                    )
                }
            }
        },
        label = {
            Text(text = "Password")
        },
        placeholder = {
            //Text(text = "choiyena@gmail.com")
        },
        value = value,
        onValueChange = {
            onTextChanged(it)
        },
        visualTransformation =  if (showPassword) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
    )
}