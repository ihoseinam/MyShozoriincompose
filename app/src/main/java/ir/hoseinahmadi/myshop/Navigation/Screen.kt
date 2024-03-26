package ir.hoseinahmadi.myshop.Navigation

sealed class Screen(
    val route:String
) {
    object SplashScreen :Screen("splash")
    object Home:Screen("Home")
    object Login:Screen("login_screen")
    object InfoItem:Screen("info_screen")
    object Profile:Screen("Profile_Screen")
    object ShopingCard:Screen("ShopingCard")
    object Fav:Screen("fave_screen")

}