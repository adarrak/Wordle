package com.example.wordle.domain


import androidx.lifecycle.ViewModel
import com.example.wordle.data.SquareState
import com.example.wordle.data.squareStatus
import com.example.wordle.domain.usecase.GameLogic
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

const val TAG = "MyTag"

class GameViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    private val _currentWord = "ШУМОК"


    init {
        gameRestart()
    }


    private val gameLogic = GameLogic(
        getCurrentFieldState = { uiState.value.currentField },
        updateSquareState = { index, newState ->
            uiState.value.currentField[index].value = newState
        },
        getCurrentWord = { _currentWord }
    )

    fun onClickKeyboardButton(symbol: Char) {
        gameLogic.onClickKeyboardButton(symbol)
    }

    fun selectSquare(onClickNumber: Int) {
        gameLogic.selectSquare(onClickNumber = onClickNumber)
    }

    fun gameRestart() {

        //обновить поле
        val currentFieldState = uiState.value.currentField
        for (index in 0 until NUMBER_OF_SQUARE) {
            currentFieldState[index].value = SquareState(
                letter = ' ',
                status = if (index == 0) squareStatus.CurrentSquare else squareStatus.NotCurrentSquare,
                isActive = index < LENGTH_OF_WORD
            )
        }
        // gameLogic.resetVariable()
    }
}