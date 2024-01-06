package com.creamydark.avz.presentation.ui.customcomposables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.creamydark.avz.enozItools.YenaTools

@Composable
fun HomeUpdatesItem(
    modifier: Modifier = Modifier,
    title:String="",
    date:Long=0,
    postedBy:String="",
    postImg:Any? = null,
    dpUser:Any?=null
) {
    ElevatedCard(
        modifier = modifier
            .width(132.dp)
            .height(220.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xff323232),
        )
    ) {
        Box(
            modifier = modifier.fillMaxSize()
            /*.background(
                color = Color(0xff323232),
                shape = RoundedCornerShape(16.dp)
            )
            .clip(
                shape = RoundedCornerShape(16.dp)
            )*/
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth(),
            ) {
                AsyncImage(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .fillMaxWidth(),
                    model = postImg,
                    contentDescription = "",
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    color = Color.White,
                    text = title.ifBlank { "No Caption" },
                    fontSize = 12.sp,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 2
                )
                Text(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    text = YenaTools().timeAgo(date),
                    fontSize = 8.sp,
                    color = Color.White.copy(alpha = 0.7f),
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        8.dp
                    )
                    .align(Alignment.BottomCenter),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    modifier = Modifier
                        .clip(shape = CircleShape)
                        .size(16.dp)
                        .background(Color.Gray),
                    model = dpUser,
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = postedBy,
                    fontSize = 8.sp,
                    color = Color.White.copy(alpha = 0.7f),
                )
            }
        }

    }
}