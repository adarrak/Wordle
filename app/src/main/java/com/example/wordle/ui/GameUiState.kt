package com.example.wordle.ui

data class GameUiState(

    val currentWord: String = "apple",
    val currentAnswer: MutableList<String> = MutableList(size = currentWord.length){""},



    val currentRow: Int = 0,
    val currentColumn:Int = 0,

    val numberOfAttempts:Int = 5,
)
