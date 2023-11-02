package com.creamydark.avz.presentation.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.creamydark.avz.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen3 (loginClicked:()->Unit) {
    Scaffold(Modifier.fillMaxSize()) {inner->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(
                top = inner.calculateTopPadding(),
                bottom = inner
                    .calculateBottomPadding()
                    .plus(16.dp)
            )
            .padding(horizontal = 16.dp)
        ){
            Column(
                Modifier
                    .align(Alignment.TopCenter)) {
                Spacer(modifier = Modifier.size(40.dp))
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = "Let’s get started.",
                    fontSize = 32.sp,
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = stringResource(id = R.string.login_below_title_text),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
//            GoogleButton(
//                modifier = Modifier
//                    .align(Alignment.BottomCenter),
//                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
//            ){
//                loginClicked()
//            }
            OutlinedButton(
                modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth(),
                onClick = {
                          loginClicked()
                },
                border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_google),
                    contentDescription = null, // Set content description as needed
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Sign in using Google",
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(vertical = 16.dp),
//                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun prev() {
    LoginScreen3(){

    }
}