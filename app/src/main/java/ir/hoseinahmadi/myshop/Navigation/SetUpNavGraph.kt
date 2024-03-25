package ir.hoseinahmadi.myshop.Navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ir.hoseinahmadi.myshop.Screen.HomeScreen
import ir.hoseinahmadi.myshop.Screen.InfoItem
import ir.hoseinahmadi.myshop.Screen.LoginScreen
import ir.hoseinahmadi.myshop.Screen.ProfileScreen
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
        composable(Screen.Login.route) {
            LoginScreen(navHostController)
        }
        composable(Screen.InfoItem.route + "?id={id}",
            arguments = listOf(
                navArgument("id"){
                    type = NavType.StringType
                }
            )
            ) {
            InfoItem(
                navHostController = navHostController,
                id = it.arguments?.getString("id", "0")
            )
        }
        composable(Screen.Profile.route){
            ProfileScreen(navHostController = navHostController)
        }
    }
}