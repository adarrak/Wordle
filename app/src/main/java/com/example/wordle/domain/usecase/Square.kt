package com.example.wordle.domain.usecase

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

enum class squareStatus {
    CorrectLetter,
    UnCorrectLetter,
    NoLetter,
    NearLetter,
    CurrentSquare,
    NotCurrentSquare
}


data class SquareState(
    val letter: Char = ' ',
    val status: squareStatus = squareStatus.NotCurrentSquare,
    val isActive: Boolean = false
)


data class KeyboardButton(
    val char: Char = ' ',
    val status: squareStatus = squareStatus.NotCurrentSquare
)