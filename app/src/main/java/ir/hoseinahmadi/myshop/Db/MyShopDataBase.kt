package ir.hoseinahmadi.myshop.Db

import androidx.room.Database
import androidx.room.RoomDatabase
import ir.hoseinahmadi.myshop.component.Constants
import ir.hoseinahmadi.myshop.component.Constants.SHOPING_CART_TABLE


@Database(entities = [CartItem::class], version = 1, exportSchema = false)
abstract class MyShopDataBase :RoomDatabase(){

    abstract fun CartDao():CartDao

}