package ir.hoseinahmadi.myshop.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.hoseinahmadi.datastore.datastore.DataStoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DataStoreViewModel @Inject constructor(
    private val repository: DataStoreRepository
) : ViewModel() {
    companion object {
        const val UserPhoneKey = "UserPhoneKey"
        const val nameKey = "user_name"
        const val emailKey = "emailKey"
        const val checkLogin = "check_user_login"
        const val UserPas = "UserPas"
    }

    fun saveName(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
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

    suspend fun getName(): String = repository.getString(nameKey) ?: ""
    suspend fun getEmail(): String = repository.getString(emailKey) ?: ""
    suspend fun getLoginInfo(): Boolean = repository.getBoolean(checkLogin) ?: false
    suspend fun getPassword():String =repository.getString(UserPas)?:""


    val userPhone = MutableStateFlow("")
    fun getUserPhone() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getString(UserPhoneKey)?.let {
                userPhone.emit(it)
            }
        }
    }

    suspend fun getUserPhone2(): String? = repository.getString(UserPhoneKey)


}


