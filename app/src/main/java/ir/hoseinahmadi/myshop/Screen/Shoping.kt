package ir.hoseinahmadi.myshop.Screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import ir.hoseinahmadi.myshop.Db.ShopingCart.CartItem
import ir.hoseinahmadi.myshop.Db.ShopingCart.CartViewModel
import ir.hoseinahmadi.myshop.ui.theme.h1
import ir.hoseinahmadi.myshop.ui.theme.h2
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
                Text(text = "Shopping Cart", style = h1, color = Color.Black)
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
                val p = totalPrice.roundToInt()
                val pp =String.format("%,d", p)
                Text(
                    text = "Total shopping cart :\n $pp $",
                    style = h2,
                    color = Color.Black
                )
                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black, contentColor = Color.White),
                    onClick = { /*TODO*/ },
                    shape = RoundedCornerShape(9.dp)
                ) {
                    Text(text = " Cart payment ",
                        style = h2,
                        fontWeight = FontWeight.Bold
                        )
                }

            }
        }
    ) {
        LazyColumn(
            state = rememberLazyListState(),
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            if (item.isEmpty()){
                item {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                        Text(text = "Shopping basket is empty !",
                            style = h1,
                            color = Color.Black
                        )
                    }

                }
            }
            items(item, key = {
                it.itemId
            }) { currentItem ->
                val swipeToDismiss = rememberSwipeToDismissBoxState()
                LaunchedEffect(swipeToDismiss.currentValue) {
                    if (swipeToDismiss.currentValue == SwipeToDismissBoxValue.EndToStart) {
                        cartViewModel.removeCartItem(currentItem)
                    }
                }
                SwipeToDismissBox(
                    enableDismissFromStartToEnd = false,
                    state = swipeToDismiss,
                    backgroundContent = {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(8.dp))
                                .padding(8.dp)
                                .background(Color.Red),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            Row(
                                Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.End
                            ) {
                                Text(
                                    text = "Delete Item",
                                    style = h1, fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Spacer(modifier = Modifier.width(15.dp))

                                Icon(
                                    Icons.Rounded.Delete, contentDescription = "",
                                    tint = Color.White, modifier = Modifier.size(50.dp)
                                )
                            }

                        }
                    }
                ) {
                    CartItemCard(item = currentItem)
                }
            }
        }
    }


}
