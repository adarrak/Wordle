package com.example.wordle.ui

data class GameUiState(
    val isActiveString: Boolean = false,
    val currentWord: String = "apple"
)