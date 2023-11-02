package com.creamydark.avz.presentation.ui.customcomposables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun InstagramLikePostLayout(
    username: String,
    caption: String,
    timestamp: Long,
    likesCount: Int,
    model: Any?,
    photoUrl: String,
    commentsCount: Int
) {
//    val timestampInMilliseconds = System.currentTimeMillis() // Replace with your timestamp
    val pattern = "yyyy-MM-dd HH:mm a" // Customize the format as needed
    val timestampF = convertMillisToDateTime(timestamp,pattern)
    Column(
        modifier = Modifier
    ) {
        // User information
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar
            AsyncImage(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.Gray),
                model = photoUrl,
                contentDescription = "",
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(8.dp))

            // Username and timestamp
            Column {
                Text(
                    text = username,
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    text = timestampF,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
        }

        // Post image
        /*AsyncImage(
            model = model,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            contentDescription = "",
            placeholder = painterResource(id = R.drawable.app_logo_hd_png),
            error = painterResource(id = R.drawable.app_logo_hd_png),
            contentScale = ContentScale.Crop
        )*/
        SubcomposeAsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            model = model,
            contentDescription = "",
            contentScale = ContentScale.Crop
        ) {
            val state = painter.state
            when(state){
                AsyncImagePainter.State.Empty -> {
                    Text(text = "Empty Image", textAlign = TextAlign.Center)
                }
                is AsyncImagePainter.State.Loading -> {
                    Box(modifier = Modifier){
                        /*CircularProgressIndicator(
                            modifier = Modifier
                                .size(32.dp)
                                .align(Alignment.Center)
                        )*/
                        Text(modifier = Modifier.align(Alignment.Center), text = "Loading")
                    }
                }
                is AsyncImagePainter.State.Success -> {
                    SubcomposeAsyncImageContent()
                }
                is AsyncImagePainter.State.Error -> {
                    
                }
            }
        }
        Text(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp), text = caption)

        // Like and comment buttons
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp),
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            Row(
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.spacedBy(4.dp)
//            ) {
//                Icon(
//                    imageVector = Icons.Outlined.FavoriteBorder,
//                    contentDescription = null,
//                    modifier = Modifier.size(24.dp)
//                )
//                Text(text = "Like")
//            }
//            Icon(
//                imageVector = Icons.Outlined.Comment,
//                contentDescription = null,
//                modifier = Modifier.size(24.dp)
//            )
//        }

        // Like and comment counts
//        Text(
//            text = "$likesCount likes\n$commentsCount comments",
//            modifier = Modifier
//                .padding(16.dp)
//                .fillMaxWidth(),
//            fontWeight = FontWeight.Bold
//        )
    }
}
fun convertMillisToDateTime(milliseconds: Long, pattern: String): String {
    val instant = Instant.ofEpochMilli(milliseconds)
    val dateTime = instant.atZone(ZoneId.systemDefault())
    val formatter = DateTimeFormatter.ofPattern(pattern)
    return dateTime.format(formatter)
}