package com.creamydark.avz.presentation.ui.screen

 import android.Manifest
 import android.content.ContentResolver
 import android.content.Context
 import android.net.Uri
 import android.provider.MediaStore
 import android.util.Log
 import android.widget.Toast
 import androidx.activity.compose.rememberLauncherForActivityResult
 import androidx.activity.result.contract.ActivityResultContracts
 import androidx.compose.foundation.clickable
 import androidx.compose.foundation.layout.Column
 import androidx.compose.foundation.layout.Row
 import androidx.compose.foundation.layout.Spacer
 import androidx.compose.foundation.layout.fillMaxSize
 import androidx.compose.foundation.layout.fillMaxWidth
 import androidx.compose.foundation.layout.height
 import androidx.compose.foundation.layout.padding
 import androidx.compose.foundation.layout.size
 import androidx.compose.foundation.layout.width
 import androidx.compose.material.icons.Icons
 import androidx.compose.material.icons.outlined.Close
 import androidx.compose.material.icons.outlined.ThumbUp
 import androidx.compose.material3.AlertDialog
 import androidx.compose.material3.Button
 import androidx.compose.material3.ExperimentalMaterial3Api
 import androidx.compose.material3.Icon
 import androidx.compose.material3.IconButton
 import androidx.compose.material3.MaterialTheme
 import androidx.compose.material3.OutlinedTextField
 import androidx.compose.material3.Scaffold
 import androidx.compose.material3.Text
 import androidx.compose.material3.TextButton
 import androidx.compose.material3.TopAppBar
 import androidx.compose.runtime.Composable
 import androidx.compose.runtime.DisposableEffect
 import androidx.compose.runtime.getValue
 import androidx.compose.runtime.mutableStateOf
 import androidx.compose.runtime.remember
 import androidx.compose.runtime.setValue
 import androidx.compose.ui.Alignment
 import androidx.compose.ui.Modifier
 import androidx.compose.ui.graphics.vector.ImageVector
 import androidx.compose.ui.platform.LocalContext
 import androidx.compose.ui.text.style.TextAlign
 import androidx.compose.ui.unit.dp
 import androidx.compose.ui.window.Dialog
 import androidx.compose.ui.window.DialogProperties
 import androidx.lifecycle.compose.collectAsStateWithLifecycle
 import androidx.lifecycle.viewmodel.compose.viewModel
 import androidx.navigation.NavHostController
 import coil.compose.AsyncImage
 import com.creamydark.avz.R
 import com.creamydark.avz.domain.model.LessonData
 import com.creamydark.avz.domain.model.ResultType
 import com.creamydark.avz.presentation.viewmodels.LessonsViewModel
 import com.google.accompanist.permissions.ExperimentalPermissionsApi
 import com.google.accompanist.permissions.rememberPermissionState
 import com.rizzi.bouquet.ResourceType
 import com.rizzi.bouquet.VerticalPDFReader
 import com.rizzi.bouquet.rememberVerticalPdfReaderState
 import java.io.File
 import java.io.FileOutputStream
 import java.io.InputStream


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun UploadLessonsScreen(
    navHostController: NavHostController,
    viewModel: LessonsViewModel = viewModel()
) {

    val createLessonResult by viewModel.createLessonResult.collectAsStateWithLifecycle(initialValue = ResultType.loading())
    var selectedPDF by remember {
        mutableStateOf<Uri?>(null)
    }

    DisposableEffect(key1 = Unit){
        onDispose {
            viewModel.onClearedModel()
        }
    }

    var title by remember {
        mutableStateOf("")
    }
    var subTitle by remember {
        mutableStateOf("")
    }
    var description by remember {
        mutableStateOf("")
    }

    var showDialog by remember {
        mutableStateOf(false)
    }
    var showSuccessDialog by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    val errorShown = remember {
        mutableStateOf(false)
    }
    when(createLessonResult){
        is ResultType.Error -> {
            val error = (createLessonResult as ResultType.Error).exception
            if (!errorShown.value) {
                Toast.makeText(context, error.message?:"Unknown Error", Toast.LENGTH_SHORT).show()
                errorShown.value = true
            }
        }
        ResultType.Loading -> {

        }
        is ResultType.Success -> {
            val data = (createLessonResult as ResultType.Success<String>).data
//            Toast.makeText(context,data,Toast.LENGTH_LONG).show()
            showSuccessDialog = true
        }
    }


    if (showSuccessDialog){
        SuccessDialog(
            dialogTitle = "Upload Successful",
            dialogText = "Your lesson has been successfully uploaded. Great job!",
            onConfirmation = {
                showSuccessDialog = false.also {
                    viewModel.onClearedModel()
                }
            }
        )
    }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
            uri: Uri? ->
        // Handle the selected PDF file URI
        selectedPDF = uri
    }
    if (showDialog){
        MinimalDialog(selectedPDF) {
            showDialog = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = title,
            onValueChange = {
                title = it
            },
            label = {
                Text(text = "Title")
            },
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = subTitle,
            onValueChange = {
                subTitle = it
            },
            label = {
                Text(text = "Sub title")
            },
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = description,
            onValueChange = {
                description = it
            },
            label = {
                Text(text = "Description")
            },
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(16.dp))

        //PDF Logo
        selectedPDF?.let {
            Row(
                modifier = Modifier.clickable {
                    showDialog = true
                },
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(model = R.drawable.icon_pdf_file, contentDescription = "",modifier = Modifier.size(55.dp))
//                Icon(painterResource(id = R.drawable.icon_pdf_file), contentDescription = "", modifier = Modifier.size(55.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Preview selected file", style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.secondary)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                launcher.launch("application/pdf")
            },
        ) {
            Text(text = "Attach PDF file")
        }
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                val data = LessonData(
                    title = title,
                    subtitle = subTitle,
                    description = description
                )
                viewModel.createLesson(data = data,uri = selectedPDF)
            },
        ) {
            Text(text = "Publish")
        }
    }
}
@Composable
private fun SuccessDialog(
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
                Text("Ok")
            }
        }
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MinimalDialog(pdf:Uri?,onDismissRequest: () -> Unit) {
    Dialog(
        onDismissRequest = { onDismissRequest() },
        properties = DialogProperties(
            usePlatformDefaultWidth = false // Disable platform default width
        )
    ) {
        if (pdf !=null){
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(text = "PDF Preview")
                        },
                        actions = {
                            IconButton(
                                onClick = { onDismissRequest() }
                            ) {
                                Icon(imageVector = Icons.Outlined.Close, contentDescription = "")
                            }
                        }
                    )
                }
            ) {
                inner->

                val pdfState = rememberVerticalPdfReaderState(
                    resource = ResourceType.Local(pdf),
                    isZoomEnable = true
                )
                VerticalPDFReader(
                    state = pdfState,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = inner.calculateTopPadding())
                )
