package com.example.wordle.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Backspace
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wordle.R
import com.example.wordle.domain.GameViewModel
import com.example.wordle.domain.LENGTH_OF_WORD
import com.example.wordle.domain.usecase.KeyboardButton
import com.example.wordle.domain.usecase.SquareState
import com.example.wordle.ui.game.GameScreen
import com.example.wordle.ui.game.convertColors
import com.example.wordle.ui.theme.WordleTheme

@Composable
fun Keyboard(
    gameViewModel: GameViewModel,
    keyBoard: List<List<KeyboardButton>>
) {

    val extraSmallPadding = dimensionResource(R.dimen.padding_extra_small)

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(extraSmallPadding)
    ) {
        keyBoard.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(extraSmallPadding)
            ) {
                row.forEach { item ->
                    Box(
                        modifier = Modifier
                            .weight(if (item.char != '*') 1f else 2f),
                        contentAlignment = Alignment.Center
                    ) {

                        KeyboardButton(
                            onClick = {},
                            text = item.char.uppercase(),
                            color = Color.Green
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun KeyboardButton(
    onClick: () -> Unit,
    text: String,
    color: Color
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(dimensionResource(R.dimen.padding_small)))
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.inverseSurface,
                shape = RoundedCornerShape(dimensionResource(R.dimen.padding_small))
            )
            .background(color)
            .clickable(
                onClick = onClick
            )
            .padding(vertical = dimensionResource(R.dimen.padding_small)),
        contentAlignment = Alignment.Center
    )
    {
        if (text != "*") {
            Text(
                text = text,
                fontSize = 18.sp
            )
        } else {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Backspace,
                contentDescription = "Clear",
            )

        }
    }

}



/*

    }
        for (item in ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_extra_small))
            ) {
                item.forEach { it ->

                    Box(
                        modifier = Modifier
                            .weight(if (it.char != '*') 1f else 2f),
                        contentAlignment = Alignment.Center
                    ) {
                        KeyboardButton(
                            text = it.char.toString(),
                            onClick = if (it.char != '*') {
                                { gameViewModel.writeSymbol(it.char) }
                            } else {
                                { gameViewModel.clearSymbol() }
                            },
                            color = convertColors(it.color)
                        )
                    }
                }
            }
        }
*/


/*

@Composable
fun Keyboard(
    gameViewModel: GameViewModel,
) {
    val gameUiState by gameViewModel.uiState.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_extra_small))
    ) {
        for (item in gameUiState.currentKeyboardButtons) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_extra_small))
            ) {
                item.forEach { it ->

                    Box(
                        modifier = Modifier
                            .weight(if (it.char != '*') 1f else 2f),
                        contentAlignment = Alignment.Center
                    ) {
                        KeyboardButton(
                            text = it.char.toString(),
                            onClick = if (it.char != '*') {
                                { gameViewModel.writeSymbol(it.char) }
                            } else {
                                { gameViewModel.clearSymbol() }
                            },
                            color = convertColors(it.color)
                        )
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        )
        {
            KeyboardButton(
                onClick = { gameViewModel.checkButton() },
                text = "CHECK",
                color = convertColors(Color.DarkGray)
            )

        }

    }

}

@Composable
fun KeyboardButton(
    onClick: () -> Unit,
    text: String,
    color: Color
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(shape = RoundedCornerShape(dimensionResource(R.dimen.padding_small)))
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.inverseSurface,
                shape = RoundedCornerShape(dimensionResource(R.dimen.padding_small))
            )
            .background(color)
            .clickable(
                onClick = onClick
            )
            .padding(vertical = dimensionResource(R.dimen.padding_small)),
        contentAlignment = Alignment.Center
    )
    {
        if (text != "*") {
            Text(
                text = text,
                fontSize = 18.sp
            )
        } else {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Backspace,
                contentDescription = "Clear",
            )

        }
    }

}

 */