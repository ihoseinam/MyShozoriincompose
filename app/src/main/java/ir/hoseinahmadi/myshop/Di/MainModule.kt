package ir.hoseinahmadi.myshop.Di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.hoseinahmadi.myshop.Remote.Main.ApiInterFace
import ir.hoseinahmadi.myshop.component.Constants.BUSE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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