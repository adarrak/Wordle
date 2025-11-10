package com.example.wordle.domain

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.wordle.data.allWords
import com.example.wordle.domain.usecase.KeyboardButton
import com.example.wordle.domain.usecase.SquareState
import com.example.wordle.domain.usecase.squareStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

const val TAG = "MyTag"

class GameViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()


    private val _currentWord = "ШУМОК"
    private var _currentIndex = 0

    init {
        gameRestart()
    }

    fun updateSquare(indexOfSquare: Int, newState: SquareState) {
        _uiState.update { currentState ->
            val updatedField = currentState.currentField.mapIndexed { i, square ->
                if (i == indexOfSquare) newState else square
            }
            currentState.copy(currentField = updatedField)
        }
    }


    fun selectSquare(onClickNumber: Int) {
        Log.d(TAG, "нажал $onClickNumber")
        val oldSquareState = _uiState.value.currentField[_currentIndex]
        val newSquareState = _uiState.value.currentField[onClickNumber]
        _uiState.update { currentState ->
            val updatedField = currentState.currentField.mapIndexed { i, square ->
                when (i) {
                    _currentIndex -> square.copy(status = squareStatus.NotCurrentSquare)
                    onClickNumber -> square.copy(status = squareStatus.CurrentSquare)
                    else -> square
                }
            }
            currentState.copy(currentField = updatedField)
        }
        _currentIndex = onClickNumber
    }


    fun gameRestart() {
        val newField = List(NUMBER_OF_SQUARE) { index ->
            SquareState(
                letter = ' ',
                status = if (index == 0) squareStatus.CurrentSquare else squareStatus.NotCurrentSquare,
                isActive = index < LENGTH_OF_WORD
            )
        }


        _uiState.update { currentState ->
            currentState.copy(currentField = newField)
        }
        _currentIndex = 0
    }


}