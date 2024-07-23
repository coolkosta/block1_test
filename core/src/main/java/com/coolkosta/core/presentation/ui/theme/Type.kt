package com.coolkosta.core.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import com.coolkosta.core.R


val OfficingSans = FontFamily(Font(R.font.officina_sans_scc_extrabold))

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    labelLarge = TextStyle(
        fontFamily = OfficingSans,
        fontSize = 21.sp,
        color = Color.White
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontStyle = FontStyle.Normal,
        fontSize = 16.sp,
        color = Color.Black
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontStyle = FontStyle.Normal,
        fontSize = 16.sp,
        color = Color.White,
    ),
    titleMedium = TextStyle(
        fontSize = 14.sp,
        textAlign = TextAlign.Center,
        textDecoration = TextDecoration.Underline,
        color = Leaf
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

    /*<style name="TextStyle7">
    <item name="android:fontFamily">sans-serif</item>
    <item name="android:textStyle">normal</item>
    <item name="android:textSize">14sp</item>
    <item name="android:lineSpacingExtra">6sp</item>
    <item name="android:textColor">@color/leaf</item>
    </style>*/
)