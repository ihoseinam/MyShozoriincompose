package ir.hoseinahmadi.myshop.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import ir.hoseinahmadi.myshop.Navigation.Screen
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navHostController: NavHostController){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LaunchedEffect(true) {
            delay(1000)
            navHostController.navigate(Screen.Home.route){
                popUpTo(0){
                    inclusive =true
                }
            }
        }

        Text(text = "سلام گویو لولی لولی",
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            color = Color.White
            )

    }

}