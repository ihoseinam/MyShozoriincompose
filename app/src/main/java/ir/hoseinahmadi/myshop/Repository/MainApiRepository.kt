package ir.hoseinahmadi.myshop.Repository

import ir.hoseinahmadi.myshop.Remote.Data.ProductItem
import ir.hoseinahmadi.myshop.Remote.Data.Rating
import ir.hoseinahmadi.myshop.Remote.Main.ApiInterFace
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainApiRepository @Inject constructor(
    private val api: ApiInterFace
) {


    val getElectronicsCategory = MutableStateFlow<List<ProductItem>>(emptyList())
    val getJeweleryCategory = MutableStateFlow<List<ProductItem>>(emptyList())
    val getMensCategory = MutableStateFlow<List<ProductItem>>(emptyList())
    val getWomanCategory = MutableStateFlow<List<ProductItem>>(emptyList())
    val loading = MutableStateFlow(true)
    val allItem =MutableStateFlow<List<ProductItem>>(emptyList())

    val emty = ProductItem(
        "", "", 0, "", 0.0, Rating(0, 0.0),
        ""
    )
    val productById = MutableStateFlow<ProductItem>(emty)

    suspend fun getAllProduct() {
        loading.emit(true)
        val responce =try {
            api.getAllProduct()
        }catch (e:Exception){
            return
        }
        withContext(Dispatchers.Main){
            if (responce.isSuccessful){
                responce.body()?.let {
                    allItem.emit(it)
                }
                loading.emit(false)
            }
        }
    }

    suspend fun getProductById(id: String) {
        loading.emit(true)
        val responce =try {
            api.getProductById(id)
        }catch (e:Exception){
            return
        }
        withContext(Dispatchers.Main){
            if (responce.isSuccessful){
                productById.emit(responce.body()!!)
                loading.emit(false)
            }
        }

    }


    suspend fun getElectronicsCategory() {
        loading.emit(true)
        val response = try {
            api.getElectronicsCategory()
        } catch (e: Exception) {
            return
        }
        withContext(Dispatchers.Main) {
            if (response.isSuccessful) {
                getElectronicsCategory.emit(response.body()!!)
                loading.emit(false)

            }
        }
    }

    suspend fun getJeweleryCategory() {
        loading.emit(true)
        val response = try {
            api.getJeweleryCategory()
        } catch (e: Exception) {
            return
        }
        withContext(Dispatchers.Main) {
            if (response.isSuccessful) {
                getJeweleryCategory.emit(response.body()!!)
                loading.emit(false)

            }
        }
    }

    suspend fun getMensCategory() {
        loading.emit(true)
        val response = try {
            api.getMensCategory()
        } catch (e: Exception) {
            return
        }
        withContext(Dispatchers.Main) {
            if (response.isSuccessful) {
                getMensCategory.emit(response.body()!!)
                loading.emit(false)
            }
        }
    }

    suspend fun getWomanCategory() {
        loading.emit(true)
        val response = try {
            api.getWomanCategory()
        } catch (e: Exception) {
            return
        }
        withContext(Dispatchers.Main) {
            if (response.isSuccessful) {
                getWomanCategory.emit(response.body()!!)
                loading.emit(false)
            }
        }
    }




}