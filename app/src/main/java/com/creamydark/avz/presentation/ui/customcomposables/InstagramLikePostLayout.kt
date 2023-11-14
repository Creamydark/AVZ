package com.creamydark.avz.presentation.ui.customcomposables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.creamydark.avz.enozItools.YenaTools

@Composable
fun InstagramLikePostLayout(
    username: String,
    caption: String,
    timestamp: Long,
    likesCount: Int=0,
    model: Any?,
    photoUrl: String,
    commentsCount: Int=0,
    userType:Boolean=true,
    onSelectDropDown: (p: Int) -> Unit
) {
    val timestampF = YenaTools().convertMillisToDateTime(timestamp)

    Column(
        modifier = Modifier
    ) {
        // User information
        Box(modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(alignment = Alignment.CenterStart),
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
            if (!userType){
                var expandedMenu by remember {
                    mutableStateOf(false)
                }
                CustomDropDownMenu(
                    modifier = Modifier.align(alignment = Alignment.BottomEnd),
                    onExpanded = {
                        expandedMenu = it
                    },
                    expanded = expandedMenu,
                ){
                    onSelectDropDown(it)
                }
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
                .aspectRatio(1f),
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

@Composable
private fun CustomDropDownMenu(
    modifier: Modifier=Modifier,
    onExpanded:(Boolean)->Unit,
    expanded:Boolean=false,
    onSelectDropDown:(p:Int)->Unit
) {
    Box(
        modifier = modifier
            .wrapContentSize(Alignment.TopStart)
    ) {
        IconButton(onClick = { onExpanded(true) }) {
            Icon(Icons.Default.MoreVert, contentDescription = "Localized description")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpanded(false) }
        ) {
            DropdownMenuItem(
                text = { Text("Edit") },
                onClick = {
                    onExpanded(false)
                },
                leadingIcon = {
                    Icon(
                        Icons.Outlined.Edit,
                        contentDescription = null,
                    )
                },
            )
            DropdownMenuItem(
                text = { Text("Delete") },
                onClick = {
                    onExpanded(false)
                    onSelectDropDown(1)
                },
                leadingIcon = {
                    Icon(
                        Icons.Outlined.Delete,
                        contentDescription = null,
                    )
                },
            )
        }
    }
}
