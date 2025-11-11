package com.example.wordle.domain

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.wordle.domain.usecase.KeyboardButton
import com.example.wordle.domain.usecase.SquareState


// размер поля
const val LENGTH_OF_WORD = 5
const val NUMBER_OF_ATTEMPTS = 6
const val NUMBER_OF_SQUARE = LENGTH_OF_WORD * NUMBER_OF_ATTEMPTS


//раскладка клавиатуры "*" для стирания символа "/" для кнопки check
val stringKeyboard: List<String> = listOf("ЙЦУКЕНГШЩЗХЪ", "ФЫВАПРОЛДЖЭ", "ЯЧСМИТЬБЮ*", "/")


data class GameUiState(
    val currentField: List<MutableState<SquareState>> = List(NUMBER_OF_SQUARE) {
        mutableStateOf(SquareState())
    },
    val keyBoard: List<List<KeyboardButton>> = List(stringKeyboard.size) { row ->
        List(stringKeyboard[row].length) { index ->
            KeyboardButton(stringKeyboard[row][index])
        }
    }
)

