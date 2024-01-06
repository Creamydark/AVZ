package com.creamydark.avz.presentation.ui.customcomposables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TitleWithSeeAllButtonComposable(
    modifier :Modifier = Modifier.fillMaxWidth(),
    title: String ="",
    actionText:String="",
    onActionClocked:()->Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall
        )
        TextButton(
            onClick = {
                onActionClocked()
            },
        ) {
            Text(text = actionText)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TitleWithSeeAllButtonComposablePrev() {
    TitleWithSeeAllButtonComposable(){

    }
}