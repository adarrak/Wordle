package com.example.wordle.ui.game

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wordle.R
import com.example.wordle.domain.GameViewModel
import com.example.wordle.ui.components.GameLayout
import com.example.wordle.ui.components.Keyboard
import com.example.wordle.ui.theme.WordleTheme


@Composable
fun GameScreen(
    gameViewModel: GameViewModel = viewModel(),

    ) {
    val gameUiState by gameViewModel.uiState.collectAsState()
    val mediumPadding = dimensionResource(R.dimen.padding_medium)
    val smallPadding = dimensionResource(R.dimen.padding_small)

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


        //игровое поле
        Column(
            modifier = Modifier.weight(5f),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.app_name).uppercase(),
                fontSize = 48.sp
            )
            GameLayout(gameViewModel, gameUiState.currentField)
        }
        //TODO добавить поле с подсказкой по цветам

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(mediumPadding),
            contentAlignment = Alignment.Center

        ) {

            Box(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.outlineVariant,
                        shape = RoundedCornerShape(smallPadding)
                    )
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.inverseSurface,
                        shape = RoundedCornerShape(smallPadding)
                    )
                    .padding(smallPadding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.infoBox, "qweqw", "qwsse", "qffwe"),
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
            }
        }

        //клавиатура
        Box(
            modifier = Modifier
                .weight(2f)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Keyboard(gameViewModel, gameUiState.keyBoard)
        }

    }


    /*
    if (gameUiState.isGameOver || gameUiState.isGameWin) {
        Success(
            gameUiState.isGameWin
        ) { gameViewModel.restartGame() }
    }

     */
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

@Preview(showBackground = true, widthDp = 427, heightDp = 952)
@Composable
fun GameScreenPreview() {
    WordleTheme {
        GameScreen()
        //Success(
        // true
        //) {}

    }
}