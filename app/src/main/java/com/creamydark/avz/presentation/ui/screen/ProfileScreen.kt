package com.creamydark.avz.presentation.ui.screen

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.creamydark.avz.R
import com.creamydark.avz.presentation.viewmodels.ProfileViewModel

@Composable
fun ProfileScreen(viewModel:ProfileViewModel,onclicked:(Int)->Unit) {

    val currentUserData by viewModel.currentUserData.collectAsStateWithLifecycle()

    val profileUri = currentUserData?.photoUri

    val name = currentUserData?.displayName

    val email = currentUserData?.email

    var userTypeText by remember {
        mutableStateOf("")
    }

    val userData by viewModel._userData.collectAsState()

    userData?.student?.let {
        userType ->
        if (userType){
            userTypeText = "Student"
        }else{
            userTypeText = "Teacher"
        }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 16.dp)) {
        ProfileHeader(profileUri = profileUri, name = name?:"", desc = "$email\n${userTypeText}")
        Log.d("ProfileScreen", "ProfileScreen: ${profileUri?.toString()}")
        ElevatedCard {
            LazyColumn{
                userData?.student?.let {
                        usertype ->
                    if (!usertype){
                        item {
                            ProfileListItem(title = "Upload Words", icon = painterResource(id = R.drawable.outline_cloud_upload_24)) {
                                onclicked(0)
                            }
                        }
                        item {
                            ProfileListItem(title = "Post Announcements", icon = painterResource(id = R.drawable.outline_post_add_24)) {
                                onclicked(1)
                            }
                        }
                    }
                }

                item {
                    ProfileListItem(title = "About", icon = painterResource(id = R.drawable.outline_info_24)) {
                        onclicked(2)

                    }
                }
                item {
                    ProfileListItem(title = "Sign Out", icon = painterResource(id = R.drawable.outline_account_circle_24)) {
                        onclicked(3)

                    }
                }
            }
        }
    }
}
@Composable
private fun ProfileHeader(profileUri : Uri?, name:String, desc:String) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = profileUri, // Replace with your profile image resource
            placeholder =  painterResource(id = R.drawable.ic_google),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = name,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = desc,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            textAlign = TextAlign.Center
        )
    }
}
@Composable
private fun ProfileListItem(title:String,icon:Painter,clicked:()->Unit){
    Row(
        Modifier
            .fillMaxWidth()
            .padding()
            .clickable { clicked() }, verticalAlignment = Alignment.CenterVertically) {
        Icon(modifier = Modifier.padding(16.dp), painter = icon, contentDescription = "")
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .weight(1f),
            text = title,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}