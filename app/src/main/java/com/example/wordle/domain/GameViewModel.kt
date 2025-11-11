package com.example.wordle.domain


import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.wordle.domain.usecase.SquareState
import com.example.wordle.domain.usecase.squareStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

const val TAG = "MyTag"

class GameViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()


    private val _currentWord = "ШУМОК"
    private val _userAnswer: MutableList<Char> = MutableList(LENGTH_OF_WORD) { ' ' }
    private var _currentIndex = 0
    private var _currentAttempt = 0


    init {
        gameRestart()
    }

    fun onClickKeyboardButton(symbol: Char) {
        Log.d(TAG, "вписана буква - $symbol")
        val lastIndex = LENGTH_OF_WORD * _currentAttempt + LENGTH_OF_WORD - 1
        val firstIndex = LENGTH_OF_WORD * _currentAttempt
        val currentFieldState = uiState.value.currentField

        //записываем нажатую букву в текущий квадрат
        fun writeSymbol() {
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

    fun selectSquare(onClickNumber: Int) {
        val currentFieldState = uiState.value.currentField
        Log.d(TAG, "вписана буква - ")

        if (onClickNumber != _currentIndex) {
            // Снимаем выделение с текущего квадрата

            currentFieldState[_currentIndex].value =
                currentFieldState[_currentIndex].value.copy(status = squareStatus.NotCurrentSquare)
            // Выделяем новый квадрат
            currentFieldState[onClickNumber].value =
                currentFieldState[onClickNumber].value.copy(status = squareStatus.CurrentSquare)
            // Обновляем индекс
            _currentIndex = onClickNumber
        }
    }

    fun gameRestart() {
        val currentFieldState = uiState.value.currentField
        for (index in 0 until NUMBER_OF_SQUARE) {
            currentFieldState[index].value = SquareState(
                id = index,
                letter = ' ',
                status = if (index == 0) squareStatus.CurrentSquare else squareStatus.NotCurrentSquare,
                isActive = index < LENGTH_OF_WORD * _currentAttempt + LENGTH_OF_WORD
            )
        }
        _currentIndex = 0
        _userAnswer.fill(' ')
    }
}