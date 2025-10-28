package com.example.wordle.ui

import kotlin.collections.MutableList

data class GameUiState(

    val currentWord: String = "APPLE",



    //не угадал слово и закончились попытки
    val isGameOver: Boolean = false,

    //правильный ответ?
    val isGameWin: Boolean = false,

    // активные строка и столбец
    val currentRow: Int = 0,
    val currentColumn: Int = 0,

    // количесто попыток
    val numberOfAttempts: Int = 5,


    // текущее поле
    val currentField: MutableList<MutableList<Char>> = MutableList(numberOfAttempts) {
        MutableList(
            currentWord.length
        ) { ' ' }
    }
)
