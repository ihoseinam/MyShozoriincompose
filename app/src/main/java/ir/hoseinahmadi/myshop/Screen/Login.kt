package ir.hoseinahmadi.myshop.Screen

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import ir.hoseinahmadi.myshop.Navigation.Screen
import ir.hoseinahmadi.myshop.ViewModel.VerifyApiUserViewModel
import ir.hoseinahmadi.myshop.component.Loading3Dots
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
        SendCode(viewModel)
    }
    AnimatedVisibility(checkSend) {
        VerifyCode(viewModel = viewModel, navHostController = navHostController)
    }


}

@Composable
fun SendCode(viewModel: VerifyApiUserViewModel) {
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

//                    viewModel.messageSendCode.collectLatest {
//                        Log.e("pasi", it)
//                    }
            startSend = false
        }

    }
    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    supportingText = {
                        if (isError) {
                            Text(text = "enter email zori")
                        }
                    },
                    placeholder = {
                        Text(text = "enter email")
                    },
                    isError = isError
                )

                Button(
                    shape = RoundedCornerShape(12.dp),
                    enabled = email.isNotEmpty(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
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
                            text = "login",
                            fontSize = 14.sp
                        )
                    }
                }
            }

        }


    }

}

@Composable
fun VerifyCode(viewModel: VerifyApiUserViewModel, navHostController: NavHostController) {
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

    Box(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(
                    value = code,
                    onValueChange = { code = it },
                    isError = isError
                )
                Button(
                    enabled = code.isNotEmpty(),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
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
                        Text(text = "ta iid")

                    }

                }

            }
        }


        if (checkLogin) {
            navHostController.navigate(Screen.Home.route) {
                popUpTo(Screen.Login.route) {
                    inclusive = true
                }
            }
        }
    }
}


