package ir.hoseinahmadi.myshop.Navigation

sealed class Screen(
    val route:String
) {
    object SplashScreen :Screen("splash")
    object Home:Screen("Home")
}