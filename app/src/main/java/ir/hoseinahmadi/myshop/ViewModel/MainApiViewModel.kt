package ir.hoseinahmadi.myshop.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.hoseinahmadi.myshop.Remote.Data.ProductItem
import ir.hoseinahmadi.myshop.Repository.MailApiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainApiViewModel @Inject constructor(
    private val repository: MailApiRepository
) : ViewModel() {

    val AllCategoryName = MutableStateFlow(emptyList<String>())
    val AllProduct = MutableStateFlow<List<ProductItem>>(emptyList())
    val getElectronicsCategory = MutableStateFlow<List<ProductItem>>(emptyList())
    val getJeweleryCategory = MutableStateFlow<List<ProductItem>>(emptyList())
    val getMensCategory = MutableStateFlow<List<ProductItem>>(emptyList())
    val getWomanCategory = MutableStateFlow<List<ProductItem>>(emptyList())

//    val productById = MutableStateFlow<ProductItem>(ProductItem())

    fun getNameCategory() {
        viewModelScope.launch(Dispatchers.IO) {
            val responce = try {
                repository.getCategoryItem()
            } catch (e: Exception) {
                Log.e("pasi", e.message.toString())
                return@launch
            }
            withContext(Dispatchers.Main) {
                if (responce.isSuccessful) {
                    responce.body()?.let {
                        AllCategoryName.value = it
                    }
                }
            }


        }

    }

    fun getAllProduct() {
        viewModelScope.launch(Dispatchers.IO) {
            val responce = try {
                repository.getAllProduct()
            } catch (e: Exception) {
                Log.e("pasi", "error")
                return@launch
            }
            withContext(Dispatchers.Main) {
                if (responce.isSuccessful && responce.body() != null) {
                    Log.e("pasi", " res  isSuccessful")
                    AllProduct.emit(responce.body()!!)
                } else {
                    Log.e("pasi", "not succes")
                }
            }

        }

    }

    suspend fun getElectronicsCategory() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = try {
                repository.getElectronicsCategory()
            } catch (e: Exception) {
                return@launch
            }
            withContext(Dispatchers.Main){
                if (response.isSuccessful){
                    getElectronicsCategory.emit(response.body()!!)
                }
            }

        }

    }

    suspend fun getJeweleryCategory() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = try {
                repository.getJeweleryCategory()
            } catch (e: Exception) {
                return@launch
            }
            withContext(Dispatchers.Main){
                if (response.isSuccessful){
                    getJeweleryCategory.emit(response.body()!!)
                }
            }

        }

    }

    suspend fun getMensCategory() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = try {
                repository.getMensCategory()
            } catch (e: Exception) {
                return@launch
            }
            withContext(Dispatchers.Main){
                if (response.isSuccessful){
                    getMensCategory.emit(response.body()!!)
                }
            }

        }

    }

    suspend fun getWomanCategory() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = try {
                repository.getWomanCategory()
            } catch (e: Exception) {
                return@launch
            }
            withContext(Dispatchers.Main){
                if (response.isSuccessful){
                    getWomanCategory.emit(response.body()!!)
                }
            }

        }

    }

}