package com.creamydark.avz.presentation.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun UserTypeSelectionScreen(onUploadCliced: (Boolean) -> Unit) {

    var selectedUserType by remember { mutableStateOf<UserType?>(null) }
    val type = selectedUserType != UserType.TEACHER
    Scaffold(modifier = Modifier.fillMaxSize()) {
        inner->
        Column(
            modifier = Modifier
                .fillMaxSize().statusBarsPadding().navigationBarsPadding().padding(horizontal = 16.dp)
        ) {
            Text("Select your user type")

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        selectedUserType = UserType.STUDENT
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedUserType == UserType.STUDENT) MaterialTheme.colorScheme.primary else Color.Gray
                    )
                ) {
                    Text("Student")
                }

                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    onClick = {
                        selectedUserType = UserType.TEACHER
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedUserType == UserType.TEACHER) MaterialTheme.colorScheme.primary else Color.Gray
                    )
                ) {
                    Text("Teacher")
                }
            }

            // Add Upload button at the bottom
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                    onUploadCliced(type)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Upload")
            }
        }
    }
}

private enum class UserType {
    STUDENT,
    TEACHER
}