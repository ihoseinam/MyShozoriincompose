package ir.hoseinahmadi.myshop.Screen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import ir.hoseinahmadi.myshop.Db.ShopingCart.CartItem
import ir.hoseinahmadi.myshop.Db.ShopingCart.CartViewModel
import kotlin.math.roundToInt

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CartItemCard(
    item: CartItem,
    cartViewModel: CartViewModel = hiltViewModel()
) {
    var count by remember {
        mutableIntStateOf(item.count)
    }
    Card(
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Column(
                modifier = Modifier
                    .weight(0.5f)
                    .padding(vertical = 10.dp, horizontal = 5.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                GlideImage(
                    modifier = Modifier
                        .align(Alignment.Start)
                        .fillMaxWidth(.9f)
                        .height(170.dp),
                    model = item.img,
                    contentDescription = "",
                )
                Spacer(modifier = Modifier.height(7.dp))

            }
            Column(
                modifier = Modifier
                    .weight(0.5f)
                    .fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(15.dp))
                Text(text = item.title,)
                Spacer(modifier = Modifier.height(6.dp))
                Text(text = " Category: ${item.category}",
                    color = Color.DarkGray
                    )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = " Price: ${(item.price * count).roundToInt()} $",
                    fontSize = 16.sp,
                    color = Color.Red
                )
                Spacer(modifier = Modifier.height(40.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .padding(9.dp)
                ) {
                    Surface(
                        color = Color.White,
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .border(
                                1.5.dp, Color.LightGray.copy(0.9f),
                                RoundedCornerShape(6.dp)
                            )
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceAround,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            if (count == 1) {
                                IconButton(
                                    modifier = Modifier.size(25.dp),
                                    colors = IconButtonDefaults.iconButtonColors(contentColor = Color.Red),
                                    onClick = {
                                        cartViewModel.removeCartItem(item)
                                    }) {
                                    Icon(
                                        Icons.Rounded.Delete,
                                        contentDescription = "",
                                    )
                                }

                            } else {
                                IconButton(
                                    modifier = Modifier.size(25.dp),
                                    colors = IconButtonDefaults.iconButtonColors(contentColor = Color.Red),
                                    onClick = {
                                        count--
                                        cartViewModel.changeCartItemCount(item.itemId, count)
                                    }) {
                                    Text(text = "---",
                                        fontSize = 19.sp,
                                        fontWeight = FontWeight.Bold
                                        )
                                }
                            }
                            Text(
                                text = count.toString(),
                                modifier = Modifier.padding(horizontal = 2.dp),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Red
                            )
                            IconButton(
                                modifier = Modifier.size(25.dp),
                                colors = IconButtonDefaults.iconButtonColors(contentColor = Color.Red),
                                onClick = {
                                    count++
                                    cartViewModel.changeCartItemCount(item.itemId, count)
                                }) {
                                Icon(
                                    Icons.Rounded.Add,
                                    contentDescription = "",

                                    )
                            }

                        }

                    }

                }
            }

        }

    }

}


