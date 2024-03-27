package ir.hoseinahmadi.myshop.Db

import androidx.room.Database
import androidx.room.RoomDatabase
import ir.hoseinahmadi.myshop.Db.Fave.FaveDao
import ir.hoseinahmadi.myshop.Db.Fave.FaveItem
import ir.hoseinahmadi.myshop.Db.ShopingCart.CartDao
import ir.hoseinahmadi.myshop.Db.ShopingCart.CartItem


@Database(entities = [CartItem::class,FaveItem::class], version = 1, exportSchema = false)
abstract class MyShopDataBase :RoomDatabase(){

    abstract fun CartDao(): CartDao
    abstract fun FaveDao():FaveDao

}