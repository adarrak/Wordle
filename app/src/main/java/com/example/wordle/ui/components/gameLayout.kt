package com.example.wordle.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.magnifier
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wordle.R
import com.example.wordle.domain.GameViewModel
import com.example.wordle.domain.SIZE_OF_FIELD
import com.example.wordle.ui.game.convertColors


@Composable
fun GameLayout(
    gameViewModel: GameViewModel,
) {
    val gameUiState by gameViewModel.uiState.collectAsState()
    val smallPadding = dimensionResource(R.dimen.padding_small)
    LazyVerticalGrid(
        columns = GridCells.Fixed(SIZE_OF_FIELD),
        modifier = Modifier.padding(smallPadding),
        contentPadding = PaddingValues(smallPadding)
    ) {
        items(SIZE_OF_FIELD * SIZE_OF_FIELD) {number ->
            val square = gameUiState.currentField[number]
            Box(contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(smallPadding)
                    .size(48.dp)
                    .background(
                        color = Color.Cyan,
                        shape = RoundedCornerShape(smallPadding)
                    )
                    .border(2.dp, Color.Black, RoundedCornerShape(4.dp))
            ) {}
        }
    }


    /* Column(
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
                                     gameViewModel.checkColorSquare(
                                         row = numberOfRow,
                                         column = numberOfColumn
                                     )
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

     */

}