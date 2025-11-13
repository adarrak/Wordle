package com.example.wordle.GameScreen.domain.usecase

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext
import com.example.wordle.GameScreen.data.KeyboardButton
import com.example.wordle.GameScreen.data.SquareState
import com.example.wordle.GameScreen.data.SquareStatus
import com.example.wordle.GameScreen.domain.LENGTH_OF_WORD
import com.example.wordle.GameScreen.domain.NUMBER_OF_ATTEMPTS
import com.example.wordle.GameScreen.domain.TAG

class GameLogic(
    private val getCurrentFieldState: () -> List<MutableState<SquareState>>,
    private val updateSquareState: (index: Int, newState: SquareState) -> Unit,
    private val getCurrentWord: () -> String,
    private val getCurrentKeyboard: () -> List<List<KeyboardButton>>,
    private val updateCurrentKeyboard: (newKeyboard: List<List<KeyboardButton>>) -> Unit
) {
    private var _currentIndex = 0
    private var _currentAttempt = 0
    private val currentFieldState
        get() = getCurrentFieldState()
    private val _userAnswer: MutableList<Char> = MutableList(LENGTH_OF_WORD) { ' ' }
    private val _correctLetter: MutableSet<Char> = mutableSetOf()
    private val _nearLetter: MutableSet<Char> = mutableSetOf()
    private val _unCorrectLetter: MutableSet<Char> = mutableSetOf()
    private val _currentWord
        get() = getCurrentWord()
    fun selectSquare(onClickNumber: Int) {
        Log.d(TAG, "вписана буква - ")
        if (onClickNumber != _currentIndex) {
            // Снимаем выделение с текущего квадрата
            updateSquareState(
                _currentIndex,
                currentFieldState[_currentIndex].value.copy(status = SquareStatus.NotCurrentSquare)
            )
            // Выделяем новый квадрат
            updateSquareState(
                onClickNumber,
                currentFieldState[onClickNumber].value.copy(status = SquareStatus.CurrentSquare)
            )
            // Обновляем индекс
            _currentIndex = onClickNumber
        }
    }
    // обработчик нажатия клавиши
    fun onClickKeyboardButton(symbol: Char) {
        when (symbol) {
            '*' -> clearSymbol()
            '/' -> checkAnswer()
            else -> writeSymbol(symbol)
        }
    }

    //записываем нажатую букву в текущий квадрат
    private fun writeSymbol(symbol: Char) {
        Log.d(TAG, "вписана буква - $symbol")
        val lastIndex = LENGTH_OF_WORD * _currentAttempt + LENGTH_OF_WORD - 1
        val currentFieldState = currentFieldState
        currentFieldState[_currentIndex].value =
            currentFieldState[_currentIndex].value.copy(letter = symbol)
        _userAnswer[_currentIndex % LENGTH_OF_WORD] = symbol
        if (_currentIndex != lastIndex) selectSquare(_currentIndex + 1)
    }

    //стираем букву в текущем квадрате, а если в нем пусто то в предыдущем квадрате
    private fun clearSymbol() {
        val firstIndex = LENGTH_OF_WORD * _currentAttempt
        val currentFieldState = currentFieldState

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

    //проверка введенного ответа
    private fun checkAnswer() {
        val firstIndex = LENGTH_OF_WORD * _currentAttempt
        val lastIndex = LENGTH_OF_WORD * _currentAttempt + LENGTH_OF_WORD - 1
        val checkMask = _currentWord.toMutableList()
        // проверка полная ли строка
        if (!_userAnswer.contains(' ')) {

            // активация следующей строки если попытки не закончились
            if (_currentAttempt < NUMBER_OF_ATTEMPTS - 1) {
                activateNextAttempt(firstIndex, lastIndex)
                _currentAttempt++
                selectSquare(_currentAttempt * LENGTH_OF_WORD)
            }

            // проверка есть ли буквы на своих местах
            for (i in 0 until _userAnswer.size) {
                //если буквы нет в ответе добавить в некорректные
                if (_userAnswer[i] !in _currentWord) _unCorrectLetter.add(_userAnswer[i])
                if (_userAnswer[i] == _currentWord[i]) {
                    //если буква на правильном месте добавить в некорректные
                    _correctLetter.add(_currentWord[i])
                    currentFieldState[i + firstIndex].value =
                        currentFieldState[i + firstIndex].value.copy(status = SquareStatus.CorrectLetter)
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
                    _nearLetter.add(_userAnswer[i])
                    countMap[_userAnswer[i]] = (countMap[_userAnswer[i]] ?: 0) - 1
                    currentFieldState[i + firstIndex].value =
                        currentFieldState[i + firstIndex].value.copy(status = SquareStatus.NearLetter)
                }
            }
            _userAnswer.fill(' ')
            updateKeyboard()
        } else {
            //TODO ответ не полный
        }
    }

    private fun activateNextAttempt(from: Int, to: Int) {
        val currentFieldState = currentFieldState
        for (i in from..to) {
            // деактивация текущей строки
            currentFieldState[i].value = currentFieldState[i].value.copy(isActive = false)
            // активация следующей строки
            currentFieldState[i + LENGTH_OF_WORD].value =
                currentFieldState[i + LENGTH_OF_WORD].value.copy(isActive = true)
        }
    }

    private fun updateKeyboard() {
        val currentKeyboard = getCurrentKeyboard()
        val newKeyboard: List<List<KeyboardButton>> = currentKeyboard.map { row ->
            row.map { item ->
                when (item.char) {
                    in _correctLetter -> item.copy(status = SquareStatus.CorrectLetter)
                    in _nearLetter -> item.copy(status = SquareStatus.NearLetter)
                    in _unCorrectLetter -> item.copy(status = SquareStatus.UnCorrectLetter)
                    else -> item
                }
            }
        }
        updateCurrentKeyboard(newKeyboard)
    }

    fun resetVariable() {
        _userAnswer.fill(' ')
        _currentIndex = 0
        _currentAttempt = 0
        _nearLetter.clear()
        _correctLetter.clear()
        _unCorrectLetter.clear()
    }
}
