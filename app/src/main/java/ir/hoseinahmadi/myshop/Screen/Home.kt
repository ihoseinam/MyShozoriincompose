package ir.hoseinahmadi.myshop.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import ir.hoseinahmadi.myshop.Remote.Data.ProductItem
import ir.hoseinahmadi.myshop.ViewModel.MainApiViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeScreen(navHostController: NavHostController) {
    Column {
        Category()
    }

}

@Composable
fun Category() {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        var selectedIndex by remember {
            mutableIntStateOf(0)
        }
        val item = listOf(
            "electronics",
            "jewelery",
            "men's",
            "women's",
        )
        TabRow(
            modifier = Modifier.padding(start = 6.dp).fillMaxWidth(0.9f),
            selectedTabIndex = selectedIndex,
            indicator = {},
            divider = {}
        ) {
            item.forEachIndexed { index, item ->
                Tab(
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
                )
            }
        }

        when (selectedIndex) {
            0 -> Electronics()
            1 -> Jewelery()
            2 -> Mens()
            3 -> Womens()
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun ItemProduct(data: ProductItem) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        onClick = { /*TODO*/ },
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
fun Electronics(viewModel: MainApiViewModel = hiltViewModel()) {
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
            ItemProduct(data = item)
        }
    }
}


@Composable
fun Jewelery(viewModel: MainApiViewModel = hiltViewModel()) {
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
            ItemProduct(data = item)
        }
    }
}

@Composable
fun Mens(viewModel: MainApiViewModel = hiltViewModel()) {
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
            ItemProduct(data = item)
        }
    }
}

@Composable
fun Womens(viewModel: MainApiViewModel = hiltViewModel()) {
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
            ItemProduct(data = item)
        }
    }
}


