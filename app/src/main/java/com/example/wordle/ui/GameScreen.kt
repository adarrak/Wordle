package com.example.wordle.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wordle.R
import com.example.wordle.ui.theme.WordleTheme


@Composable
fun GameScreen(
) {
    val mediumPadding = dimensionResource(R.dimen.padding_medium)
    Column(
        modifier = Modifier
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .safeDrawingPadding()
            .padding(mediumPadding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.app_name).uppercase()
        )
        Text(
            text = "5 Letter"
        )
        GameLayout()
        Spacer(Modifier.size(50.dp))
        Keyboard()
    }
}

@Composable
fun GameLayout(
    gameViewModel: GameViewModel = viewModel(),
) {
    val gameUiState by gameViewModel.uiState.collectAsState()
    val smallPadding = dimensionResource(R.dimen.padding_small)

    var text by remember { mutableStateOf("") }

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
                            .clickable(
                                enabled = gameViewModel.deactivateRow(numberOfRow),
                                onClick = {
                                    gameViewModel.selectSquare(numberOfColumn)
                                }
                            )
                            .background(
                                color =
                                    if (gameUiState.currentRow <= numberOfRow) {
                                        if (
                                            numberOfColumn == gameUiState.currentColumn
                                            && numberOfRow == gameUiState.currentRow
                                        ) {
                                            Color.Gray
                                        } else {
                                            Color.DarkGray
                                        }
                                    } else {
                                        gameViewModel.checkAnswer(
                                            row = numberOfRow,
                                            column = numberOfColumn
                                        )
                                    }
                            )
                            .padding(dimensionResource(R.dimen.padding_small)),


                        contentAlignment = Alignment.Center
                    )
                    {
                        Text(
                            text = if (gameUiState.currentRow == numberOfRow) {
                                gameUiState.currentAnswer[numberOfColumn]
                            } else {
                                if (gameUiState.currentRow > numberOfRow) {
                                    gameUiState.userGuess[numberOfRow][numberOfColumn].toString()
                                } else {
                                    " "
                                }
                            },
                            fontSize = 32.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Keyboard(
    gameViewModel: GameViewModel = viewModel(),
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
                            .weight(1f)
                            .clip(shape = RoundedCornerShape(dimensionResource(R.dimen.padding_small)))
                            .background(color = MaterialTheme.colorScheme.primary)
                            .clickable {
                                gameViewModel.writeSymbol(it.toString())
                            }
                            .padding(dimensionResource(R.dimen.padding_small)),
                        contentAlignment = Alignment.Center
                    )
                    {
                        Text(
                            text = it.toString(),
                            fontSize = 24.sp
                        )
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(dimensionResource(R.dimen.padding_small)))
                .background(color = MaterialTheme.colorScheme.primary)
                .clickable {
                    gameViewModel.checkButton()
                }
                .padding(dimensionResource(R.dimen.padding_small)),
            contentAlignment = Alignment.Center
        )
        {
            Text(
                text = "CHECK"
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GameScreenPreview() {
    WordleTheme {
        GameScreen()
    }
}