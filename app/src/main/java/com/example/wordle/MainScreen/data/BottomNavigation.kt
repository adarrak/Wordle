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
    BottomImage(id=0, title = R.string.main, Icons.Filled.Home),
    BottomImage(id=1, title = R.string.rules, Icons.AutoMirrored.Filled.HelpOutline),
    BottomImage(id=2, title = R.string.statistic, Icons.Filled.BarChart),
    BottomImage(id=3, title = R.string.history, Icons.Filled.History),
    BottomImage(id=4, title = R.string.profile, Icons.Filled.AccountCircle),
)



data class BottomImage(
    val id: Int,
    @StringRes
    val title: Int,
    val icon: ImageVector
)




