package com.example.wordle.domain.usecase

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.flow.MutableStateFlow

enum class squareState{
    CorektLetter,
    UnCorrectLetter,
    NoLetter,
    NearLetter,
    CurrentSquare
}


class Square(
    val numberOfSquare: Int,
    char: Char = ' ',
    var isVisible: Boolean = false,
    var state: squareState = squareState.NoLetter
) {
    private val mutableChar = MutableStateFlow(char)
    val char by mutableStateOf(char)

    fun updateChar(newChar: Char) {
        mutableChar.value = newChar
    }
}




data class KeyboardButton(
    val char: Char,
    var color: Color = Color.DarkGray
)