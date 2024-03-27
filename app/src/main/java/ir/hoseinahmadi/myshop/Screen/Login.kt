package ir.hoseinahmadi.myshop.Screen

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import ir.hoseinahmadi.myshop.Navigation.Screen
import ir.hoseinahmadi.myshop.R
import ir.hoseinahmadi.myshop.ViewModel.DataStoreViewModel
import ir.hoseinahmadi.myshop.ViewModel.VerifyApiUserViewModel
import ir.hoseinahmadi.myshop.component.Loading3Dots
import ir.hoseinahmadi.myshop.ui.theme.h1
import ir.hoseinahmadi.myshop.ui.theme.h2
import ir.hoseinahmadi.myshop.ui.theme.h3
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

var checkSend = mutableStateOf(false)
var email = mutableStateOf("")


@Composable
fun LoginScreen(
    navHostController: NavHostController,
    viewModel: VerifyApiUserViewModel = hiltViewModel()
) {
    val checkSend by remember {
        checkSend
    }
    AnimatedVisibility(!checkSend) {
        SendCode(viewModel, navHostController)
    }
    AnimatedVisibility(checkSend) {
        VerifyCode(viewModel, navHostController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SendCode(viewModel: VerifyApiUserViewModel, navHostController: NavHostController) {
    val scop = rememberCoroutineScope()
    var email by remember {
        email
    }
    var isError by remember {
        mutableStateOf(false)
    }
    var loading by remember {
        mutableStateOf(false)
    }
    var startSend by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(startSend) {
        if (startSend) {
            launch {
                scop.launch {
                    viewModel.loadingSendCode.collect {
                        loading = it
                    }
                }
            }
            launch {
                scop.launch {
                    viewModel.sendCode(email)
                    viewModel.resultSendCode.collectLatest {
                        checkSend.value = it
                    }
                }
            }
            launch {
                scop.launch {
                    viewModel.isErrorSenCode.collectLatest {
                        isError = it
                    }
                }
            }
            startSend = false
        }

    }
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                ),
                title = {
                Text(text = "Login or Register", style = h1, color = Color.Black)
            })

        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F4F2))
                .padding(it)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OutlinedTextField(
                        textStyle = h3,
                        shape = RoundedCornerShape(8.dp),
                        colors = TextFieldDefaults.colors(
                            focusedLeadingIconColor = Color.Black,
                            unfocusedLeadingIconColor = Color.DarkGray,
                            focusedIndicatorColor = Color(0x23666464),
                            unfocusedIndicatorColor = Color.Transparent,
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp),
                        singleLine = true,
                        value = email,
                        onValueChange = { email = it },
                        supportingText = {
                            if (isError) {
                                Text(text = "Please enter the correct email!")
                            }
                        },

                        leadingIcon = {
                            Icon(
                                Icons.Rounded.Email,
                                contentDescription = ""
                            )
                        },
                        placeholder = {
                            Text(text = "enter email")
                        },
                        isError = isError
                    )
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(65.dp)
                            .padding(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF000000),
                            contentColor = Color.White,
                            disabledContentColor = Color.DarkGray
                        ),
                        shape = RoundedCornerShape(9.dp),
                        enabled = email.isNotEmpty(),

                        onClick = {
                            if (email.isNotEmpty()) {
                                startSend = true
                            } else {
                                isError = true
                            }
                        }) {
                        AnimatedVisibility(loading) {
                            Loading3Dots(isDark = false)
                        }
                        AnimatedVisibility(!loading) {
                            Text(
                                style = h2,
                                text = "Send verification code",
                                color = Color.White
                            )
                        }
                    }
                }
            }
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopCenter
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                ) {
                    Spacer(modifier = Modifier.height(60.dp))
                    Text(
                        style = h1,
                        text = "Hello !",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        style = h2,
                        text = "Welcome, Please enter your email address to register or log in to your account",
                        color = Color.Black
                    )

                }
            }

        }

    }

}

