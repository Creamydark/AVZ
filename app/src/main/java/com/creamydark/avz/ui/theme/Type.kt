package com.creamydark.avz.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val baseSize = 14.sp // Base size

val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = PoppinsBold,
        fontWeight = FontWeight.Bold,
        fontSize = baseSize * 2.0  // 32sp
    ),
    displayMedium = TextStyle(
        fontFamily = PoppinsBold,
        fontWeight = FontWeight.Bold,
        fontSize = baseSize * 1.875  // 30sp
    ),
    displaySmall = TextStyle(
        fontFamily = PoppinsBold,
        fontWeight = FontWeight.Bold,
        fontSize = baseSize * 1.75  // 28sp
    ),
    headlineLarge = TextStyle(
        fontFamily = PoppinsBold,
        fontWeight = FontWeight.Bold,
        fontSize = baseSize * 1.625  // 26sp
    ),
    headlineMedium = TextStyle(
        fontFamily = PoppinsBold,
        fontWeight = FontWeight.Bold,
        fontSize = baseSize * 1.5  // 24sp
    ),
    headlineSmall = TextStyle(
        fontFamily = PoppinsBold,
        fontWeight = FontWeight.Bold,
        fontSize = baseSize * 1.375  // 22sp
    ),
    titleLarge = TextStyle(
        fontFamily = PoppinsBold,
        fontWeight = FontWeight.Bold,
        fontSize = baseSize * 1.25  // 20sp
    ),
    titleMedium = TextStyle(
        fontFamily = PoppinsBold,
        fontWeight = FontWeight.Bold,
        fontSize = baseSize * 1.125  // 18sp
    ),
    titleSmall = TextStyle(
        fontFamily = PoppinsBold,
        fontWeight = FontWeight.Bold,
        fontSize = baseSize * 1.0  // 16sp
    ),
    bodyLarge = TextStyle(
        fontFamily = PoppinsRegular,
        fontWeight = FontWeight.Normal,
        fontSize = baseSize * 0.9375  // 15sp
    ),
    bodyMedium = TextStyle(
        fontFamily = PoppinsRegular,
        fontWeight = FontWeight.Normal,
        fontSize = baseSize * 0.875  // 14sp
    ),
    bodySmall = TextStyle(
        fontFamily = PoppinsRegular,
        fontWeight = FontWeight.Normal,
        fontSize = baseSize * 0.8125  // 13sp
    ),
    labelLarge = TextStyle(
        fontFamily = PoppinsBold,
        fontWeight = FontWeight.Bold,
        fontSize = baseSize * 0.75  // 12sp
    ),
    labelMedium = TextStyle(
        fontFamily = PoppinsBold,
        fontWeight = FontWeight.Bold,
        fontSize = baseSize * 0.71875  // 11.5sp
    ),
    labelSmall = TextStyle(
        fontFamily = PoppinsBold,
        fontWeight = FontWeight.Bold,
        fontSize = baseSize * 0.6875  // 11sp
    )
)