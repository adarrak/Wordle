package com.example.wordle.ui

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

val stringKeyboard: Set<String> =
    setOf(
        "QWERTYUIOP",
        "ASDFGHJKL",
        "ZXCVBNM"
    )

class GameViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()





    private fun updateGameState() {

    }

    fun deactivateRow(numberOfRow: Int): Boolean {
        return uiState.value.currentRow == numberOfRow
    }

    fun selectSquare(
        numberOfColumn: Int,
    ) {
        _uiState.update { it ->
            it.copy(currentColumn = numberOfColumn)
        }
    }

    fun writeSymbol(symbol: String) {
        _uiState.update { it ->
            val tempList = it.currentAnswer
            tempList[it.currentColumn] = symbol
            it.copy(
                currentAnswer = tempList,
                currentColumn = if (it.currentColumn == it.currentWord.length - 1) {
                    0
                } else {
                    it.currentColumn + 1
                }
            )
        }
    }

    fun checkButton() {
        if (!uiState.value.currentAnswer.contains(" ")) {
            _uiState.update { it ->
                val tempList = it.userGuess
                tempList[it.currentRow] = it.currentAnswer.joinToString(separator = "")
                it.copy(
                    currentRow = it.currentRow + 1,
                    currentAnswer = MutableList(uiState.value.currentWord.length) { " " },
                    currentColumn = 0,
                    userGuess = tempList
                )

            }
        } else {

            //добавить функционал того что не полная строка для ответа
            val i = 0
        }
    }


    fun checkAnswer(row: Int, column: Int): Color {

        if (uiState.value.userGuess[row][column] == uiState.value.currentWord[column]) {
            val tempHintList = uiState.value.hintWord
            val tempHintWord = tempHintList[row].toMutableList()
            tempHintWord[column] = '*'
            tempHintWord.joinToString { "" }
            // разобраться с присвоением строки. работает не правильно
            _uiState.update { it ->
                it.copy(hintWord = tempHintList)
            }
            return Color.Green
        } else {
            //return if (uiState.value.currentAnswer[column] in _hintWord[row])
               return Color.Yellow
            //else Color.DarkGray
        }
    }


}

