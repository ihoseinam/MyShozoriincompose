package ir.hoseinahmadi.myshop.Screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.rounded.Send
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Build
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import ir.hoseinahmadi.myshop.Navigation.Screen
import ir.hoseinahmadi.myshop.R
import ir.hoseinahmadi.myshop.ViewModel.DataStoreViewModel
import ir.hoseinahmadi.myshop.ui.theme.h1
import ir.hoseinahmadi.myshop.ui.theme.h2
import ir.hoseinahmadi.myshop.ui.theme.h3
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyProfileScreen(navHostController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    text = "Profile Screen",
                    style = h1,
                    color = Color.Black
                )
            },)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Account()
            InfoAccount(navHostController)
            ProfileItem(onclick = {
                navHostController.navigate(Screen.Fav.route) {
                    popUpTo(Screen.Profile.route) {
                        inclusive = true
                    }
                }
            }, text = "My favorites list", icon = Icons.Rounded.FavoriteBorder)
            ProfileItem(onclick = { /*TODO*/ }, text = "Update check", icon = Icons.Outlined.Build)
            ProfileItem(
                onclick = {},
                text = "Message to support",
                icon = Icons.Outlined.Email
            )

        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileItem(onclick: @Composable () -> Unit, text: String, icon: ImageVector) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            onClick = { onclick },
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(0.5.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 20.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(icon, contentDescription = "")
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(text = text, style = h3, color = Color.Black)
                }
                Icon(
                    Icons.AutoMirrored.Rounded.KeyboardArrowRight, contentDescription = ""
                )

            }
        }
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .alpha(0.4f)
                .padding(horizontal = 12.dp),
            thickness = 0.2.dp,
            color = Color.DarkGray
        )
    }
}

var showDialog = mutableStateOf(false)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoAccount(
    navHostController: NavHostController,
    viewModel: DataStoreViewModel = hiltViewModel()
) {
    val show by remember {
        showDialog
    }
    var pasword by remember {
        mutableStateOf("")
    }
    var pasword2 by remember {
        mutableStateOf("")
    }
    var name by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }

    val scope = rememberCoroutineScope()

    LaunchedEffect(Dispatchers.Main) {
        launch {
            scope.launch {
                viewModel.getEmail()
                viewModel.getEmail.collectLatest {
                    email = it
                }
            }
        }
        launch {
            scope.launch {
                viewModel.getPassword()
                viewModel.getPassword.collectLatest {
                    pasword = it
                }
            }
        }
        launch {
            scope.launch {
                viewModel.getPassword()
                viewModel.getPassword.collectLatest {
                    pasword2 = it
                }
            }
        }
        launch {
            scope.launch {
                viewModel.getName()
                viewModel.getName.collectLatest {
                    name = it
                }
            }
        }
    }
    if (show) {
        ModalBottomSheet(
            onDismissRequest = { showDialog.value = false })
        {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp),
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        OutlinedTextField(
                            textStyle = h3,
                            modifier = Modifier.fillMaxWidth(),
                            value = email,
                            onValueChange = { },
                            readOnly = true,
                            label = {
                                Text(text = "your email", style = h3)
                            },
                        )
                        OutlinedTextField(
                            textStyle = h3,
                            modifier = Modifier.fillMaxWidth(),
                            value = name,
                            onValueChange = { name = it },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                            ),
                            label = {
                                Text(text = "your name",style = h3)
                            },
                        )

                        Spacer(modifier = Modifier.height(2.dp))
                        OutlinedTextField(
                            textStyle = h3,
                            modifier = Modifier.fillMaxWidth(),
                            value = pasword,
                            onValueChange = { pasword = it },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password,
                            ),
                            label = {
                                Text(text = "your password",style = h3)
                            },
                        )
                        Spacer(modifier = Modifier.height(3.dp))
                        OutlinedTextField(
                            textStyle = h3,
                            modifier = Modifier.fillMaxWidth(),
                            value = pasword2,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password,
                            ),
                            onValueChange = { pasword2 = it },
                            label = {
                                Text(text = "Repeat password",style = h3)
                            },
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Button(
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Black,
                                contentColor = Color.White
                            ),
                            enabled = pasword == pasword2,
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                viewModel.saveName(name)
                                viewModel.savePassword(pasword)
                                showDialog.value = false
                            },
                            shape = RoundedCornerShape(9.dp)
                        ) {
                            Text(text = "save",style = h3)
                        }
                    }

                }

            }
        }
    }
}

var name = mutableStateOf("")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Account(viewModel: DataStoreViewModel = hiltViewModel()) {
    var email by remember {
        mutableStateOf("بدون ایمیل ")
    }
    LaunchedEffect(showDialog.value) {
        launch {
            viewModel.getName()
            viewModel.getName.collectLatest {
                name.value = it
            }
        }
        launch {
            viewModel.getEmail()
            viewModel.getEmail.collectLatest {
                email = it
            }
        }
    }
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            onClick = {
                showDialog.value = true
            },
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(0.5.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 17.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.avatar), contentDescription = "",
                        Modifier
                            .size(90.dp)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Column {
                        Text(text = name.value.trim(),
                            style = h2, color = Color.Black)
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(text = email, fontSize = 10.sp, style = h3, color = Color.DarkGray)
                    }
                }
                Icon(
                    Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                    contentDescription = ""
                )

            }
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .alpha(0.18f),
            thickness = 5.dp,
            color = Color.DarkGray
        )
    }

}

