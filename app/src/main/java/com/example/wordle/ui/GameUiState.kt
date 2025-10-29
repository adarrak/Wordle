package com.example.wordle.ui

import kotlin.collections.MutableList


data class GameUiState(

    val currentWord: String = " ",

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
    val currentField: MutableList<MutableList<Square>> = MutableList(numberOfAttempts) { i ->
        MutableList(currentWord.length) { j ->
            Square(row = i, column = j)
        }
    }
)
