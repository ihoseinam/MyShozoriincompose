package ir.hoseinahmadi.myshop.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.hoseinahmadi.datastore.datastore.DataStoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DataStoreViewModel @Inject constructor(
    private val repository: DataStoreRepository
) : ViewModel() {
    companion object {
        const val nameKey = "user_name"
        const val emailKey = "emailKey"
        const val checkLogin = "check_user_login"
        const val UserPas = "UserPas"
    }


    val getName = MutableStateFlow("")
    val getEmail =MutableStateFlow("")
    val getLoginInfo =MutableStateFlow(false)
    val getPassword =MutableStateFlow("")


    fun saveName(name: String) {
        viewModelScope.launch() {
            repository.putString(nameKey, name)
        }
    }

    fun savePassword(password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.putString(UserPas, password)
        }
    }

    fun saveEmail(email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.putString(emailKey, email)
        }
    }

    fun saveCheckLogin(check: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.putBoolean(checkLogin, check)
        }
    }

     fun getName(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getString(nameKey)?.let {
                withContext(Dispatchers.Main){
                    getName.emit(it)
                }
            }
        }
    }

    suspend fun getEmail(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getString(emailKey)?.let {
                withContext(Dispatchers.Main){
                    getEmail.emit(it)
                }
            }
        }
    }
    suspend fun getLoginInfo(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getBoolean(checkLogin)?.let {
                withContext(Dispatchers.Main){
                    getLoginInfo.emit(it)
                }
            }
        }
    }
    suspend fun getPassword(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getString(UserPas)?.let {
                withContext(Dispatchers.Main){
                    getPassword.emit(it)
                }
            }
        }
    }





}


