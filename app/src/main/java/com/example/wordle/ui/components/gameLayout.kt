package com.example.wordle.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wordle.R
import com.example.wordle.domain.GameViewModel
import com.example.wordle.domain.LENGTH_OF_WORD
import com.example.wordle.domain.NUMBER_OF_ATTEMPTS
import com.example.wordle.domain.usecase.SquareState
import com.example.wordle.domain.usecase.squareStatus
import com.example.wordle.ui.game.GameScreen
import com.example.wordle.ui.theme.WordleTheme


@Composable
fun SquareView(
    state: SquareState,
    onClick: () -> Unit
) {

    val extraSmallPadding = dimensionResource(R.dimen.padding_extra_small)
    val smallPadding = dimensionResource(R.dimen.padding_small)
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(extraSmallPadding)
            .aspectRatio(1f)
            .background(
                color = InitColor(state.status),
                shape = RoundedCornerShape(smallPadding)
            )
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.inverseSurface,
                shape = RoundedCornerShape(smallPadding)
            )
            .clickable(
                enabled = state.isActive,
                onClick = onClick
            ),

        ) {
        Text(text = state.letter.uppercase())
    }
}

@Composable
fun GameLayout(
    gameViewModel: GameViewModel,
    currentField: List<SquareState>
) {

    val extraSmallPadding = dimensionResource(R.dimen.padding_extra_small)

    LazyVerticalGrid(
        columns = GridCells.Fixed(LENGTH_OF_WORD),
        modifier = Modifier.padding(extraSmallPadding),
        contentPadding = PaddingValues(extraSmallPadding)
    ) {

        items(currentField.size) { index ->
            SquareView(
                state = currentField[index],
                onClick = {gameViewModel.selectSquare(index)}
            )
        }
    }
}


@Composable
fun InitColor(state: squareStatus): Color {

    return when (state) {
        squareStatus.CurrentSquare -> MaterialTheme.colorScheme.outline
        squareStatus.NotCurrentSquare -> MaterialTheme.colorScheme.outlineVariant
        else -> MaterialTheme.colorScheme.surfaceDim
    }
}

/*
Color.DarkGray -> MaterialTheme.colorScheme.outlineVariant
        Color.Gray -> MaterialTheme.colorScheme.outline
        Color.Green -> MaterialTheme.colorScheme.tertiary
        Color.Yellow -> MaterialTheme.colorScheme.primary
        else -> MaterialTheme.colorScheme.surface
 */

@Preview(showBackground = true, widthDp = 427, heightDp = 952)
@Composable
fun GameScreenPreview() {
    WordleTheme {
        GameScreen()
    }
}