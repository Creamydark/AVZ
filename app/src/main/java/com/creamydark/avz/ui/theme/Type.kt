package com.creamydark.avz.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val baseSize = 16.sp // Base size

val fontFamilyBold = PoppinsBold
val fontFamilyRegular = PoppinsRegular

val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = fontFamilyBold,
        fontWeight = FontWeight.Bold,
        fontSize = baseSize * 2.0  // 32sp
    ),
    displayMedium = TextStyle(
        fontFamily = fontFamilyBold,
        fontWeight = FontWeight.Bold,
        fontSize = baseSize * 1.875  // 30sp
    ),
    displaySmall = TextStyle(
        fontFamily = fontFamilyBold,
        fontWeight = FontWeight.Bold,
        fontSize = baseSize * 1.75  // 28sp
    ),
    headlineLarge = TextStyle(
        fontFamily = fontFamilyBold,
        fontWeight = FontWeight.Bold,
        fontSize = baseSize * 1.625  // 26sp
    ),
    headlineMedium = TextStyle(
        fontFamily = fontFamilyBold,
        fontWeight = FontWeight.Bold,
        fontSize = baseSize * 1.5  // 24sp
    ),
    headlineSmall = TextStyle(
        fontFamily = fontFamilyBold,
        fontWeight = FontWeight.Bold,
        fontSize = baseSize * 1.375  // 22sp
    ),
    titleLarge = TextStyle(
        fontFamily = fontFamilyBold,
        fontWeight = FontWeight.Bold,
        fontSize = baseSize * 1.25  // 20sp
    ),
    titleMedium = TextStyle(
        fontFamily = fontFamilyBold,
        fontWeight = FontWeight.Bold,
        fontSize = baseSize * 1.125  // 18sp
    ),
    titleSmall = TextStyle(
        fontFamily = fontFamilyBold,
        fontWeight = FontWeight.Bold,
        fontSize = baseSize * 1.0  // 16sp
    ),
    bodyLarge = TextStyle(
        fontFamily = fontFamilyRegular,
        fontWeight = FontWeight.Normal,
        fontSize = baseSize * 0.9375  // 15sp
    ),
    bodyMedium = TextStyle(
        fontFamily = fontFamilyRegular,
        fontWeight = FontWeight.Normal,
        fontSize = baseSize * 0.875  // 14sp
    ),
    bodySmall = TextStyle(
        fontFamily = fontFamilyRegular,
        fontWeight = FontWeight.Normal,
        fontSize = baseSize * 0.8125  // 13sp
    ),
    labelLarge = TextStyle(
        fontFamily = fontFamilyBold,
        fontWeight = FontWeight.Bold,
        fontSize = baseSize * 0.75  // 12sp
    ),
    labelMedium = TextStyle(
        fontFamily = fontFamilyBold,
        fontWeight = FontWeight.Bold,
        fontSize = baseSize * 0.71875  // 11.5sp
    ),
    labelSmall = TextStyle(
        fontFamily = fontFamilyBold,
        fontWeight = FontWeight.Bold,
        fontSize = baseSize * 0.6875  // 11sp
    )
)