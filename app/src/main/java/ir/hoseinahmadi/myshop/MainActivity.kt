package ir.hoseinahmadi.myshop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ir.hoseinahmadi.myshop.Navigation.BottomNavigation
import ir.hoseinahmadi.myshop.Navigation.NavGraph
import ir.hoseinahmadi.myshop.ui.theme.MyShopTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navHostController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            navHostController = rememberNavController()
            Scaffold(
                bottomBar = {
                    BottomNavigation(navHostController = navHostController,
                        onItemClick = {
                            navHostController.navigate(it.route) {
                                popUpTo(0) {
                                    inclusive = true
                                }
                            }
                        }
                    )
                }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                ) {
                    NavGraph(navHostController = navHostController)
                }
            }


        }
    }
}
