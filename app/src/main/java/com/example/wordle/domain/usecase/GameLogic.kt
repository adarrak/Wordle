package com.example.wordle.domain.usecase

import android.util.Log
import androidx.compose.runtime.MutableState
import com.example.wordle.data.SquareState
import com.example.wordle.data.squareStatus
import com.example.wordle.domain.GameViewModel
import com.example.wordle.domain.LENGTH_OF_WORD
import com.example.wordle.domain.NUMBER_OF_ATTEMPTS
import com.example.wordle.domain.TAG

class GameLogic(
    getCurrentFieldState: () -> List<MutableState<SquareState>>,
    private val updateSquareState: (index: Int, newState: SquareState) -> Unit,
    getCurrentWord:()-> String
) {
    private var _currentIndex = 0
    private var _currentAttempt = 0
    private val currentFieldState = getCurrentFieldState()
    private val _userAnswer: MutableList<Char> = MutableList(LENGTH_OF_WORD) { ' ' }

    private val _currentWord = getCurrentWord()



    fun selectSquare(onClickNumber: Int) {
        Log.d(TAG, "вписана буква - ")

        if (onClickNumber != _currentIndex) {
            // Снимаем выделение с текущего квадрата
            updateSquareState(
                _currentIndex,
                currentFieldState[_currentIndex].value.copy(status = squareStatus.NotCurrentSquare)
            )
            // Выделяем новый квадрат
            updateSquareState(
                onClickNumber,
                currentFieldState[onClickNumber].value.copy(status = squareStatus.CurrentSquare)
            )
            // Обновляем индекс
            _currentIndex = onClickNumber
        }
    }

    fun onClickKeyboardButton(symbol: Char) {

        val lastIndex = LENGTH_OF_WORD * _currentAttempt + LENGTH_OF_WORD - 1
        val firstIndex = LENGTH_OF_WORD * _currentAttempt

        //записываем нажатую букву в текущий квадрат
        fun writeSymbol() {
            Log.d(TAG, "вписана буква - $symbol")
            currentFieldState[_currentIndex].value =
                currentFieldState[_currentIndex].value.copy(letter = symbol)
            _userAnswer[_currentIndex % LENGTH_OF_WORD] = symbol
            if (_currentIndex != lastIndex) selectSquare(_currentIndex + 1)
        }

        //стираем букву в текущем квадрате, а если в нем пусто то в предыдущем квадрате
        fun clearSymbol() {

                        // На первом индексе просто очищаем букву

                        if (_currentIndex == firstIndex) {
                            currentFieldState[_currentIndex].value =
                                currentFieldState[_currentIndex].value.copy(letter = ' ')
                            _userAnswer[_currentIndex % LENGTH_OF_WORD] = ' '
                        } else {
                            // Если текущая ячейка пустая, двигаемся назад и очищаем ее
                            if (currentFieldState[_currentIndex].value.letter == ' ')
                                selectSquare(_currentIndex - 1)
                        }
                        // Очищаем текущий квадрат (независимо пустой или нет)
                        currentFieldState[_currentIndex].value =
                            currentFieldState[_currentIndex].value.copy(letter = ' ')
                        _userAnswer[_currentIndex % LENGTH_OF_WORD] = ' '

                        // Если вначале был символ, то после очистки переходим на прошлый индекс
                        if (currentFieldState[_currentIndex].value.letter != ' ') {
                            selectSquare(_currentIndex - 1)
                        }
        }

        fun checkAnswer() {

            val checkMask = _currentWord.toMutableList()

            // проверка полная ли строка
            if (!_userAnswer.contains(' ')) {

                // активация следующей строки если попытки не закончились
                if (_currentAttempt < NUMBER_OF_ATTEMPTS - 1) {
                    for (index in firstIndex..lastIndex) {
                        currentFieldState[index].value =
                            currentFieldState[index].value.copy(
                                isActive = false
                            )
                        currentFieldState[index + LENGTH_OF_WORD].value =
                            currentFieldState[index + LENGTH_OF_WORD].value.copy(
                                isActive = true
                            )
                    }
                    _currentAttempt++
                    selectSquare(_currentAttempt * LENGTH_OF_WORD)
                }


                // проверка есть ли буквы на своих местах
                for (i in 0 until _userAnswer.size) {
                    if (_userAnswer[i] == _currentWord[i]) {
                        currentFieldState[i + firstIndex].value =
                            currentFieldState[i + firstIndex].value.copy(status = squareStatus.CorrectLetter)
                        _userAnswer[i] = ' '
                        checkMask[i] = ' '
                    }
                }
                val countMap = checkMask.groupingBy { it }.eachCount().toMutableMap()

                // проверка буквы не на своих местах
                for (i in 0 until _userAnswer.size) {
                    if (_userAnswer[i] != ' ' &&
                        _userAnswer[i] in checkMask &&
                        countMap.getOrDefault(
                            key = _userAnswer[i],
                            defaultValue = 0
                        ) > 0
                    ) {
                        countMap[_userAnswer[i]] = (countMap[_userAnswer[i]] ?: 0) - 1
                        currentFieldState[i + firstIndex].value =
                            currentFieldState[i + firstIndex].value.copy(status = squareStatus.NearLetter)

                    }
                }
                _userAnswer.fill(' ')
            } else {
                //TODO ответ не полный
            }
        }




        // обработчик нажатия клавиши
        when (symbol) {
            '*' -> clearSymbol()
            '/' -> checkAnswer()
            else -> writeSymbol()
        }
    }

    fun resetVariable() {
        _userAnswer.fill(' ')
        _currentIndex = 0
    }

}
