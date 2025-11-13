package com.example.wordle.MainScreen.data

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.wordle.R

val bottomImageList: List<BottomImage> = listOf(
    BottomImage(R.string.main, Icons.Filled.Home),
    BottomImage(R.string.rules, Icons.AutoMirrored.Filled.HelpOutline),
    BottomImage(R.string.statistic, Icons.Filled.BarChart),
    BottomImage(R.string.history, Icons.Filled.History),
    BottomImage(R.string.profile, Icons.Filled.AccountCircle),

)



data class BottomImage(
    @StringRes
    val title: Int,
    val icon: ImageVector
)




