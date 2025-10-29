package com.example.wordle.ui

import androidx.compose.ui.graphics.Color

data class Square(
    val row: Int,
    val column: Int,
    var char: Char = ' ',
    var isVisible: Boolean = false,
    var color: Color = if (row == column && row == 0) Color.Gray else Color.DarkGray,
)