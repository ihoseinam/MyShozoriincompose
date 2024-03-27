package ir.hoseinahmadi.myshop.Db.ShopingCart

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CartRepository @Inject constructor(
    private val dao: CartDao
) {

    val cartItem = dao.getAllItemCart()
    val totalPrice: Flow<Double> = dao.getTotalPrice()

    val getProductCount: Flow<Int> = dao.getProductCount()

     fun isShopItemExist(itemId:String):Flow<Boolean> =dao.isShopItemExist(itemId)

    suspend fun insertCartItem(cartItem: CartItem) {
        dao.InsertCartItem(cartItem)
    }

     fun getProductById(itemId: String):Flow<CartItem> = dao.getProductBYId(itemId)
     fun removeFromCart(cartItem: CartItem) {
        dao.removeFromCart(cartItem)
    }

    suspend fun changeCartItemCount(id: String, newCount: Int) {
        dao.changeCountCartItem(id, newCount)
    }


}