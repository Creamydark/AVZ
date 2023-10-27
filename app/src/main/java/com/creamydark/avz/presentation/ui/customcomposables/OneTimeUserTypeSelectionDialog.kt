package com.creamydark.avz.presentation.ui.customcomposables

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OneTimeUserTypeSelectionDialog(
    onclicked:(Int)-> Unit
){
    AlertDialog(
        onDismissRequest = {
            onclicked(0)
        },
        title = {
            Text(text = "User Type")
        },
        text = {
            Text(text = "Are you a student or a teacher?")
        },
        confirmButton = {
            TextButton(onClick = {
                onclicked(1)
            }) {
                Text(text = "Student")
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onclicked(2)
            }) {
                Text(text = "Teacher")
            }
        }
    )
}
