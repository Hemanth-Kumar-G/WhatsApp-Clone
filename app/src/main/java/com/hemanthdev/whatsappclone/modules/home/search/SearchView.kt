package com.hemanthdev.whatsappclone.modules.home.search


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.hemanthdev.whatsappclone.data.model.User
import com.hemanthdev.whatsappclone.ui.composbles.CircularIndicatorMessage
import com.hemanthdev.whatsappclone.ui.composbles.SnackbarCustom
import com.hemanthdev.whatsappclone.ui.theme.WhatsAppCloneTheme
import com.hemanthdev.whatsappclone.ui.theme.black20Bold
import com.hemanthdev.whatsappclone.ui.theme.lightGray15
import com.hemanthdev.whatsappclone.ui.theme.link15
import kotlinx.coroutines.launch

@Composable
fun SearchView(
    userMessage: (String) -> Unit,
    searchViewModel: SearchViewModel = hiltViewModel()
) {

    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = SnackbarHostState()

    val loading: Boolean by searchViewModel.loading.observeAsState(initial = false)
    val showMessage: Boolean by searchViewModel.showMessage.observeAsState(initial = false)
    val message: String by searchViewModel.message.observeAsState(initial = "")
    val users: List<User> by searchViewModel.users.observeAsState(initial = emptyList())

    val showSnackbar = {
        coroutineScope.launch {
            when (snackbarHostState.showSnackbar(
                message = message,
            )) {
                SnackbarResult.Dismissed -> {
                    searchViewModel.snackbarDismissed()
                }
                SnackbarResult.ActionPerformed -> {
                    searchViewModel.snackbarDismissed()
                }
            }
        }
    }

    if (showMessage) {
        showSnackbar()
    }

    searchViewModel.getUsers()

    WhatsAppCloneTheme() {
        Surface(color = MaterialTheme.colors.background) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(15.dp)
                    ) {
                        items(users) { singleUser ->
                            Spacer(modifier = Modifier.height(5.dp))
                            UserCard(
                                profileImage = singleUser.profilePic,
                                title = singleUser.name,
                                subTitle = singleUser.userName,
                                thirdLine = singleUser.status,
                                userId = singleUser.userId,
                                userMessage = userMessage,
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                        }
                    }
                    if (loading) {
                        CircularIndicatorMessage(
                            message = "please_wait"
                        )
                    }
                }
                SnackbarCustom(snackbarHostState)
            }
        }
    }

}

@Composable
fun UserCard(
    profileImage: String,
    title: String,
    subTitle: String,
    thirdLine: String,
    userId: String,
    userMessage: (String) -> Unit
) {
    Card(
        backgroundColor = Color.White,
        elevation = 3.dp,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                userMessage(userId)
            }
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CircularUrlImage(url = profileImage, size = 50)
            Spacer(modifier = Modifier.height(10.dp))
            Column {
                Text(text = title, style = black20Bold)
                if (subTitle.isNotEmpty() || thirdLine.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(5.dp))
                }
                if (subTitle.isNotEmpty()) {
                    Text(text = "@$subTitle", style = link15)
                }
                if (subTitle.isNotEmpty() || thirdLine.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(5.dp))
                }
                if (thirdLine.isNotEmpty()) {
                    Text(text = thirdLine, style = lightGray15)
                }
            }
        }
    }
}

@Composable
fun CircularUrlImage(url: String, size: Int) {
    Image(
        painter = rememberImagePainter(
            data = url,
            builder = {
                crossfade(true)
            }
        ),
        contentDescription = "network image",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(size.dp)
            .clip(CircleShape)
    )
}

