package ir.hoseinahmadi.myshop.ViewModel

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.hoseinahmadi.myshop.Repository.VerifyUserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class VerifyApiUserViewModel @Inject constructor(
    private val repository: VerifyUserRepository
) : ViewModel() {

    val messageSendCode = repository.messageSendCode
    val loadingSendCode = repository.loadingSendCode
    val resultSendCode = repository.resultSendCode
    val isErrorSenCode =repository.isError

    val messageVerifyCode = repository.messageVerifyCode
    val loadingVerifyCode = repository.loadingVerifyCode
    val resultVerify = repository.resultVerify


    fun sendCode(email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.senCodeeEmail(email)
        }

    }


    fun verifyUser(email: String, code: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.verifyCode(email, code)
        }
    }

}