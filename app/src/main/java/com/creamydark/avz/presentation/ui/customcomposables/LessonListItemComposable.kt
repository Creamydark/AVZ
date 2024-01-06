package com.creamydark.avz.presentation.ui.customcomposables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.creamydark.avz.R
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LessonListItemComposable(
    title:String = "",
    description:String = "",
    onclicked:()->Unit
) {
    val bgiconColor by rememberSaveable {
        mutableStateOf(
            generateRandomNeutralColor().toArgb()
        )
    }
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        onClick = {
            onclicked()
        }
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                modifier = Modifier
                    .padding(8.dp)
                    .background(
                        color = Color(bgiconColor),
                        shape = CircleShape
                    )
                    .padding(8.dp),
                painter = painterResource(id = R.drawable.outline_bookmark_border_24),
                contentDescription = "",
                tint = Color.Black
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 3
                )
            }
        }
    }
}
fun generateRandomNeutralColor(): Color {
    val red = Random.nextInt(150, 256)
    val green = Random.nextInt(150, 256)
    val blue = Random.nextInt(150, 256)
    return Color(red, green, blue)
}
@Preview
@Composable
fun LessonListItemComposablePrev() {
    LessonListItemComposable(
        title = "awdwada",
        description = "wdwdwwawada"
    ){

    }
}