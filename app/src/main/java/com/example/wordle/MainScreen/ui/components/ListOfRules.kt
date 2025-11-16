package com.example.wordle.MainScreen.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.wordle.GameScreen.ui.theme.WordleTheme
import com.example.wordle.MainScreen.ui.MainScreen
import com.example.wordle.R

@Composable
fun ListOfRules() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        // инфобокс
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .border(
                    width = dimensionResource(R.dimen.padding_super_extra_small),
                    color = MaterialTheme.colorScheme.errorContainer,
                    shape = RoundedCornerShape(dimensionResource(R.dimen.padding_medium))
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "as")
        }
        // точки снизу
        Box() {
            Text(text = "asd")
        }

    }
}


@Preview(showBackground = true, widthDp = 427, heightDp = 952)
@Composable
fun GameScreenPreview() {
    WordleTheme {
        MainScreen()
    }
}