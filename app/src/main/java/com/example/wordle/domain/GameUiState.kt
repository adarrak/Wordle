package com.example.wordle.domain

import com.example.wordle.domain.usecase.KeyboardButton
import com.example.wordle.domain.usecase.Square
import kotlin.collections.MutableList


// размер поля
const val SIZE_OF_FIELD = 5
val stringKeyboard: List<String> =
    listOf(
        "ЙЦУКЕНГШЩЗХЪ",
        "ФЫВАПРОЛДЖЭ",
        "ЯЧСМИТЬБЮ*"
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


    //клавиатура

    val currentKeyboardButtons: MutableList<MutableList<KeyboardButton>> = MutableList(
        stringKeyboard.size
    ) { it ->
        val string = stringKeyboard[it]
        MutableList(string.length) { index ->
            KeyboardButton(char = string[index])
        }
    },
    // текущее поле
    val currentField: List<Square> = List(SIZE_OF_FIELD * SIZE_OF_FIELD) { number ->
        Square(
            number
        )
    }
)
