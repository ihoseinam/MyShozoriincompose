package ir.hoseinahmadi.myshop.Di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.hoseinahmadi.myshop.Remote.Constants.BUSE_URL_EMAIL
import ir.hoseinahmadi.myshop.Remote.Email.VerifyEmailInterFace
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object VerifyModule {

    @Singleton
    @Provides
    @Named("RetrofitVerify")
    fun provideRetrofitVerify():Retrofit =Retrofit.Builder()
        .baseUrl(BUSE_URL_EMAIL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    @Named("VerifyInterFace")
    fun provideVerifyInterFace(
        @Named("RetrofitVerify") retrofit: Retrofit) :VerifyEmailInterFace=
        retrofit.create(VerifyEmailInterFace::class.java)

}