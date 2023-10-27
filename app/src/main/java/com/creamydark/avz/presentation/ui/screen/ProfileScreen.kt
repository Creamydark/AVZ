package com.creamydark.avz.presentation.ui.screen

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Send
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.creamydark.avz.R
import com.creamydark.avz.presentation.viewmodels.ProfileViewModel

@Composable
fun ProfileScreen(viewModel:ProfileViewModel,onclicked:(Int)->Unit) {

    val profileUri by viewModel._photoUri.collectAsState()

    val name by viewModel._name.collectAsState()

    val email by viewModel._email.collectAsState()

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

        ElevatedCard {
            LazyColumn{
                userData?.student?.let {
                        usertype ->
                    if (!usertype){
                        item {
                            ProfileListItem(title = "Upload Words", icon = Icons.Outlined.Send) {
                                onclicked(0)
                            }
                        }
                    }
                }

                item {
                    ProfileListItem(title = "About", icon = Icons.Outlined.Info) {
                        onclicked(1)

                    }
                }
                item {
                    ProfileListItem(title = "Sign Out", icon = Icons.Outlined.AccountBox) {
                        onclicked(2)

                    }
                }
            }
        }
        /*LazyColumn(modifier = Modifier.fillMaxWidth(), contentPadding = PaddingValues(16.dp)){
            item {
                userData?.student?.let {
                    usertype ->
                    if (!usertype){
                        ProfileListItem(title = "Upload Words", desc = "Add vocabulary words into database."){
                            onclicked(0)
                        }
                    }
                }
                ProfileListItem(title = "About", desc = "Information's about AVZ"){
                    onclicked(1)
                }
                ProfileListItem(title = "Sign out", desc = "Exit and log out current user."){
                    onclicked(2)
                }
            }
        }*/
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
private fun ProfileListItem(title:String,icon:ImageVector,clicked:()->Unit){
    Row(
        Modifier
            .fillMaxWidth()
            .padding()
            .clickable { clicked() }, verticalAlignment = Alignment.CenterVertically) {
        Icon(modifier = Modifier.padding(16.dp), imageVector = icon, contentDescription = "")
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