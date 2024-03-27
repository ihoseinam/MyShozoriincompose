package ir.hoseinahmadi.myshop.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import ir.hoseinahmadi.myshop.Db.Fave.FaveItem
import ir.hoseinahmadi.myshop.Db.Fave.FaveViewModel
import ir.hoseinahmadi.myshop.ui.theme.h1
import ir.hoseinahmadi.myshop.ui.theme.h2
import ir.hoseinahmadi.myshop.ui.theme.h3
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FaveScreen(
    navHostController: NavHostController,
    faveViewModel: FaveViewModel = hiltViewModel()
) {

    var item by remember {
        mutableStateOf(emptyList<FaveItem>())
    }

    LaunchedEffect(true) {
        launch {
            faveViewModel.allFaveItem.collectLatest {
                item = it
            }
        }

    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Favorite products", style = h1) })
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            if (item.isEmpty()){
                item {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                        Text(text = "Favorites list is empty !",
                            style = h1,
                            color = Color.Black
                        )
                    }

                }
            }

            items(item, key = {
                it.id
            }) { currentItem ->
                val swipeToDismiss = rememberSwipeToDismissBoxState()
                LaunchedEffect(swipeToDismiss.currentValue) {
                    if (swipeToDismiss.currentValue == SwipeToDismissBoxValue.EndToStart) {
                        faveViewModel.deleteFaveItem(currentItem)
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
                                Text(text = "Delete Item",
                                    style = h1,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White)
                                Spacer(modifier = Modifier.width(15.dp))
                                Icon(
                                    Icons.Rounded.Delete, contentDescription ="",
                                    tint = Color.White, modifier = Modifier.size(50.dp)
                                )
                            }

                        }
                    }
                ) {
                    FaveItem(currentItem)
                }
            }
        }
    }

}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun FaveItem(data: FaveItem) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(9.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 10.dp)
        ) {
            GlideImage(
                modifier = Modifier
                    .weight(0.45f)
                    .fillMaxWidth(0.9f)
                    .height(160.dp)
                    .padding(horizontal = 9.dp),
                model = data.img,
                contentDescription = ""
            )
            Column(
                modifier = Modifier
                    .weight(0.55f)
            ) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = data.title,
                    style = h2,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    )
                Spacer(modifier = Modifier.height(5.dp))
                Text(text ="${data.price} $",
                    fontSize = 17.sp,
                    style = h3
                    )
            }


        }

    }
}