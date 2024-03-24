package ir.hoseinahmadi.myshop.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import ir.hoseinahmadi.myshop.Navigation.Screen
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navHostController: NavHostController){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Blue)
    ) {
        LaunchedEffect(true) {
            delay(1000)
            navHostController.navigate(Screen.Home.route){
                popUpTo(0){
                    inclusive =true
                }
            }
        }
    }

}