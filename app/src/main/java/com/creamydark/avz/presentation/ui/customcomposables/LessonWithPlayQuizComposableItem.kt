package com.creamydark.avz.presentation.ui.customcomposables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.creamydark.avz.enozItools.YenaTools
import java.util.Date

@Composable
fun LessonWithPlayQuizComposableItem(
    countPos:Int = 0,
    date: Date = Date(),
    subtitle:String = "",
    title:String = "",
    shortDescription:String = "",
    playQuizBtn:()->Unit,
    viewBtn:()->Unit
) {
    Card {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(55.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.Gray.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text = "$countPos",
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(Modifier.width(8.dp))
                Column(
                    verticalArrangement = Arrangement.Center
                ){
                    Text(
                        text = YenaTools().simpleDateFormatter(date.time),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        modifier = Modifier
                            .clip(RoundedCornerShape(100))
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.3f))
                            .padding(horizontal = 16.dp, vertical = 4.dp),
                        text = subtitle,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = title, style = MaterialTheme.typography.titleLarge, maxLines = 2)
            Spacer(modifier = Modifier.height( 4.dp))
            Text(text = shortDescription, style = MaterialTheme.typography.bodySmall, maxLines = 5)
            Row(
                horizontalArrangement = Arrangement.Start
            ) {
                TextButton(
                    onClick = {
                        playQuizBtn()
                    },
                ) {
                    Text(
                        text = "Play Quiz",
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
//                Spacer(modifier = Modifier.width(16.dp))
                TextButton(onClick = {
                    viewBtn()
                }
                ) {
                    Text(
                        text = "View this Lesson",
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LessonWithPlayQuizComposableItemPrev() {
    LessonWithPlayQuizComposableItem(viewBtn = {}, playQuizBtn = {})
}
