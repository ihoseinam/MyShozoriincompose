package ir.hoseinahmadi.myshop.Navigation

import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem(
    val name:String,
    val route:String,
    val selectedIcon:ImageVector,
    val unselectedIcon:ImageVector,
)
