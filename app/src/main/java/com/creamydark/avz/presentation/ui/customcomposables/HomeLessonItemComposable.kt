package com.creamydark.avz.presentation.ui.customcomposables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.creamydark.avz.enozItools.YenaTools

@Composable
fun HomeLessonItemComposable(
    title: String= "",
    date:Long=0
) {
    Column(modifier = Modifier
        .height(128.dp)
        .width(132.dp)

        .background(
            color = Color(0xff323232),
            shape = RoundedCornerShape(16.dp)
        )
        .padding(16.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(
            text = title,
            color = Color.White,
            style = MaterialTheme.typography.labelLarge
        )
        Text(
            color = Color.White.copy(alpha = 0.7f),
            text = YenaTools().convertMillisToDateTime(date),
            fontSize = 8.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeLessonItemComposablePrev() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        HomeLessonItemComposable()
    }
}