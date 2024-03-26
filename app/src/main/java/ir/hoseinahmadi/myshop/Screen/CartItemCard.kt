package ir.hoseinahmadi.myshop.Screen

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import ir.hoseinahmadi.myshop.Db.CartItem
import ir.hoseinahmadi.myshop.Db.CartViewModel
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
                    .padding(vertical = 10.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                GlideImage(
                    modifier = Modifier
                        .align(Alignment.Start)
                        .fillMaxWidth()
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
                Text(text = item.title)
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = " Category: ${item.category}")
                Spacer(modifier = Modifier.height(5.dp))
                Text(text =" Price: ${(item.price * count).roundToInt()} $",
                    fontSize = 16.sp,
                    color = Color.Red
                )
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                ) {
                    Surface(
                        color = Color.White,
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .border(1.5.dp, Color.LightGray.copy(0.9f), RoundedCornerShape(6.dp))
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceAround,
                            modifier = Modifier.fillMaxWidth().padding(
                                horizontal = 15.dp,
                                vertical = 6.dp
                            ),
                        ) {
                            if (count == 1) {
                                Icon(
                                    Icons.Rounded.Delete,
                                    contentDescription = "",
                                    Modifier.clickable {
                                        cartViewModel.removeCartItem(item)
                                    }
                                )
                            } else {
                                Icon(
                                    Icons.Rounded.Clear,
                                    contentDescription = "",
                                    Modifier.clickable {
                                        count--
                                        cartViewModel.changeCartItemCount(item.itemId, count)
                                    }
                                )
                            }
                            Text(
                                text = count.toString(),
                                modifier = Modifier.padding(horizontal = 5.dp)
                            )

                            Icon(
                                Icons.Rounded.Add,
                                contentDescription = "",
                                Modifier.clickable {
                                    count++
                                    cartViewModel.changeCartItemCount(item.itemId, count)
                                }
                            )
                        }

                    }

                }
            }

        }

    }

}

