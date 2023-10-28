package com.creamydark.avz.presentation.ui.screen
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.creamydark.avz.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UploadPostScreen(
    onUploadClick: (caption:String, image:Uri?) -> Unit
) {


    var caption by remember { mutableStateOf("") }
    val context = LocalContext.current

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
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(16.dp),
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = image,
                placeholder = painterResource(id = R.drawable.app_logo_hd_png),
                error = painterResource(id = R.drawable.app_logo_hd_png),
                contentDescription = "",
                contentScale = ContentScale.Crop
            )
        }
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
        )
        Spacer(modifier = Modifier.size(8.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(4.dp),
            onClick = {
                launcher.launch("image/*")
            },
        ) {
            Text(text = "Choose photo")
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(4.dp),
            onClick = {
                      onUploadClick(caption,image)
            },
        ) {
            Text(text = "Upload")
        }
    }

}