package ir.hoseinahmadi.myshop.Navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ir.hoseinahmadi.myshop.Screen.HomeScreen
import ir.hoseinahmadi.myshop.Screen.SplashScreen

@Composable
fun NavGraph(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.SplashScreen.route,
    ) {
        composable(Screen.SplashScreen.route) {
            SplashScreen(navHostController = navHostController)
        }
        composable(Screen.Home.route) {
            HomeScreen(navHostController = navHostController)
        }
    }
}