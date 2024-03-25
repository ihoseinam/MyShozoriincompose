package ir.hoseinahmadi.myshop.Screen

import android.util.Log
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import ir.hoseinahmadi.myshop.Navigation.Screen
import ir.hoseinahmadi.myshop.Remote.Data.ProductItem
import ir.hoseinahmadi.myshop.ViewModel.DataStoreViewModel
import ir.hoseinahmadi.myshop.ViewModel.MainApiViewModel
import ir.hoseinahmadi.myshop.component.Loading3Dots
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navHostController: NavHostController,
    viewModel: MainApiViewModel = hiltViewModel(),
) {
    var loadin by remember {
        mutableStateOf(true)
    }
    val scop = rememberCoroutineScope()
    LaunchedEffect(loadin) {
        launch {
            scop.launch {
                viewModel.loading.collectLatest {
                    loadin = it
                }
            }
        }
    }
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { navHostController.navigate(Screen.Profile.route) }) {
                    Icon(Icons.Rounded.AccountCircle, contentDescription = "")
                }
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            contentAlignment = TopCenter
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = TopCenter) {
                Category(navHostController)
            }
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Center) {
                if (loadin) {
                    Loading3Dots(isDark = true)
                }
            }
        }
    }


}

@Composable
fun Category(navHostController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        var selectedIndex by remember {
            mutableIntStateOf(0)
        }
        val item = listOf(
            "All",
            "Electronics",
            "Jewelery",
            "Men's",
            "Women's",
        )
        Row {
            item.forEachIndexed { index, item ->
                TextButton(
                    onClick = { selectedIndex = index },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedIndex == index)
                            Color(0xFFFF9800)
                        else
                            Color.White
                    )
                ) {
                    Text(
                        text = item,
                        color = if (selectedIndex == index)
                            Color.White
                        else
                            Color.DarkGray

                    )
                }
            }

            /*    Tab(
                    modifier = Modifier
                        .padding(3.dp)
                        .clip(CircleShape)
                        .background(
                            if (selectedIndex == index)
                                Color.Green
                            else
                                Color.White
                        ),
                    selected = selectedIndex == index,
                    onClick = { selectedIndex = index },
                    text = {
                        Text(
                            text = item,
                            fontSize = 9.sp
                        )
                    }
                )*/
        }


        when (selectedIndex) {
            0 -> AllItem(navHostController)
            1 -> Electronics(navHostController)
            2 -> Jewelery(navHostController)
            3 -> Mens(navHostController)
            4 -> Womens(navHostController)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun ItemProduct(navHostController: NavHostController, data: ProductItem) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        onClick = {
            navHostController.navigate(Screen.InfoItem.route + "?id=${data.id}")
        },
        elevation = CardDefaults.cardElevation(12.dp),
        modifier = Modifier
            .padding(6.dp)
            .fillMaxWidth(0.55f)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(6.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                Column {
                    GlideImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp),
                        model = data.image,
                        contentDescription = "",
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = data.title.substring(0, 16))
                    Text(
                        text = "${data.price} $",
                    )
                }

            }


        }


    }
}

@Composable
fun AllItem(navHostController: NavHostController, viewModel: MainApiViewModel = hiltViewModel()) {
    var item by remember {
        mutableStateOf(emptyList<ProductItem>())
    }
    LaunchedEffect(true) {
        viewModel.getAllItem()
        viewModel.allItem.collectLatest {
            item = it
        }
    }
    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        itemsIndexed(item) { index, item ->
            ItemProduct(navHostController, data = item)
        }
    }
}

@Composable
fun Electronics(
    navHostController: NavHostController,
    viewModel: MainApiViewModel = hiltViewModel(),
) {
    var item by remember {
        mutableStateOf(emptyList<ProductItem>())
    }
    LaunchedEffect(true) {
        viewModel.getElectronicsCategory()
        viewModel.getElectronicsCategory.collectLatest {
            item = it
        }
    }
    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        itemsIndexed(item) { index, item ->
            ItemProduct(navHostController, data = item)
        }
    }
}


@Composable
fun Jewelery(navHostController: NavHostController, viewModel: MainApiViewModel = hiltViewModel()) {
    var item by remember {
        mutableStateOf(emptyList<ProductItem>())
    }
    LaunchedEffect(true) {
        viewModel.getJeweleryCategory()
        viewModel.getJeweleryCategory.collectLatest {
            item = it
        }
    }

    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        itemsIndexed(item) { index, item ->
            ItemProduct(navHostController, data = item)
        }
    }
}

@Composable
fun Mens(navHostController: NavHostController, viewModel: MainApiViewModel = hiltViewModel()) {
    var item by remember {
        mutableStateOf(emptyList<ProductItem>())
    }
    LaunchedEffect(true) {
        viewModel.getMensCategory()
        viewModel.getMensCategory.collectLatest {
            item = it
        }
    }

    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        itemsIndexed(item) { index, item ->
            ItemProduct(navHostController, data = item)
        }
    }
}

@Composable
fun Womens(navHostController: NavHostController, viewModel: MainApiViewModel = hiltViewModel()) {
    var item by remember {
        mutableStateOf(emptyList<ProductItem>())
    }
    LaunchedEffect(true) {
        viewModel.getWomanCategory()
        viewModel.getWomanCategory.collectLatest {
            item = it
        }
    }

    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        itemsIndexed(item) { index, item ->
            ItemProduct(navHostController, data = item)
        }
    }
}


