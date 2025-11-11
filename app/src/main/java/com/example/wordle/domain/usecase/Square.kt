package com.example.wordle.domain.usecase

enum class squareStatus {
    CorrectLetter,
    UnCorrectLetter,
    NearLetter,
    CurrentSquare,
    NotCurrentSquare
}


data class SquareState(
    val id: Int = 0,
    val letter: Char = ' ',
    val status: squareStatus = squareStatus.NotCurrentSquare,
    val isActive: Boolean = false
)


data class KeyboardButton(
    val char: Char,
    val status: squareStatus = squareStatus.NotCurrentSquare

)