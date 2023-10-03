package com.creamydark.avz.navgraphs.screens

import  android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.creamydark.avz.R
import com.creamydark.avz.ui.theme.PoppinsBold
import com.creamydark.avz.ui.theme.PoppinsRegular
import com.creamydark.avz.viewmodels.RegisterViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserRegisterScreen2() {

    val viewmodel = hiltViewModel<RegisterViewModel>()

    val registerResult = viewmodel._registerResult.collectAsState()

    var showPassword by remember {
        mutableStateOf(false)
    }
    var name by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }
    var emailErrorState by remember {
        mutableStateOf(false)
    }

    var password by remember {
        mutableStateOf("")
    }

    var confirmpassword by remember {
        mutableStateOf("")
    }

    var studentbtnState by remember {
        mutableStateOf(false)
    }

    var teachertbtnState by remember {
        mutableStateOf(false)
    }
    var registerBtnState by remember {
        mutableStateOf(true)
    }


    val userType = if (studentbtnState){
        "Student"
    }else{
        "Teacher"
    }


    val snackbarHostState = remember { SnackbarHostState() }

    registerBtnState = !registerResult.value.isLoading

    if (registerResult.value.isSuccessful.isNotEmpty()){
        LaunchedEffect(snackbarHostState){
            snackbarHostState.showSnackbar(message = registerResult.value.isSuccessful, duration = SnackbarDuration.Long, withDismissAction = true)
        }
    }else if(registerResult.value.error.isNotEmpty()){
        LaunchedEffect(snackbarHostState){
            snackbarHostState.showSnackbar(message = registerResult.value.error, duration = SnackbarDuration.Long, withDismissAction = true)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        }
    ) {
            innerpadding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(top = innerpadding.calculateTopPadding())){
            Column(modifier = Modifier
                .fillMaxWidth()) {
                Spacer(modifier = Modifier.size(48.dp))
                IntroComponent(
                    title = "Create Account",
                    message = stringResource(id = R.string.register_below_title_text)
                )
                Spacer(modifier = Modifier.size(16.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Person, contentDescription = "")
                    },
                    label = {
                        Text(text = "Name")
                    },
                    placeholder = {
                        Text(text = "Choi Yena")
                    },
                    value = name,
                    onValueChange = {
                        name = it
                    }
                )

                Spacer(modifier = Modifier.size(16.dp))

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
                    isError = emailErrorState,
                    value = email,
                    onValueChange = {
                        email = it
                        emailErrorState = !checkEmail(it)
                    }
                )
                Spacer(modifier = Modifier.size(16.dp))

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Lock, contentDescription = "")
                    },
                    trailingIcon = {
                        if (showPassword){
                            IconButton(onClick = { showPassword = false }) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(R.drawable.baseline_visibility_24),
                                    contentDescription = "hide_password"
                                )
                            }
                        }else{
                            IconButton(onClick = { showPassword = true  }) {
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

                    },
                    value = password,
                    onValueChange = {
                        password = it
                    },
                    visualTransformation =  if (showPassword) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )
                Spacer(modifier = Modifier.size(16.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Check, contentDescription = "")
                    },
                    label = {
                        Text(text = "Confirm Password")
                    },
                    placeholder = {
                        //Text(text = "choiyena@gmail.com")
                    },
                    value = confirmpassword,
                    onValueChange = {
                        confirmpassword = it
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )
                Spacer(modifier = Modifier.size(16.dp))

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    SomeOptionsComponent(
                        modifier = Modifier.weight(1f),
                        text = "Student",
                        state = studentbtnState
                    ) {
                        teachertbtnState = false
                        studentbtnState = true
                    }
                    SomeOptionsComponent(
                        modifier = Modifier.weight(1f),
                        text = "Teacher",
                        state = teachertbtnState
                    ) {
                        studentbtnState = false
                        teachertbtnState = true
                    }
                }
            }
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(
                        bottom = innerpadding
                            .calculateBottomPadding()
                            .plus(16.dp)
                    ),
                enabled = registerBtnState,
                onClick = {
                    viewmodel.signUp(
                        email = email,
                        password = password,
                        name = name,
                        userType = userType
                    )
                }
            ) {
                Text(text = "Register")
            }
        }
    }
}


fun checkEmail(email:String):Boolean=!TextUtils.isEmpty(email)&&Patterns.EMAIL_ADDRESS.matcher(email).matches()

@Composable
private fun SomeOptionsComponent(modifier: Modifier,text:String,state:Boolean=false,onclick:(Boolean)->Unit) {
    val textColor = if (state){
        MaterialTheme.colorScheme.primary
    }else{
        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
    }
    val cardBgColor = if (state){
        MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
    }else{
        Color.Transparent
    }
    Card(
        modifier = modifier.clickable {
            onclick(state)
        },
        colors = CardDefaults.cardColors(containerColor = cardBgColor),
        border = BorderStroke(width = 1.dp, color = textColor)
    ) {
        Row(
            modifier = Modifier.padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = state,
                onClick = {
                    onclick(state)
                }
            )
            Text(
                text = text,
                fontWeight = FontWeight.Medium,
                color = textColor
            )
        }
    }
}

@Composable
fun IntroComponent(title:String,message:String) {
    Text(
        text = title,
        fontWeight = FontWeight.Bold,
        fontSize = 25.sp,
        color = MaterialTheme.colorScheme.primary,
        fontFamily = PoppinsBold
    )
    Text(
        text = message,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        lineHeight = 20.sp,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f ),
        fontFamily = PoppinsRegular
    )
}

@Preview(showBackground = true)
@Composable
fun some() {
    MaterialTheme{
        UserRegisterScreen2()
    }
}