package com.example.wordle.GameScreen.domain


import androidx.lifecycle.ViewModel
import com.example.wordle.GameScreen.data.SquareState
import com.example.wordle.GameScreen.data.SquareStatus
import com.example.wordle.GameScreen.domain.usecase.GameLogic
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

const val TAG = "MyTag"

class GameViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    //TODO: переделать получение нового слова
    private val _currentWord = "АРБУЗ"


    private val gameLogic = GameLogic(
        getCurrentFieldState = { uiState.value.currentField },
        updateSquareState = { index, newState ->
            uiState.value.currentField[index].value = newState
        },
        getCurrentWord = { _currentWord },
        getCurrentKeyboard = { uiState.value.keyBoard },
        updateCurrentKeyboard = { newKeyboard ->
            _uiState.update { state -> state.copy(keyBoard = newKeyboard) }
        }
    )
    init {
        gameRestart()
    }
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
                status = if (index == 0) SquareStatus.CurrentSquare else SquareStatus.NotCurrentSquare,
                isActive = index < LENGTH_OF_WORD
            )
        }
        gameLogic.resetVariable()
    }
}