package ir.hoseinahmadi.myshop.Db

import androidx.room.Entity
import androidx.room.PrimaryKey
import ir.hoseinahmadi.myshop.Remote.Data.Rating
import ir.hoseinahmadi.myshop.component.Constants.SHOPING_CART_TABLE


@Entity(tableName = SHOPING_CART_TABLE)
data class CartItem(
    @PrimaryKey()
    val itemId: String,
    val title: String,
    val price:Double,
    val img:String,
    val count:Int,
    val category: String,
    val rating:Double
)