@Composable
fun VerifyCode(
    viewModel: VerifyApiUserViewModel,
    navHostController: NavHostController,
    viewModel2: DataStoreViewModel = hiltViewModel()
) {
    var code by remember {
        mutableStateOf("")
    }
    var checkLogin by remember {
        mutableStateOf(false)
    }
    var isError by remember {
        mutableStateOf(false)
    }
    val scop = rememberCoroutineScope()
    var startVerify by remember {
        mutableStateOf(false)
    }
    var loading by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(startVerify) {
        if (startVerify) {
            launch {
                scop.launch {
                    viewModel.verifyUser(email.value, code)
                    viewModel.resultVerify.collectLatest {
                        checkLogin = it
                    }
                }
            }
            launch {
                scop.launch {
                    viewModel.resultVerify.collectLatest { isError = !it }
                }
            }
            launch {
                scop.launch {
                    viewModel.loadingVerifyCode.collectLatest {
                        loading = it
                    }
                }
            }
            startVerify = false
        }

    }
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { checkSend.value = false }) {
                    Icon(
                        Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = "",
                    )
                }
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F4F2))
                .padding(it)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 6.dp),
                contentAlignment = Alignment.TopStart
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                ) {
                    Spacer(modifier = Modifier.height(40.dp))
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = "Enter the verification code",
                        color = Color.Black,
                        style = h1
                    )
                    Spacer(modifier = Modifier.height(7.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = R.drawable.sendmail),
                            contentDescription = ""
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = "The verification code has been sent to your email",
                            color = Color.DarkGray,
                            style = h2
                        )
                    }

                }
            }
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OutlinedTextField(
                        textStyle = h3,
                        shape = RoundedCornerShape(8.dp),
                        colors = TextFieldDefaults.colors(
                            focusedLeadingIconColor = Color.Black,
                            unfocusedLeadingIconColor = Color.DarkGray,
                            focusedIndicatorColor = Color(0x23666464),
                            unfocusedIndicatorColor = Color.Transparent,
                        ),

                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp),
                        singleLine = true,
                        value = email.value, onValueChange = {},
                        readOnly = true,
                        leadingIcon = {
                            Icon(Icons.Rounded.AccountCircle, contentDescription = "")
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        textStyle = h3,
                        shape = RoundedCornerShape(8.dp),
                        colors = TextFieldDefaults.colors(
                            focusedLeadingIconColor = Color.Black,
                            unfocusedLeadingIconColor = Color.DarkGray,
                            focusedIndicatorColor = Color(0x23666464),
                            unfocusedIndicatorColor = Color.Transparent,
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp),
                        singleLine = true,
                        value = code,
                        onValueChange = { code = it },
                        isError = isError,
                        supportingText = {
                            if (isError) {
                                Text(text = "Enter the verification code correctly", style = h3)
                            }
                        },
                        placeholder = {
                            Text(text = "enter code")
                        },

                        )

                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Black,
                            contentColor = Color.White,
                            disabledContentColor = Color.DarkGray
                        ),
                        enabled = code.isNotEmpty(),
                        shape = RoundedCornerShape(9.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(65.dp)
                            .padding(8.dp),
                        onClick = {
                            if (code.isNotEmpty()) {
                                startVerify = true
                            } else {
                                isError = true
                            }
                        }) {
                        AnimatedVisibility(visible = loading) {
                            Loading3Dots(isDark = false)
                        }
                        AnimatedVisibility(visible = !loading) {
                            Text(text = "Confirm", style = h2)
                        }

                    }

                }
            }

            if (checkLogin) {
                LaunchedEffect(true) {
                    launch {
                        viewModel2.saveEmail(email.value)
                    }
                    launch {
                        viewModel2.saveCheckLogin(true)
                    }
                }
                navHostController.navigate(Screen.Profile.route) {
                    popUpTo(Screen.Profile.route) {
                        inclusive = true
                    }
                }
            }
        }

    }
}


