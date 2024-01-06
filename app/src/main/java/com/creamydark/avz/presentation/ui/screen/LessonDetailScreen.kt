package com.creamydark.avz.presentation.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.creamydark.avz.R
import com.creamydark.avz.domain.model.ResultType
import com.creamydark.avz.presentation.ui.customcomposables.LessonDetailBanner
import com.creamydark.avz.presentation.viewmodels.LessonsViewModel
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LessonDetailScreen(
    navHostController: NavHostController,
    lessonsViewModel:LessonsViewModel
) {
    val removeLessonResult by lessonsViewModel.removeLessonResult.collectAsStateWithLifecycle()
    val updateLessonResult by lessonsViewModel.updateLessonResult.collectAsStateWithLifecycle()
    val userData by lessonsViewModel.userData.collectAsStateWithLifecycle()
    val data by lessonsViewModel.selectedLesson.collectAsStateWithLifecycle()
    var deleteDialogConfirmationState by remember { mutableStateOf(false) }
    var successDeleteDialogConfirmationState by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )


    when (removeLessonResult){
        is ResultType.Success -> {
            successDeleteDialogConfirmationState = true
        }
        else ->{}
    }



    if (successDeleteDialogConfirmationState){
        SuccessDeleteDialog(
            dialogTitle = "Deletion Successful",
            dialogText = "The item has been successfully deleted.",
            icon = Icons.Outlined.Done,
            onConfirmation = {
                navHostController.popBackStack()
                successDeleteDialogConfirmationState = false
                lessonsViewModel.removeResultReset()
            },
        )
    }
    if (deleteDialogConfirmationState){
        DeleteDialogConfirmation(
            dialogTitle = "Confirm Deletion",
            dialogText = "Are you sure you want to delete this item? This action cannot be undone.",
            icon = Icons.Outlined.Delete,
            onConfirmation = {
                data?.let {
                    toDeleteData ->
                    lessonsViewModel.removeLessonFromDatabase(toDeleteData)
                }
                deleteDialogConfirmationState = false
            },
            onDismissRequest = {
                deleteDialogConfirmationState = false
            },
        )
    }

    if (showBottomSheet){
        ModalBottomSheet(
            modifier = Modifier.imePadding(),
            sheetState = sheetState,
            onDismissRequest = {
                showBottomSheet = false
            }
        ) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)) {
                var title by remember {
                    mutableStateOf(data?.title?:"")
                }
                var description by remember {
                    mutableStateOf(data?.description?:"")
                }
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text(text = "Title")
                    },
                    value = title,
                    onValueChange = {
                        title = it
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text(text = "Description")
                    },
                    value = description,
                    onValueChange = {
                        description = it
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
//                        viewModel.editPost(selectedPostData.copy(caption = caption))
                        lessonsViewModel.updateLesson(data?.copy(title = title, description = description))
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            showBottomSheet = false
                        }
                    },
                ) {
                    Text(text = "Confirm")
                }
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        LessonDetailBanner(
            modifier = Modifier.padding(vertical = 22.dp),
            title = data?.title ?: "",
            profile_pic = data?.who_posted_profilePhoto,
            timestamp = data?.timestamp?.time?:0,
            isStudent = userData?.student != true,
            who_posted = data?.who_posted_name?:"",
            onDeleteDialogClicked = {
                deleteDialogConfirmationState = true
            },
            onEditClicked = {
                showBottomSheet = true
            }
        )
        Text(text = "Description", style = MaterialTheme.typography.titleMedium)
        Text(
            text = data?.description ?: "",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
        Spacer(modifier = Modifier.height(16.dp))
        ElevatedButton(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            onClick = {
                Toast.makeText(context,"Soon to be added",Toast.LENGTH_SHORT).show()
            }
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.Absolute.Left,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(painter = painterResource(id = R.drawable.outline_play_circle_24), contentDescription = "")
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Play Quiz",style = MaterialTheme.typography.titleSmall)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        ElevatedButton(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            onClick = {
                val encodedUrl = URLEncoder.encode("${data?.pdf_link}", StandardCharsets.UTF_8.toString())
                navHostController.navigate("pdf_viewer/$encodedUrl/{${data?.id}}")
            }
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.Absolute.Left,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(painter = painterResource(id = R.drawable.outline_slideshow_24), contentDescription = "")
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Preview Lesson", style = MaterialTheme.typography.titleSmall)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DeleteDialogConfirmation(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String = "",
    dialogText: String= "",
    icon: ImageVector = Icons.Outlined.ThumbUp,
) {
    AlertDialog(
        icon = {
            Icon(icon, contentDescription = "Example Icon")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText, textAlign = TextAlign.Center)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Dismiss")
            }
        }
    )
}




@Composable
private fun SuccessDeleteDialog(
    onConfirmation: () -> Unit,
    dialogTitle: String = "",
    dialogText: String= "",
    icon: ImageVector = Icons.Outlined.ThumbUp,
) {
    AlertDialog(
        icon = {
            Icon(icon, contentDescription = "Example Icon")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText, textAlign = TextAlign.Center)
        },
        onDismissRequest = {

        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Confirm")
            }
        }
    )
}

