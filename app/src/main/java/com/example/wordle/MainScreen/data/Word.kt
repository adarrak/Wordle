package com.example.wordle.MainScreen.data

import com.example.wordle.GameScreen.data.SquareState
import com.example.wordle.GameScreen.data.SquareStatus
import com.example.wordle.R


val word = listOf(
    SquareState('Э'),
    SquareState('К', SquareStatus.CorrectLetter),
    SquareState('Р'),
    SquareState('А', SquareStatus.NearLetter),
    SquareState('Н'),
)

val hintMap = mapOf(word[1]to R.string.first_rules_screen_correct,
    word[3] to R.string.first_rules_screen_near,
    word[0] to R.string.first_rules_screen_un_correct,
    )