package ir.hoseinahmadi.myshop.Db.ShopingCart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val repository: CartRepository
) : ViewModel() {

    val cartItem: Flow<List<CartItem>> = repository.cartItem
    val totalPrice: Flow<Double> = repository.totalPrice

    val productCountFlow: Flow<Int> = repository.getProductCount

     fun checkProduct(itemId:String): Flow<Boolean> = repository.isShopItemExist(itemId)



    fun insertCartItem(item: CartItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertCartItem(item)
        }
    }

    fun removeCartItem(item: CartItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.removeFromCart(item)
        }
    }

    fun changeCartItemCount(id: String, newCount: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.changeCartItemCount(id, newCount)
        }

    }

    fun getProductById(itemId: String) :Flow<CartItem> = repository.getProductById(itemId)


}