package com.example.wordle.domain.usecase

import android.util.Log
import androidx.compose.runtime.MutableState
import com.example.wordle.data.SquareState
import com.example.wordle.data.squareStatus
import com.example.wordle.domain.TAG

class clickLogic(
    private val _getCurrentIndex: () -> Int,
    private val updateCurrentIndex: (Int) -> Unit,
    private val getCurrentFieldState: () -> List<MutableState<SquareState>>,
    private val updateSquareState: (index: Int, newState: SquareState) -> Unit
) {
    fun selectSquare(onClickNumber: Int) {
        Log.d(TAG, "вписана буква - ")
        val currentIndex = _getCurrentIndex()
        val currentFieldState = getCurrentFieldState()
        if (onClickNumber != currentIndex) {
            // Снимаем выделение с текущего квадрата
            updateSquareState(
                currentIndex,
                currentFieldState[currentIndex].value.copy(status = squareStatus.NotCurrentSquare)
            )
            // Выделяем новый квадрат
            updateSquareState(
                onClickNumber,
                currentFieldState[onClickNumber].value.copy(status = squareStatus.CurrentSquare)
            )
            // Обновляем индекс
            updateCurrentIndex(onClickNumber)
        }
    }
}
