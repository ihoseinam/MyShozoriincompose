package ir.hoseinahmadi.myshop.Di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ir.hoseinahmadi.myshop.Db.CartDao
import ir.hoseinahmadi.myshop.Db.MyShopDataBase
import ir.hoseinahmadi.myshop.component.Constants.DATABASE_NAME
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {


    @Provides
    @Singleton
    fun provideDataBase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        MyShopDataBase::class.java,
        DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideCartDao(dataBase: MyShopDataBase):CartDao=dataBase.CartDao()

}