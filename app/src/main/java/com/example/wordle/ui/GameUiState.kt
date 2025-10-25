package com.example.wordle.ui

data class GameUiState(

    val currentWord: String = "APPLE",


    // активные строка и столбец
    val currentRow: Int = 0,
    val currentColumn: Int = 0,


    // количесто попыток
    val numberOfAttempts: Int = 5,

    // ответ в активном состоянии
    val currentAnswer: MutableList<String> = MutableList(size = currentWord.length) { " " },

    //список полученных ответов
    val userGuess: MutableList<String> = MutableList(numberOfAttempts) { "" },


    // список подсказок
    val hintWord: MutableList<String> = MutableList(numberOfAttempts) { currentWord },
)
