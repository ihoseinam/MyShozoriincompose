package ir.hoseinahmadi.myshop.Di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.hoseinahmadi.myshop.Remote.ApiInterFace
import ir.hoseinahmadi.myshop.Remote.Constants
import ir.hoseinahmadi.myshop.Remote.Constants.BUSE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {


    @Provides
    @Singleton
    fun provideMainRetrofitService(): Retrofit = Retrofit.Builder()
        .baseUrl(BUSE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideMainApiInterFace(retrofit: Retrofit): ApiInterFace =
        retrofit.create(ApiInterFace::class.java)


}