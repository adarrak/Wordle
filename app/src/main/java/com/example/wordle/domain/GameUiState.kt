package com.example.wordle.domain

import com.example.wordle.domain.usecase.KeyboardButton
import com.example.wordle.domain.usecase.SquareState
import kotlin.collections.MutableList


// размер поля
const val LENGTH_OF_WORD = 5
const val NUMBER_OF_ATTEMPTS = 6
const val NUMBER_OF_SQUARE = LENGTH_OF_WORD * NUMBER_OF_ATTEMPTS


//раскладка клавиатуры
val stringKeyboard: String = "ЙЦУКЕНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМИТЬБЮ*"


data class GameUiState(
    val currentField: List<SquareState> = List(NUMBER_OF_SQUARE) {
        SquareState()
    },
    val keyBoard: List<KeyboardButton> = List(stringKeyboard.length) {
        KeyboardButton()
    }
)

