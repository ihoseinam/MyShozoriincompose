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
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.provider.Settings
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.TextButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import ir.hoseinahmadi.myshop.R
import ir.hoseinahmadi.myshop.component.Loading3Dots
import ir.hoseinahmadi.myshop.ui.theme.font_bold
import ir.hoseinahmadi.myshop.ui.theme.font_medium
import ir.hoseinahmadi.myshop.ui.theme.font_standard
import ir.hoseinahmadi.myshop.ui.theme.h1
import ir.hoseinahmadi.myshop.ui.theme.h3
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun SplashScreen(navHostController: NavHostController) {
    var refreshe by remember {
        mutableStateOf(false)
    }

    var showButton by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    LaunchedEffect(refreshe, Dispatchers.Main) {
        if ( isOnline(context))
        {
            navHostController.navigate(Screen.Home.route){
                popUpTo(0){
                    inclusive =true
                }
            }
        }
        else{
            showDialogNoInternet.value =true
            showButton =true
        }
    }
    NoInternetDialog()
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        AnimatedVisibility(showButton) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(11.dp), contentAlignment = Alignment.BottomCenter
            )
            {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        refreshe = true
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                ) {
                    AnimatedVisibility(refreshe) {
                        Loading3Dots(isDark = false)
                    }
                    AnimatedVisibility(!refreshe) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "try again",
                                fontFamily = font_standard,
                                color = Color.White,
                                fontSize = 18.sp
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Icon(
                                Icons.Rounded.Refresh,
                                contentDescription = "",
                                tint = Color.White
                            )

                        }

                    }

                }
            }

            Box(modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center){
                Image(
                    painter = painterResource(id = R.drawable.logos), contentDescription ="" )
            }
        }

    }

}


var showDialogNoInternet = mutableStateOf(false)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoInternetDialog() {
    if (showDialogNoInternet.value) {
        ModalBottomSheet(
            containerColor = Color.White,
            onDismissRequest = { showDialogNoInternet.value = false },
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.48f),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "No internet connection",
                    style = h1,
                    color = Color.Red,
                    textAlign = TextAlign.End
                )
                Spacer(modifier = Modifier.height(8.dp))
                Image(
                    painter = painterResource(id = R.drawable.network),
                    contentDescription = "",
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "You need to connect to the Internet to continue or try again",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontFamily = font_standard
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    val context = LocalContext.current
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Black
                        ),
                        modifier = Modifier.weight(0.5f),
                        shape = RoundedCornerShape(11.dp),
                        onClick = {
                            val intent = Intent(Settings.ACTION_WIFI_SETTINGS)
                            context.startActivity(intent)
                        }) {
                        Text(
                            text = "Wi-Fi internet",
                            color = Color.White,
                            style = h3
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedButton(
                        modifier = Modifier.weight(0.5f),
                        shape = RoundedCornerShape(11.dp),
                        onClick = {
                            val intent = Intent(Settings.ACTION_NETWORK_OPERATOR_SETTINGS)
                            context.startActivity(intent)
                        }) {
                        Text(
                            text = "mobile Internet",
                            color = Color.Black,
                            style = h3
                        )
                    }
                }

            }
        }
    }
}


fun isOnline(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
    return when {
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
        else -> false
    }
}
