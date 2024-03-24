package ir.hoseinahmadi.myshop.Remote

import ir.hoseinahmadi.myshop.Remote.Data.CategoryData
import ir.hoseinahmadi.myshop.Remote.Data.ProductItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterFace {

    @GET("products/categories")
    suspend fun getCategory(): Response<CategoryData>

    @GET("products")
    suspend fun getAllProduct(): Response<List<ProductItem>>

    @GET("products/category/electronics")
    suspend fun getElectronicsCategory(): Response<List<ProductItem>>

    @GET("products/category/jewelery")
    suspend fun getJeweleryCategory(): Response<List<ProductItem>>

    @GET("products/category/men's clothing")
    suspend fun getMensCategory(): Response<List<ProductItem>>

    @GET("products/category/women's clothing")
    suspend fun getWomanCategory(): Response<List<ProductItem>>


    @GET("products/{id}")
    suspend fun getProductById(
        @Path(
            value = "id",
            encoded = true
        ) id: String
    ): Response<ProductItem>

}