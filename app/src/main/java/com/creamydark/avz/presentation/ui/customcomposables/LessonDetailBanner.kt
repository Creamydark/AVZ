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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.creamydark.avz.R
import com.creamydark.avz.enozItools.YenaTools
@Preview(showBackground = true)
@Composable
fun LessonDetailBannerPrev() {
    LessonDetailBanner(onDeleteDialogClicked = {}, onEditClicked = {})
}
@Composable
fun LessonDetailBanner(
    modifier: Modifier=Modifier,
    profile_pic:Any?=null,
    title:String="",
    timestamp:Long = 0,
    who_posted:String="",
    isStudent:Boolean=true,
    onDeleteDialogClicked:()->Unit,
    onEditClicked:()->Unit,
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Column(
            Modifier.padding(vertical = 22.dp, horizontal = 16.dp)
        ) {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterStart) {
                Row(
                    modifier = Modifier.align(Alignment.CenterStart),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .clip(shape = CircleShape)
                            .size(42.dp)
                            .background(MaterialTheme.colorScheme.onPrimary),
                        model = profile_pic,
                        contentDescription = "",
                        placeholder = painterResource(id = R.drawable.outline_person_24),
                        error = painterResource(id = R.drawable.outline_person_24),
                        contentScale = ContentScale.Crop,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        val date = YenaTools().simpleDateFormatter(timestamp)
                        val name = who_posted.ifBlank {
                            "Unknown"
                        }
                        Text(
                            text = name,
                            color = MaterialTheme.colorScheme.onPrimary,
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            text = date,
                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
                if (isStudent) {
                    Row(
                        modifier = Modifier.align(Alignment.CenterEnd),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = {
//                                deleteDialogConfirmationState = true
                                      onDeleteDialogClicked()
                            },
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Delete,
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                        IconButton(
                            onClick = {
//                                showBottomSheet = true
                                      onEditClicked()
                            },
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Edit,
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}