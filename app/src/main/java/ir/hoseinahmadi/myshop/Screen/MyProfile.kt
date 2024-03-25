package ir.hoseinahmadi.myshop.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Create
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import ir.hoseinahmadi.myshop.R
import ir.hoseinahmadi.myshop.ViewModel.DataStoreViewModel
import kotlinx.coroutines.launch

@Composable
fun MyProfileScreen(navHostController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        InfoAccount()
        Account()


//        for (item in 0..2) {
//            ProfileItem(onclick = { }, text = "loli", icon = Icons.Rounded.Email)
//        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileItem(onclick: () -> Unit, text: String, icon: ImageVector) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            onClick = { },
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
                    Text(text = text)
                }
                Icon(
                    Icons.Rounded.KeyboardArrowRight, contentDescription = ""
                )

            }
        }
        Divider(
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

var name by mutableStateOf("")
var isemail by mutableStateOf("")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoAccount(viewModel: DataStoreViewModel = hiltViewModel()) {
    val show by remember {
        showDialog
    }
    var pasword by remember {
        mutableStateOf("")
    }
    var pasword2 by remember {
        mutableStateOf("")
    }
    var isError by remember {
        mutableStateOf(false)
    }
    var myName = remember {
        name
    }
    var save by remember {
        mutableStateOf(false)
    }


    val scope = rememberCoroutineScope()
    LaunchedEffect(save) {
        if (save) {
            launch {
                scope.launch {
                    viewModel.saveName(name)
                }
            }
            launch {
                scope.launch {
                    viewModel.savePassword(pasword)
                }
                save = false
            }
        }
    }
    LaunchedEffect(save) {
        launch {
            scope.launch {
                viewModel.getEmail().let {
                    isemail = it
                }
            }
        }
        launch {
            scope.launch {
                viewModel.getPassword().let {
                    pasword = it
                    pasword2 = it
                }
            }
        }
        launch {
            scope.launch {
                viewModel.getName().let {
                    myName = it
                }
            }
        }
    }
    if (show) {
        ModalBottomSheet(
            onDismissRequest = {
                showDialog.value = false
                save = false
            })
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
                            modifier = Modifier.fillMaxWidth(),
                            value = isemail,
                            onValueChange = { },
                            readOnly = true,
                            label = {
                                Text(text = "your email")
                            },
                        )
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = name,
                            onValueChange = { name = it },
                            label = {
                                Text(text = "your name")
                            },
                        )

                        Spacer(modifier = Modifier.height(2.dp))
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = pasword,
                            onValueChange = { pasword = it },
                            label = {
                                Text(text = "your pasword")
                            },
                            isError = isError,
                            supportingText = {
                                if (isError)
                                    Text(text = "Your password is not the same as repeating it")
                            }
                        )
                        Spacer(modifier = Modifier.height(3.dp))
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = pasword2,
                            onValueChange = { pasword2 = it },
                            label = {
                                Text(text = "Repeat password")
                            },
                            isError = isError,
                            supportingText = {
                                if (isError)
                                    Text(text = "Your password is not the same as repeating it")
                            }
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Button(
                            enabled = pasword == pasword2,
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                isError = pasword != pasword2
                                if (!isError) {
                                    save = true
                                    showDialog.value = false
                                }
                                save = false
                            },
                            shape = RoundedCornerShape(9.dp)
                        ) {
                            Text(text = "save")
                        }

                    }

                }

            }
        }
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Account() {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        onClick = { showDialog.value =true},
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
                Image(painter = painterResource(id = R.drawable.avatar), contentDescription = "",
                    Modifier
                        .size(90.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Column(
                ) {
                    Text(text = name)
                    Text(text = isemail,
                        fontSize = 9.sp
                        )
                }
            }
                Icon(Icons.Rounded.KeyboardArrowRight,
                    contentDescription = "")


        }
    }
}

