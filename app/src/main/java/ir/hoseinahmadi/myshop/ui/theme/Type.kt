package ir.hoseinahmadi.myshop.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.sp
import ir.hoseinahmadi.myshop.R

val font_medium = FontFamily(
    Font(R.font.iranyekanmedium)
)
val font_bold = FontFamily(
    Font(R.font.iranyekanbold)
)
val font_standard = FontFamily(
    Font(R.font.iranyekan)
)

val h1 = TextStyle(
    fontFamily = font_bold,
    fontWeight = FontWeight.Medium,
    fontSize = 19.sp,
    lineHeight = 24.sp,
)

val h2 =TextStyle(
    fontFamily = font_standard,
    fontWeight = FontWeight.Medium,
    fontSize = 17.sp,
    lineHeight = 24.sp,
)
val h3 =TextStyle(
    fontFamily = font_medium,
    fontWeight = FontWeight.Normal,
    fontSize = 15.sp,
    lineHeight = 22.sp,
    letterSpacing = 0.5.sp,
)
// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)