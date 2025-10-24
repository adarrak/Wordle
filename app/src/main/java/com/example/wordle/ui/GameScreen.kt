package com.example.wordle.ui

import android.app.GameState
import android.health.connect.datatypes.units.Length
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
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
            text = stringResource(R.string.app_name)
        )
        Text(
            text = "4 Letter"
        )
        GameLayout()
        //GameLayout()
    }
}

@Composable
fun GameLayout(

    length: Int = 5,
) {
    var text by remember { mutableStateOf("") }
    BasicTextField(
        value = text,
        singleLine = true,
        onValueChange = { newText ->
            if (newText.length <= length) text = newText
        },
        textStyle = TextStyle(color = Color.Transparent),
        cursorBrush = SolidColor(Color.Transparent),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium)),
        decorationBox = { innerTextfield ->
            Row {
                val cursorIndex = text.length.coerceAtMost(length - 1)
                for (i in 0 until length) {
                    val char = text.getOrNull(i)?.toString() ?: ""
                    val isCursorHere = i == cursorIndex
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .border(
                                width = 1.dp,
                                color = if (isCursorHere) Color.Blue else Color.Gray,
                                shape = MaterialTheme.shapes.small
                            )
                            .padding(dimensionResource(R.dimen.padding_small)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = char, fontSize = 24.sp)
                    }
                }
            }
            innerTextfield()
        }


    )
    Spacer(Modifier.size(50.dp))
    Keyboard()
}

@Composable
fun Keyboard() {

}

@Composable
fun KeyItem(symbol: Char) {
    Box(
        modifier = Modifier
            .size(size = 20.dp)
            .border(
                shape = MaterialTheme.shapes.small,
                width = 1.dp,
                color = Color.Cyan
            )
            .padding(dimensionResource(R.dimen.padding_small)),
        contentAlignment = Alignment.Center
    )
    {
        Text(
            text = symbol.toString()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GameScreenPreview() {
    WordleTheme {
        com.example.wordle.ui.GameScreen()
    }
}