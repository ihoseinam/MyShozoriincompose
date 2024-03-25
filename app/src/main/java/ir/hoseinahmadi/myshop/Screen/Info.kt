package ir.hoseinahmadi.myshop.Screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import ir.hoseinahmadi.myshop.Remote.Data.ProductItem
import ir.hoseinahmadi.myshop.Remote.Data.Rating
import ir.hoseinahmadi.myshop.ViewModel.MainApiViewModel
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun InfoItem(
    navHostController: NavHostController, id: String?,
    viewModel: MainApiViewModel = hiltViewModel()
) {
    val emty = ProductItem(
        "", "", 0, "", 0.0, Rating(0, 0.0),
        ""
    )
    var item by remember {
        mutableStateOf<ProductItem>(emty)
    }
    LaunchedEffect(true) {
        viewModel.getProductById(id!!)
        viewModel.productById.collectLatest {
            item = it
        }
    }
    Scaffold(
        topBar = {}
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {

            GlideImage(model = item.image, contentDescription = "")
            Text(text = item.title)

        }
    }
}