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

    private var _gameOver = false
    private var _gameWin = false

    // список подсказок
    private val _hintList: MutableList<MutableList<Char>> =
        MutableList(uiState.value.numberOfAttempts) { uiState.value.currentWord.toMutableList() }


    private val _tipWord = uiState.value.currentWord.toList()

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

    fun writeSymbol(symbol: Char) {
        _uiState.update { it ->
            val row = uiState.value.currentRow
            val column = uiState.value.currentColumn
            val tempList = uiState.value.currentField.map { it.toMutableList() }.toMutableList()
            tempList[row][column] = symbol
            if (column < uiState.value.currentWord.length) {
                it.copy(
                    currentField = tempList,
                    currentColumn = if (column == it.currentWord.length - 1) {
                        column
                    } else {
                        column + 1
                    }
                )
            } else it.copy()
        }
    }

    fun clearSymbol() {
        _uiState.update { it ->
            val row = uiState.value.currentRow
            var column = uiState.value.currentColumn
            val tempList = uiState.value.currentField.map { it.toMutableList() }.toMutableList()
            if (tempList[row][column] == ' ') {
                if (column > 0) column = column - 1
            }
            tempList[row][column] = ' '
            it.copy(
                currentField = tempList,
                currentColumn = column
            )
        }
    }


    //не меняется _hint
    fun checkRow(row: Int, answer: String) {
        val countOfSymbols = _hintList[row].groupingBy { it }.eachCount().toMutableMap()

        for (i in 0 until _hintList[row].size) {
            if (answer[i] == _hintList[row][i]) {
                //заглушка того что буква на правильном месте
                _hintList[row][i] = '*'
                countOfSymbols[answer[i]] = countOfSymbols.getOrDefault(answer[i], 0) - 1
            }
        }
        /*
        for (i in 0 until _hintList[row].size) {
            val symbol = _hintList[row][i]
            if (symbol != '*') {
                val count: Int = countOfSymbols.getOrDefault(symbol, 0)
                if (symbol in answer && count > 0) {
                    _hintList[row][i] = '-' // значение того что символ стоит не на своем месте
                    countOfSymbols[symbol] = countOfSymbols.getOrDefault(symbol, 0) - 1
                }

            }
        }

         */
    }

    fun checkButton() {
        var row = uiState.value.currentRow
        var column = uiState.value.currentColumn
        val answer = uiState.value.currentField[row].joinToString("")

        //исправление _hintList
        checkRow(row, answer)

        if (!answer.contains(" ")) {
            if (answer != uiState.value.currentWord) {
                if (row != uiState.value.numberOfAttempts - 1) {
                    row = row + 1
                    column = 0
                } else _gameOver = true
            } else {
                _gameWin = true
                row = row + 1
            }
            _uiState.update { it ->
                it.copy(
                    currentColumn = column,
                    currentRow = row,
                    isGameWin = _gameWin,
                    isGameOver = _gameOver
                )
            }

        } else {
            //добавить функционал того что не полная строка для ответа
        }
    }

    // символ отображающийся в клетке поля
    fun visibleChar(row: Int, column: Int): String {
        return uiState.value.currentField[row][column].toString()
    }

    fun checkColorSquare(row: Int, column: Int): Color {
        val currentRow = uiState.value.currentRow
        return if (row > currentRow) Color.DarkGray
        else {
            if (row == currentRow) {
                if (column == uiState.value.currentColumn) Color.Gray
                else Color.DarkGray
            } else {
                val symbol = uiState.value.currentField[row][column]
                return when (symbol) {
                    '*' -> Color.Green
                    '-' -> Color.Yellow
                    else -> Color.DarkGray
                }
            }
        }
    }
}


