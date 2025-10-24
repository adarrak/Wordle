package com.example.wordle.ui

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

    fun some(symbol: String) {
        _uiState.update { it ->
            val tempList = it.currentAnswer.toMutableList()
            tempList[it.currentColumn] = symbol
            it.copy(
                currentAnswer = tempList,
                currentColumn = if (it.currentColumn == it.currentWord.length - 1) {
                    it.currentColumn
                } else {
                    it.currentColumn + 1
                },
            )

        }
    }
}

