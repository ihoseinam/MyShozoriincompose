package ir.hoseinahmadi.myshop.Repository

import android.util.Log
import ir.hoseinahmadi.myshop.Remote.ApiInterFace
import ir.hoseinahmadi.myshop.Remote.Data.ProductItem
import retrofit2.Response
import retrofit2.http.GET
import javax.inject.Inject

class MailApiRepository @Inject constructor(
    private val api: ApiInterFace
) {

    suspend fun getCategoryItem() = api.getCategory()

    suspend fun getAllProduct() = api.getAllProduct()

    suspend fun getProductById(id: String) = api.getProductById(id)


    suspend fun getElectronicsCategory()=api.getElectronicsCategory()

    suspend fun getJeweleryCategory() =api.getJeweleryCategory()

    suspend fun getMensCategory()=api.getMensCategory()

    suspend fun getWomanCategory()=api.getWomanCategory()

}