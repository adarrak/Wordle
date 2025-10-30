package com.example.wordle.ui

import kotlin.collections.MutableList

val stringKeyboardEng: List<String> =
    listOf(
        "QWERTYUIOP",
        "ASDFGHJKL",
        "ZXCVBNM*"
    )

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

    //клавиатура

    val currentKeyboardButtons: MutableList<MutableList<KeyboardButton>> = MutableList(
        stringKeyboardEng.size
    ) { it ->
        val string = stringKeyboardEng[it]
        MutableList(string.length) { index ->
            KeyboardButton(char = string[index])
        }
    },
    // текущее поле
    val currentField: MutableList<MutableList<Square>> = MutableList(numberOfAttempts) { i ->
        MutableList(currentWord.length) { j ->
            Square(row = i, column = j)
        }
    }
)
