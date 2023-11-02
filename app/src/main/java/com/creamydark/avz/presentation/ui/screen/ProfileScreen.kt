package com.creamydark.avz.presentation.ui.screen

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.Card
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
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
        /*Text(
            text = "Options",
            style = MaterialTheme.typography.bodyLarge,
        )*/
        Card(
//            colors = CardDefaults.cardColors(containerColor = Color.Black.copy(alpha = 0.10f))
        ) {
            LazyColumn{
                userData?.student?.let {
                        usertype ->
                    if (!usertype){
                        item {
                            ProfileListItem(title = "Upload Words") {
                                onclicked(0)
                            }
                        }
                        item {
                            ProfileListItem(title = "Post Announcements") {
                                onclicked(1)
                            }
                        }
                    }
                }
                item {
                    ProfileListItem(title = "About") {
                        onclicked(2)

                    }
                }
                item {
                    ProfileListItem(title = "Sign Out") {
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
//                .border(width = 5.dp,MaterialTheme.colorScheme.primary, shape = CircleShape)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = name,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = desc,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            textAlign = TextAlign.Center,
            lineHeight = 16.sp
        )
    }
}
@Composable
private fun ProfileListItem(title:String,clicked:()->Unit){
    Row(
        Modifier
            .fillMaxWidth()
            .padding()
            .clickable { clicked() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = 20.dp),
            text = title,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
        Icon(
            modifier = Modifier.padding(16.dp),
            imageVector = Icons.Rounded.KeyboardArrowRight,
            contentDescription = "",
            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
    }
}