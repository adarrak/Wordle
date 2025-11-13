package com.example.wordle.data

enum class squareStatus {
    CorrectLetter,
    UnCorrectLetter,
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
    val char: Char,
    val status: squareStatus = squareStatus.NotCurrentSquare

)