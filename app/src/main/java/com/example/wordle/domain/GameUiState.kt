package com.example.wordle.domain

import com.example.wordle.domain.usecase.KeyboardButton
import com.example.wordle.domain.usecase.SquareState
import kotlin.collections.MutableList


// размер поля
const val LENGTH_OF_WORD = 5
const val NUMBER_OF_ATTEMPTS = 6
const val NUMBER_OF_SQUARE = LENGTH_OF_WORD * NUMBER_OF_ATTEMPTS


//раскладка клавиатуры
val stringKeyboard: List<String> = listOf("ЙЦУКЕНГШЩЗХЪ","ФЫВАПРОЛДЖЭ","ЯЧСМИТЬБЮ*")


data class GameUiState(
    val currentField: List<SquareState> = List(NUMBER_OF_SQUARE) {
        SquareState()
    },
    val keyBoard: List<List<KeyboardButton>> = List(stringKeyboard.size){row ->
        List(stringKeyboard[row].length) { index ->
            KeyboardButton(stringKeyboard[row][index])
        }
    }



)

