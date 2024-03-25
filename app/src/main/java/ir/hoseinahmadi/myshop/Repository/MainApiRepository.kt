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
    val emty = ProductItem(
        "", "", 0, "", 0.0, Rating(0, 0.0),
        ""
    )
    val productById = MutableStateFlow<ProductItem>(emty)

    suspend fun getCategoryItem() = api.getCategory()

    suspend fun getAllProduct() = api.getAllProduct()

    suspend fun getProductById(id: String) {
        val responce =try {
            api.getProductById(id)
        }catch (e:Exception){
            return
        }
        if (responce.isSuccessful){
            productById.emit(responce.body()!!)
        }
    }


    suspend fun getElectronicsCategory() {
        val response = try {
            api.getElectronicsCategory()
        } catch (e: Exception) {
            return
        }
        withContext(Dispatchers.Main) {
            if (response.isSuccessful) {
                getElectronicsCategory.emit(response.body()!!)
            }
        }
    }

    suspend fun getJeweleryCategory() {
        val response = try {
            api.getJeweleryCategory()
        } catch (e: Exception) {
            return
        }
        withContext(Dispatchers.Main) {
            if (response.isSuccessful) {
                getJeweleryCategory.emit(response.body()!!)
            }
        }
    }

    suspend fun getMensCategory() {
        val response = try {
            api.getMensCategory()
        } catch (e: Exception) {
            return
        }
        withContext(Dispatchers.Main) {
            if (response.isSuccessful) {
                getMensCategory.emit(response.body()!!)
            }
        }
    }

    suspend fun getWomanCategory() {
        val response = try {
            api.getWomanCategory()
        } catch (e: Exception) {
            return
        }
        withContext(Dispatchers.Main) {
            if (response.isSuccessful) {
                getWomanCategory.emit(response.body()!!)
            }
        }
    }




}