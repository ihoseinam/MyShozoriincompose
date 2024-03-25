package ir.hoseinahmadi.myshop.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.hoseinahmadi.myshop.Remote.Data.ProductItem
import ir.hoseinahmadi.myshop.Repository.MainApiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainApiViewModel @Inject constructor(
    private val repository: MainApiRepository
) : ViewModel() {


    val getElectronicsCategory = repository.getElectronicsCategory
    val getJeweleryCategory = repository.getJeweleryCategory
    val getMensCategory = repository.getMensCategory
    val getWomanCategory = repository.getWomanCategory

    val productById = repository.productById

    suspend fun getProductById(id: String) {
    viewModelScope.launch(Dispatchers.IO) {
        repository.getProductById(id)
    }
    }

    suspend fun getElectronicsCategory() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getElectronicsCategory()
        }

    }

    suspend fun getJeweleryCategory() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getJeweleryCategory()

        }

    }

    suspend fun getMensCategory() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getMensCategory()
        }

    }

    suspend fun getWomanCategory() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getWomanCategory()

        }

    }

}