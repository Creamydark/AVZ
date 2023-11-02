package com.creamydark.avz.presentation.ui.screen
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.creamydark.avz.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UploadPostScreen(
    onUploadClick: (caption:String, image:Uri?) -> Unit
) {
    var caption by remember { mutableStateOf("") }
    var image by remember {
        mutableStateOf<Uri?>(null)
    }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            // Handle the selected photo, e.g., save it or display it
            image = uri
        }
    }
    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = "Upload a photo of your updates.",
            style = MaterialTheme.typography.headlineLarge,
        )
        Spacer(modifier = Modifier.size(8.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            label = {
                Text(text = "Caption")
            },
            value = caption,
            onValueChange = {
                caption = it
            },
            placeholder = {
                Text(text = "place your caption here.")
            }
        )
        Spacer(modifier = Modifier.size(8.dp))
        SubcomposeAsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(16.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.DarkGray.copy(alpha = 0.1f))
                .clickable {
                    launcher.launch("image/*")
                }
                ,
            model = image,
            contentDescription = "",
            contentScale = ContentScale.Crop
        ) {
            when(painter.state){
                is AsyncImagePainter.State.Success -> {
                    SubcomposeAsyncImageContent()
                }
                else -> {
                    Column(
                        Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(painterResource(id = R.drawable.outline_image_24), contentDescription = "")
                        Text(text = "Select file")
                    }
                }
            }
        }
//        AsyncImage(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(300.dp)
////                .clip(RoundedCornerShape(32.dp))
//                .clickable {
//                    launcher.launch("image/*")
//                },
//            model = image,
//            placeholder = painterResource(id = R.drawable.outline_image_24),
//            error = painterResource(id = R.drawable.outline_image_24),
//            contentDescription = "",
//            contentScale = ContentScale.Crop
//        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(16.dp),
            onClick = {
                onUploadClick(caption,image)
            },
        ) {
            Text(text = "Upload")
        }
    }

}