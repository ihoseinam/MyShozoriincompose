package ir.hoseinahmadi.myshop.Screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import ir.hoseinahmadi.myshop.ViewModel.DataStoreViewModel
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    navHostController: NavHostController,
    viewModel2: DataStoreViewModel = hiltViewModel()

) {
    var checkLogin by remember {
        mutableStateOf(false)
    }
        LaunchedEffect(true) {
        launch {
            viewModel2.getLoginInfo().let {
                checkLogin=it
            }
        }
    }

    if (checkLogin){
        MyProfileScreen(navHostController)
    }else{
        LoginScreen(navHostController = navHostController)
    }

}