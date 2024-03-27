package ir.hoseinahmadi.myshop.Navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import ir.hoseinahmadi.myshop.Db.Fave.FaveViewModel
import ir.hoseinahmadi.myshop.Db.ShopingCart.CartViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigation(
    navHostController: NavHostController,
    onItemClick: (NavigationItem) -> Unit,
    cartViewModel: CartViewModel = hiltViewModel(),
    faveViewModel: FaveViewModel= hiltViewModel()
) {
    val item = listOf(
        NavigationItem(
            name = "Home",
            route = Screen.Home.route,
            selectedIcon = Icons.Rounded.Home,
            unselectedIcon = Icons.Outlined.Home,
        ),
        NavigationItem(
            name = "Favorite",
            route = Screen.Fav.route,
            selectedIcon = Icons.Rounded.Favorite,
            unselectedIcon = Icons.Rounded.FavoriteBorder,
        ),
        NavigationItem(
            name = "Shopping",
            route = Screen.ShopingCard.route,
            selectedIcon = Icons.Rounded.ShoppingCart,
            unselectedIcon = Icons.Outlined.ShoppingCart,
        ),
           NavigationItem(
            name = "Profile",
            route = Screen.Profile.route,
            selectedIcon = Icons.Rounded.Person,
            unselectedIcon = Icons.Outlined.Person,
        ),


    )

    val backStackEntry = navHostController.currentBackStackEntryAsState()
    val showBottomBar = backStackEntry.value?.destination?.route in item.map { it.route }

    val cartCount by cartViewModel.productCountFlow.collectAsState(initial = 0)
    val faveCount by faveViewModel.faveCount.collectAsState(initial = 0)
    if (showBottomBar) {
        NavigationBar(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .clip(RoundedCornerShape(15.dp)),
            containerColor = Color(0xFFFFFFFF)
        ) {
            item.forEachIndexed { index, item ->
                val selected = item.route == backStackEntry.value?.destination?.route
                NavigationBarItem(
                    selected = selected,
                    onClick = { onItemClick(item) },
                    icon = {
                        BadgedBox(
                            badge = {
                                if (index == 2 && cartCount > 0) {
                                    Badge(
                                        contentColor = Color.White,
                                        containerColor = Color.Red,
                                    ) {
                                        Text(
                                            text = cartCount.toString(),
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                                if (index==1 && faveCount > 0){
                                    Badge(
                                        contentColor = Color.White,
                                        containerColor = Color.Red,
                                    ) {
                                        Text(
                                            text = faveCount.toString(),
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            },
                        ) {
                            if (selected) {
                                Icon(item.selectedIcon, contentDescription = "")
                            } else {
                                Icon(item.unselectedIcon, contentDescription = "")
                            }
                        }

                    },
                    label = {
                        Text(text = item.name)
                    },
                    colors = NavigationBarItemDefaults.colors(
                        unselectedIconColor = Color.DarkGray,
                        unselectedTextColor = Color.DarkGray,
                        selectedIconColor = Color(0xFFFFFFFF),
                        selectedTextColor = Color.Black,
                        indicatorColor = Color.Black
                    ),
                )
            }

        }
    }


}