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

    private val _guessedLettersOnPlace: MutableSet<Char> = mutableSetOf()
    private val _guessedLetters: MutableSet<Char> = mutableSetOf()

    fun deactivateRow(numberOfRow: Int): Boolean {
        return uiState.value.currentRow == numberOfRow
    }


    //TODO не работает функция выбора квадратика
    fun selectSquare(
        numberOfColumn: Int,
    ) {
        _uiState.update { state ->
            state.copy(
                currentColumn = numberOfColumn,
                //currentField = tempList
            )
        }
    }

    fun writeSymbol(symbol: Char) {
        _uiState.update { state ->
            val row = state.currentRow
            val column = state.currentColumn
            val tempList =
                state.currentField.map { rowList -> rowList.toMutableList() }.toMutableList()
            tempList[row][column] = tempList[row][column].copy(
                char = symbol,
                isVisible = true
            )
            val newColumn = if (column < state.currentWord.length - 1) column + 1 else column

            state.copy(
                currentField = tempList,
                currentColumn = newColumn
            )
        }
    }


    fun clearSymbol() {
        _uiState.update { state ->
            val row = state.currentRow
            val currentColumn = state.currentColumn
            val tempList = state.currentField.map { it.toMutableList() }.toMutableList()

            val newColumn = if (tempList[row][currentColumn].char == ' ' && currentColumn > 0) {
                currentColumn - 1
            } else {
                currentColumn
            }

            tempList[row][newColumn] = tempList[row][newColumn].copy(
                char = ' ',
                isVisible = true
            )

            state.copy(
                currentField = tempList,
                currentColumn = newColumn
            )
        }
    }


    fun checkButton() {
        var row = uiState.value.currentRow
        var column = uiState.value.currentColumn
        val answer = uiState.value.currentField[row].joinToString(separator = "") { square ->
            square.char.toString()
        }
        var gameOver = false
        var gameWin = false

        if (answer.contains(" ")) {
            //TODO: добавить функционал уведомления/подсветки для неполного ответа
            return
        }
        if (answer != uiState.value.currentWord) {
            checkRow(row)
            if (row != uiState.value.numberOfAttempts - 1) {
                row = row + 1
                column = 0
            } else gameOver = true
        } else {
            gameWin = true
            row = row + 1
        }
        _uiState.update { state ->
            state.copy(
                currentColumn = column,
                currentRow = row,
                isGameWin = gameWin,
                isGameOver = gameOver
            )
        }
    }


    // символ отображающийся в клетке поля
    fun visibleChar(row: Int, column: Int): String {
        return uiState.value.currentField[row][column].char.toString()
    }


    fun checkRow(row: Int) {
        val userAnswer = uiState.value.currentField[row].map { it.char }.toMutableList()
        val currentWord = uiState.value.currentWord.toMutableList()
        // Помечаем правильные символы '*'
        for (i in 0 until userAnswer.size) {
            if (userAnswer[i] == currentWord[i]) {
                userAnswer[i] = '*'
                currentWord[i] = '*'
            }
        }
        // Помечаем символы в слове, но не на своих местах '-'
        val countOfSymbols = currentWord.groupingBy { it }.eachCount().toMutableMap()
        for (i in 0 until userAnswer.size) {
            val symbol = userAnswer[i]
            if (symbol != '*') {
                if (symbol in currentWord && countOfSymbols.getOrDefault(symbol, 0) > 0) {
                    userAnswer[i] = '-'
                    countOfSymbols[symbol] = countOfSymbols.getOrDefault(symbol, 0) - 1
                }
            }
        }
        _uiState.update { state ->
            val tempList = state.currentField.map { it.toMutableList() }.toMutableList()
            for (column in 0 until currentWord.size) {
                tempList[row][column] = tempList[row][column].copy(
                    color = when (userAnswer[column]) {
                        '*' -> Color.Green
                        '-' -> Color.Yellow
                        else -> Color.DarkGray
                    }
                )
            }
            state.copy(
                currentField = tempList
            )
        }
    }

    fun checkColorSquare(row: Int, column: Int): Color {
        return uiState.value.currentField[row][column].color
    }


    //TODO не работает цвет клавиатуры, нужно разобраться

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


