package ir.hoseinahmadi.myshop.Db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface CartDao {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun InsertCartItem(cartItem: CartItem)

    @Query("SELECT * FROM shoping_cart")
     fun getAllItemCart():Flow<List<CartItem>>

     @Delete
     fun removeFromCart(item: CartItem)

     @Query("UPDATE shoping_cart SET count=:newCount WHERE itemId=:id")
     suspend fun changeCountCartItem(id:String,newCount:Int)

    @Query("SELECT SUM(price * count) FROM shoping_cart")
    fun getTotalPrice(): Flow<Double>

    @Query("SELECT COUNT(*) FROM SHOPING_CART")
    fun getProductCount(): Flow<Int>

    @Query("SELECT EXISTS(SELECT * FROM SHOPING_CART WHERE itemId =:itemId)")
    fun isShopItemExist(itemId: String): Flow<Boolean>


}