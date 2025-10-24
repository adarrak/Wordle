package com.example.wordle.ui

import android.app.GameState
import android.health.connect.datatypes.units.Length
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wordle.R
import com.example.wordle.ui.theme.WordleTheme
import java.text.DecimalFormatSymbols
import kotlin.text.forEach


@Composable
fun GameScreen(
    gameViewModel: GameViewModel = viewModel()
) {
    val mediumPadding = dimensionResource(R.dimen.padding_medium)

    val gameUiState by gameViewModel.uiState.collectAsState()
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
                                color = if (
                                    numberOfColumn == gameUiState.currentColumn
                                    && numberOfRow == gameUiState.currentRow
                                ) {
                                    Color.Gray
                                } else {
                                    Color.DarkGray
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
                                ""
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
                                gameViewModel.some(it.toString())
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