//                PdfViewer(modifier = Modifier.padding(top = inner.calculateTopPadding()),uri = getFile(LocalContext.current,pdf).toUri())
            }
        }
    }
}
fun getFile(mContext: Context, documentUri: Uri): File {
    val inputStream = mContext.contentResolver?.openInputStream(documentUri)
    var file: File
    inputStream.use { input ->
        file = File(mContext.cacheDir, System.currentTimeMillis().toString()+".pdf")
        FileOutputStream(file).use { output ->
            val buffer = ByteArray(4 * 1024) // or other buffer size
            var read: Int = -1
            while (input?.read(buffer).also {
                    if (it != null) {
                        read = it
                    }
                } != -1) {
                output.write(buffer, 0, read)
            }
            output.flush()
        }
    }
    return file
}
fun getPathFromUri(context: Context, uri: Uri): String? {
    val contentResolver = context.contentResolver
    val projection = arrayOf(MediaStore.Images.Media.DATA)
    val cursor = contentResolver.query(uri, projection, null, null, null)
    cursor?.use {
        val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        it.moveToFirst()
        return it.getString(columnIndex)
    }

    return null
}
fun uriToFile(context: Context, uri: Uri): File? {
    val contentResolver: ContentResolver = context.contentResolver
    val file = File(context.cacheDir, "temp_file")
    try {
        val inputStream: InputStream? = contentResolver.openInputStream(uri)
        if (inputStream != null) {
            FileOutputStream(file).use { outputStream ->
                val buffer = ByteArray(4 * 1024) // buffer size
                var read: Int
                while (inputStream.read(buffer).also { read = it } != -1) {
                    outputStream.write(buffer, 0, read)
                }
                outputStream.flush()
            }
            inputStream.close()
            return file
        }
    } catch (e: Exception) {
        e.printStackTrace()
        Log.d("uriToFile", "uriToFile: $e")
    }
    return null
}

fun convertUriToFile(context: Context, uri: Uri): File {
    val contentResolver: ContentResolver = context.contentResolver
    val inputStream: InputStream? = contentResolver.openInputStream(uri)
    val tempFile = File.createTempFile("temp", null, context.cacheDir)

    inputStream?.use { input ->
        val outputStream = FileOutputStream(tempFile)
        outputStream.use { output ->
            val buffer = ByteArray(4 * 1024)
            while (true) {
                val byteCount = input.read(buffer)
                if (byteCount < 0) break
                output.write(buffer, 0, byteCount)
            }
            output.flush()
        }
    }
    return tempFile
}
