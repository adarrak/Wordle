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
        "ZXCVBNM*"
    )


class GameViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    // список подсказок
    private lateinit var _hintWord: MutableList<Char>

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
            val tempList = it.currentAnswer.toMutableList()
            tempList[it.currentColumn] = symbol
            it.copy(
                currentAnswer = tempList,
                currentColumn = if (it.currentColumn == it.currentWord.length - 1) {
                    it.currentColumn
                } else {
                    it.currentColumn + 1
                }
            )
        }
    }

    fun clearSymbol() {
        _uiState.update { it ->
            val column = it.currentColumn
            val answer = it.currentAnswer.toMutableList()
            if (answer[column] != " ") {
                answer[column] = " "
                it.copy(
                    currentAnswer = answer,
                    currentColumn = if (column != 0) column - 1 else 0
                )
            } else {
                if (column != 0) {
                    answer[column - 1] = " "
                    it.copy(
                        currentAnswer = answer,
                        currentColumn = column - 1
                    )
                } else it
            }
        }
    }

    fun checkButton() {
        if (uiState.value.currentAnswer.joinToString("") != uiState.value.currentWord) {
            if (!uiState.value.currentAnswer.contains(" ")) {
                _uiState.update { it ->
                    val tempList = it.userGuess.toMutableList()
                    tempList[it.currentRow] = it.currentAnswer.joinToString(separator = "")
                    it.copy(
                        currentRow = it.currentRow + 1,
                        currentAnswer = MutableList(uiState.value.currentWord.length) { " " },
                        currentColumn = 0,
                        userGuess = tempList,
                    )
                }
            } else {
                //добавить функционал того что не полная строка для ответа
                val i = 0
            }
        } else {
            _uiState.update { it ->
                it.copy(
                    answerIsCorrect = true,
                )
            }
        }
    }


    fun checkColorSquare(row: Int, column: Int): Color {
        val currentRow = uiState.value.currentRow
        val currentColumn = uiState.value.currentColumn
        return if (currentRow <= row) {
            if (
                column == currentColumn
                && row == currentRow
            )
                Color.Gray
            else Color.DarkGray

        } else {
            checkAnswer(
                row = row,
                column = column
            )
        }
    }


    fun checkAnswer(row: Int, column: Int): Color {
        if (uiState.value.answerIsCorrect && row == uiState.value.currentRow) {
            return Color.Green
        } else {

            val currentAnswerChar: Char = uiState.value.userGuess[row][column]
            _hintWord = uiState.value.currentWord.toMutableList()
            for (i in 0 until _hintWord.size) {
                if (_hintWord[i] == uiState.value.userGuess[row][i]) {
                    _hintWord[i] = '*'
                }
            }
            return if (_hintWord[column] == '*') Color.Green
            else {
                if (currentAnswerChar in _hintWord) Color.Yellow
                else Color.DarkGray
            }
        }
    }
}


