package com.example.wordle.ui

import androidx.compose.material3.MaterialTheme
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

    private val _guessedLettersOnPlace: MutableSet<Char> = mutableSetOf()
    private val _guessedLetters: MutableSet<Char> = mutableSetOf()

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
            _hintList[row][column] = symbol
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


    fun checkButton() {
        var row = uiState.value.currentRow
        var column = uiState.value.currentColumn
        val answer = uiState.value.currentField[row].joinToString("")
        checkRow(row)
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

        for (i in 0 until uiState.value.currentRow) {
            if (_hintList[i][column] == '*' &&
                row == uiState.value.currentRow &&
                uiState.value.currentField[row][column] == ' '
            ) return uiState.value.currentWord[column].toString()
        }
        return uiState.value.currentField[row][column].toString()
    }


    fun checkRow(row: Int) {
        val answer = uiState.value.currentWord.toMutableList()

        for (i in 0 until _hintList[row].size) {
            if (answer[i] == _hintList[row][i]) {

                //заглушка того что буква на правильном месте
                _guessedLettersOnPlace.add(answer[i])
                _hintList[row][i] = '*'
                answer[i] = '*'
            }
        }
        val countOfSymbols = answer.groupingBy { it }.eachCount().toMutableMap()
        for (i in 0 until _hintList[row].size) {
            val symbol = _hintList[row][i]
            if (symbol != '*') {
                if (symbol in answer && countOfSymbols.getOrDefault(symbol, 0) > 0) {

                    //заглушка того что буква есть в слове но не на правильном месте
                    _hintList[row][i] = '-'
                    countOfSymbols[symbol] = countOfSymbols.getOrDefault(symbol, 0) - 1
                }
            }
        }
    }

    fun checkColorSquare(row: Int, column: Int): Color {
        val currentRow = uiState.value.currentRow
        return if (row > currentRow) Color.DarkGray
        else {
            if (row == currentRow) {
                if (column == uiState.value.currentColumn) Color.Gray
                else Color.DarkGray
            } else {
                val symbol = _hintList[row][column]
                return when (symbol) {
                    '*' -> Color.Green
                    '-' -> Color.Yellow
                    else -> Color.DarkGray
                }
            }
        }
    }


    //не работает цвет клавиатуры, нужно разобраться

    fun colorKeyboard(text: String): Color {
        val symbol = text.toCharArray()
        return if (symbol.size == 1) {
            if (symbol[0] in _guessedLettersOnPlace) {
                Color.Green
            } else {
                if (symbol[0] in _guessedLetters) Color.Yellow
                else Color.DarkGray
            }
        } else Color.DarkGray
    }
}


