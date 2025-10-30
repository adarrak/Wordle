package com.example.wordle.ui

import androidx.annotation.ColorInt
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.wordle.data.allWords
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import androidx.compose.runtime.collectAsState

val stringKeyboard: Set<String> =
    setOf(
        "QWERTYUIOP",
        "ASDFGHJKL",
        "ZXCVBNM*"
    )


class GameViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    //слова которые уже были в игре
    private val _usedWords = mutableSetOf<String>()

    private val _guessedLettersOnPlace: MutableSet<Char> = mutableSetOf()
    private val _guessedLetters: MutableSet<Char> = mutableSetOf()

    init {
        restartGame()
    }

    fun deactivateRow(numberOfRow: Int): Boolean {
        return uiState.value.currentRow == numberOfRow
    }


    fun selectSquare(numberOfColumn: Int) {
        val row = uiState.value.currentRow
        val previousColumn = uiState.value.currentColumn
        if (previousColumn == numberOfColumn) return // если тот же столбец, не обновляем

        _uiState.update { state ->
            val tempList = state.currentField.map { it.toMutableList() }.toMutableList()

            // Сброс цвета предыдущего выделенного квадрата
            tempList[row][previousColumn] = tempList[row][previousColumn].copy(
                color = Color.DarkGray
            )
            // Установка цвета для нового выделенного квадрата
            tempList[row][numberOfColumn] = tempList[row][numberOfColumn].copy(
                color = Color.Gray
            )

            state.copy(
                currentColumn = numberOfColumn,
                currentField = tempList
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
                isVisible = true,
                color = Color.DarkGray
            )
            val newColumn = if (column < state.currentWord.length - 1) column + 1 else column
            tempList[row][newColumn] = tempList[row][newColumn].copy(
                color = Color.Gray
            )
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
            tempList[row][currentColumn] = tempList[row][currentColumn].copy(
                color = Color.DarkGray
            )


            tempList[row][newColumn] = tempList[row][newColumn].copy(
                char = ' ',
                isVisible = true,
                color = Color.Gray
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
            column = 0
        }
        _uiState.update { state ->
            val tempList = state.currentField.map { it.toMutableList() }.toMutableList()
            tempList[row][column] = tempList[row][column].copy(
                color = Color.Gray
            )


            state.copy(
                currentColumn = column,
                currentRow = row,
                isGameWin = gameWin,
                isGameOver = gameOver,
                currentField = tempList
            )

        }
    }


    // символ отображающийся в клетке поля
    fun visibleChar(row: Int, column: Int): String {
        val square = uiState.value.currentField[row][column]

        if (row == uiState.value.currentRow &&
            square.char == ' ' &&
            colorCheck(column) &&
            !uiState.value.isGameOver &&
            !uiState.value.isGameWin
        ) {
            return uiState.value.currentWord[column].toString()
        }
        return square.char.toString()
    }


    fun colorCheck(column: Int): Boolean {
        for (row in 0 until uiState.value.currentRow) {
            if (uiState.value.currentField[row][column].color == Color.Green) return true
        }
        return false
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



    fun colorKeyboard(text: String): Color {
        return Color.DarkGray
    }

    fun selectCurrentWord(): String {
        val word = allWords.random().uppercase()
        return if (word in _usedWords) selectCurrentWord() else word
    }

    fun restartGame() {
        val word = selectCurrentWord()
        _usedWords.add(word)
        val row = 0
        val column = 0
        val numberOfAttempts = 5
        val field = MutableList(numberOfAttempts) { i ->
            MutableList(word.length) { j ->
                Square(row = i, column = j)
            }
        }
        _guessedLettersOnPlace.clear()
        _guessedLetters.clear()
        _uiState.update { state ->
            state.copy(
                currentWord = word,
                currentRow = row,
                currentColumn = column,
                currentField = field,
                isGameOver = false,
                isGameWin = false
            )
        }
    }

    fun isHintSymbol():Color{
        return Color.Black
    }
}



