package ir.hoseinahmadi.myshop.Screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import ir.hoseinahmadi.myshop.Db.CartItem
import ir.hoseinahmadi.myshop.Db.CartViewModel
import ir.hoseinahmadi.myshop.Remote.Data.ProductItem
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopingScreen(
    navHostController: NavHostController,
    cartViewModel: CartViewModel = hiltViewModel()
) {
    var item by remember {
        mutableStateOf(emptyList<CartItem>())
    }
    var totalPrice by remember {
        mutableDoubleStateOf(0.0)
    }


    LaunchedEffect(true) {
        launch {
            cartViewModel.cartItem.collectLatest {
                item = it
            }
        }
        launch {
            cartViewModel.totalPrice.collectLatest {
                totalPrice = it
            }
        }
    }
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 9.dp, vertical = 20.dp)
            ) {
                Text(text = "Shoping Cart")
            }
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val p = totalPrice.roundToInt().toString()
                Text(
                    text = "Total shopping cart :\n $p $",
                    fontSize = 18.sp
                )

                Button(
                    onClick = { /*TODO*/ },
                    shape = RoundedCornerShape(9.dp)
                ) {
                    Text(text = "shopppp")
                }

            }
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            items(item) {
                CartItemCard(item = it)
            }
        }
    }

}