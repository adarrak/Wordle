package com.example.wordle.ui

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Backspace
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wordle.R
import com.example.wordle.ui.theme.WordleTheme

@Composable
fun convertColors(
    gameViewModel: GameViewModel = viewModel(),
    numberOfRow: Int,
    numberOfColumn: Int
): Color {
    return when (
        gameViewModel.checkColorSquare(
            row = numberOfRow,
            column = numberOfColumn
        )) {
        Color.DarkGray -> MaterialTheme.colorScheme.outlineVariant
        Color.Gray -> MaterialTheme.colorScheme.outline
        Color.Green -> MaterialTheme.colorScheme.tertiary
        Color.Yellow -> MaterialTheme.colorScheme.primary
        else -> MaterialTheme.colorScheme.surface
    }
}


@Composable
fun roundedCorner(numberOfRow: Int, numberOfColumn: Int, gameViewModel: GameViewModel): Shape {
    val gameUiState by gameViewModel.uiState.collectAsState()
    val shape = when (Pair(numberOfRow, numberOfColumn)) {
        Pair(
            0,
            0
        ) -> RoundedCornerShape(topStart = dimensionResource(R.dimen.padding_medium))

        Pair(
            0,
            gameUiState.currentWord.length - 1
        ) -> RoundedCornerShape(topEnd = dimensionResource(R.dimen.padding_medium))

        Pair(
            gameUiState.numberOfAttempts - 1,
            0
        ) -> RoundedCornerShape(bottomStart = dimensionResource(R.dimen.padding_medium))

        Pair(
            gameUiState.numberOfAttempts - 1,
            gameUiState.currentWord.length - 1
        ) -> RoundedCornerShape(bottomEnd = dimensionResource(R.dimen.padding_medium))

        else -> RoundedCornerShape(size = 0.dp)
    }
    return shape
}

@Composable
fun GameScreen(
    gameViewModel: GameViewModel = viewModel(),

    ) {
    val gameUiState by gameViewModel.uiState.collectAsState()
    val mediumPadding = dimensionResource(R.dimen.padding_medium)

    Column(
        modifier = Modifier
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .safeDrawingPadding()
            .fillMaxSize()
            .padding(mediumPadding),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.app_name).uppercase(),
            fontSize = 48.sp
        )

        GameLayout(gameViewModel)

        Keyboard(gameViewModel)

    }
    if (gameUiState.isGameOver || gameUiState.isGameWin) {
        Success(
            gameUiState.isGameWin
        ) { gameViewModel.restartGame() }
    }
}


@Composable
fun GameLayout(
    gameViewModel: GameViewModel,
) {
    val gameUiState by gameViewModel.uiState.collectAsState()
    val smallPadding = dimensionResource(R.dimen.padding_small)
    Column(
        verticalArrangement = Arrangement.spacedBy(smallPadding)
    ) {
        for (numberOfRow in 0 until gameUiState.numberOfAttempts) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(smallPadding)
            ) {
                for (numberOfColumn in 0 until gameUiState.currentWord.length) {
                    Box(
                        modifier = Modifier
                            .weight(weight = 1f)
                            .aspectRatio(1f)
                            .clip(shape = roundedCorner(numberOfRow, numberOfColumn, gameViewModel))
                            .clickable(
                                enabled = gameViewModel.deactivateRow(numberOfRow),
                                onClick = {
                                    gameViewModel.selectSquare(numberOfColumn)
                                }
                            )
                            .background(
                                color = convertColors(
                                    numberOfRow = numberOfRow,
                                    numberOfColumn = numberOfColumn
                                )
                            )
                            .border(
                                width = 2.dp,
                                color = MaterialTheme.colorScheme.inverseSurface,
                                shape = roundedCorner(numberOfRow, numberOfColumn, gameViewModel)
                            )
                            .padding(dimensionResource(R.dimen.padding_small)),
                        contentAlignment = Alignment.Center
                    )
                    {
                        Text(
                            text = gameViewModel.visibleChar(numberOfRow, numberOfColumn),
                            fontSize = 32.sp,
                            color = if (gameViewModel.isHintSymbol(
                                    numberOfRow,
                                    numberOfColumn
                                ) == Color.Black
                            )
                                MaterialTheme.colorScheme.onSurface
                            else MaterialTheme.colorScheme.surfaceContainerHigh
                        )
                    }
                }
            }
        }
    }
}

@SuppressLint("ContextCastToActivity")
@Composable
fun Success(
    isGameWin: Boolean,
    onClick: () -> Unit
) {
    val activity = (LocalContext.current as Activity)
    AlertDialog(
        onDismissRequest = {},
        title = {
            Text(
                text = if (isGameWin) stringResource(R.string.congratulations) else stringResource(
                    R.string.doNotUpset
                )
            )
        },
        text = {
            Text(
                text = stringResource(
                    R.string.again,
                    if (isGameWin) stringResource(R.string.win)
                    else stringResource(R.string.lose)
                )
            )
        },

        modifier = Modifier,
        dismissButton = {
            TextButton(
                onClick = {
                    activity.finish()
                }
            ) {
                Text(text = stringResource(R.string.exit))
            }
        },
        confirmButton = {
            TextButton(onClick = onClick) {
                Text(text = stringResource(R.string.letsGo))
            }
        }
    )
}

//TODO переделать клавиатуру
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

                    }
                }
            }
        }

    }

}

/*
@Composable
fun Keyboard(
    gameViewModel: GameViewModel,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_extra_small))
    ) {
        for (item in stringKeyboard) {

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_extra_small))
            ) {

                item.forEach { it ->

                    Box(
                        modifier = Modifier
                            .weight(if (it != '*') 1f else 2f),
                        contentAlignment = Alignment.Center
                    )
                    {
                        KeyboardButton(
                            text = it.toString(),
                            onClick = if (it != '*') {
                                { gameViewModel.writeSymbol(it) }
                            } else {
                                { gameViewModel.clearSymbol() }
                            },
                            gameViewModel = viewModel()
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
                gameViewModel = viewModel()
            )

        }
    }
}


@Composable
fun KeyboardButton(
    onClick: () -> Unit,
    text: String,
    gameViewModel: GameViewModel,
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
            .background(Color.Red)
            .clickable(
                onClick = onClick
            )
            .padding(dimensionResource(R.dimen.padding_small)),
        contentAlignment = Alignment.Center
    )
    {
        if (text != "*") {
            Text(
                text = text,
                fontSize = 24.sp
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

@Preview(showBackground = true, widthDp = 427, heightDp = 952)
@Composable
fun GameScreenPreview() {
    WordleTheme {
        GameScreen()
        Success(
            true
        ) {}

    }
}