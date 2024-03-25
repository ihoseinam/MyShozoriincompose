package ir.hoseinahmadi.myshop.Screen

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import ir.hoseinahmadi.myshop.Navigation.Screen
import ir.hoseinahmadi.myshop.ViewModel.VerifyApiUserViewModel
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
    Box(modifier = Modifier.fillMaxSize()) {

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
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
        }
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    isError = email.isEmpty()
                    if (email.isNotEmpty()){
                        scop.launch {
                            viewModel.sendCode(email)
                            viewModel.resultSendCode.collectLatest {
                                checkSend.value = it
                            }
                            viewModel.messageSendCode.collectLatest {
                                Log.e("pasi", it)
                            }
                        }
                    }

                }) {
                Text(text = "login")
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
    Box(modifier = Modifier.fillMaxSize()) {
        val scop = rememberCoroutineScope()
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            TextField(
                value = code,
                onValueChange = { code = it },
                isError = isError
            )
        }
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    isError=code.isEmpty()
                    if (code.isNotEmpty()){
                        scop.launch {
                            viewModel.verifyUser(email.value, code)
                            viewModel.resultVerify.collectLatest {
                                checkLogin = it
                            }
                            viewModel.resultVerify.collectLatest { isError = !it }
                            viewModel.messageVerifyCode.collectLatest {
                                Log.e("pasi", it)
                            }
                        }
                    }

                }) {
                Text(text = "ta iid")
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


