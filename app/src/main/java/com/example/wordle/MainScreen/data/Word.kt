package com.example.wordle.MainScreen.data

import com.example.wordle.GameScreen.data.SquareState
import com.example.wordle.GameScreen.data.SquareStatus



val word = listOf(
    SquareState('Э'),
    SquareState('К', SquareStatus.CorrectLetter),
    SquareState('Р'),
    SquareState('А', SquareStatus.NearLetter),
    SquareState('Н'),
)