package ir.hoseinahmadi.myshop.Screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun FaveScreen(navHostController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
    Text(text = "FaveScreen")
    }
}