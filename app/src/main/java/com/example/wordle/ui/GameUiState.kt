package com.example.wordle.ui

data class GameUiState(

    val currentWord: String = "APPLE",

    //угадал ли слово?
    val isGameOver: Boolean = false,

    //правильный ответ?
    val answerIsCorrect: Boolean = false,

    // активные строка и столбец
    val currentRow: Int = 0,
    val currentColumn: Int = 0,


    // количесто попыток
    val numberOfAttempts: Int = 5,

    // ответ в активном состоянии
    val currentAnswer: MutableList<String> = MutableList(size = currentWord.length) { " " },

    //список полученных ответов
    val userGuess: MutableList<String> = MutableList(numberOfAttempts) { "" },



)
