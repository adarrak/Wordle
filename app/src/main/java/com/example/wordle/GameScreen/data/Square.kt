package com.example.wordle.GameScreen.data

enum class SquareStatus {
    CorrectLetter,
    UnCorrectLetter,
    NearLetter,
    CurrentSquare,
    NotCurrentSquare
}


data class SquareState(
    val letter: Char = ' ',
    val status: SquareStatus = SquareStatus.NotCurrentSquare,
    val isActive: Boolean = false
)


data class KeyboardButton(
    val char: Char,
    val status: SquareStatus = SquareStatus.NotCurrentSquare

)