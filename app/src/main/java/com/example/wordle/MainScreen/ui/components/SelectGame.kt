package com.example.wordle.MainScreen.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun SelectGame() {
    Box(Modifier.height(height = 200.dp).clickable(onClick = {}).fillMaxWidth(), contentAlignment = Alignment.Center){
        Text(text = "Начало игры")
    }
}