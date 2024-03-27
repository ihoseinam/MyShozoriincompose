package ir.hoseinahmadi.myshop.Screen

import android.widget.RatingBar
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import ir.hoseinahmadi.myshop.Db.Fave.FaveItem
import ir.hoseinahmadi.myshop.Db.Fave.FaveViewModel
import ir.hoseinahmadi.myshop.Db.ShopingCart.CartItem
import ir.hoseinahmadi.myshop.Db.ShopingCart.CartViewModel
import ir.hoseinahmadi.myshop.Navigation.Screen
import ir.hoseinahmadi.myshop.Remote.Data.ProductItem
import ir.hoseinahmadi.myshop.Remote.Data.Rating
import ir.hoseinahmadi.myshop.ViewModel.MainApiViewModel
import ir.hoseinahmadi.myshop.ui.theme.h1
import ir.hoseinahmadi.myshop.ui.theme.h2
import ir.hoseinahmadi.myshop.ui.theme.h3
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun InfoItem(
    navHostController: NavHostController, id: String?,
    viewModel: MainApiViewModel = hiltViewModel(),
    faveViewModel: FaveViewModel = hiltViewModel(),
    cartViewModel: CartViewModel = hiltViewModel()
) {
    val emty = ProductItem(
        "", "", 12, "", 0.0, Rating(0, 0.0),
        ""
    )
    var item by remember {
        mutableStateOf<ProductItem>(emty)
    }

    LaunchedEffect(true) {
        launch {
            viewModel.getProductById(id!!)
            viewModel.productById.collectLatest {
                item = it
            }
        }
    }

    Scaffold(
        bottomBar = {
            BottomBarInfo(
                id!!,
                item = CartItem(
                    item.id.toString(),
                    item.title,
                    item.price,
                    item.image,
                    1,
                    item.category,
                    item.rating.rate
                ),

                )
        },
        topBar = {
            TopBarInfo(
                FaveItem(item.id.toString(), item.title, item.price, item.image),
                navHostController,
                faveViewModel
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                contentColor = Color.Black,
                onClick = {
                    navHostController.navigate(Screen.ShopingCard.route) {
                        popUpTo(0) {
                            inclusive = true
                        }
                    }
                }) {
                Icon(
                    Icons.Rounded.ShoppingCart,
                    contentDescription = ""
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GlideImage(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(305.dp)
                    .padding(horizontal = 5.dp),
                model = item.image,
                contentDescription = ""
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp, bottom = 50.dp, top = 8.dp)
            ) {
                Text(
                    text = item.title,
                    style = h2,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "Category : ${item.category}",
                    style = h2,
                    color = Color(0xFF008EFF)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = item.description,
                    style = h3,
                    color = Color.DarkGray,
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.height(15.dp))

            }

        }
    }
}

@Composable
fun TopBarInfo(
    item: FaveItem,
    navHostController: NavHostController,
    faveViewModel: FaveViewModel,
) {
    val checkState by faveViewModel.checkFaveItem(item.id).collectAsState(initial = false)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 4.dp)
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            IconButton(onClick = { navHostController.popBackStack() }) {
                Icon(
                    Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "",
                    tint = Color.Black
                )
            }
            Text(
                text = "Info product",
                fontSize = 20.sp,
                color = Color.Black,
                style = h2,
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            IconToggleButton(checked = checkState,
                onCheckedChange = {
                    if (it) {
                        faveViewModel.addNewFaveItem(item)
                    } else {
                        faveViewModel.deleteFaveItem(item)
                    }
                }) {
                if (checkState) {
                    Icon(
                        Icons.Rounded.Favorite,
                        contentDescription = "",
                        Modifier.size(30.dp),
                        tint = Color.Red
                    )
                } else {
                    Icon(
                        Icons.Rounded.FavoriteBorder, contentDescription = "",
                        tint = Color.DarkGray,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        }

    }
}


@Composable
fun BottomBarInfo(
    id: String,
    item: CartItem,
    cartViewModel: CartViewModel = hiltViewModel(),
) {

    var check by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(true) {
        launch {
            cartViewModel.checkProduct(id).collectLatest {
                check = it
            }
        }
    }

    BottomAppBar(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(5.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = 9.dp,
                    vertical = 8.dp
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(modifier = Modifier.fillMaxWidth(0.6f)) {
                AnimatedVisibility(check) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { cartViewModel.removeCartItem(item) }) {
                            Icon(
                                Icons.Filled.Delete,
                                contentDescription = "", tint = Color.Red,
                                modifier = Modifier.size(35.dp),
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Available in cart",
                            style = h1,
                            color = Color.Red
                        )

                    }

                }
                AnimatedVisibility(!check) {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Black,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(10.dp),
                        onClick = {
                            cartViewModel.insertCartItem(item)
                        }) {
                        Text(
                            text = "Add To Cart",
                            style = h2,
                        )
                    }
                }
            }

            Text(
                text = "${item.price} $", style = h1, color = Color.Black,
                fontSize = 22.sp
            )


        }

    }

}
