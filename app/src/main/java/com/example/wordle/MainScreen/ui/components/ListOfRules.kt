package com.example.wordle.MainScreen.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wordle.GameScreen.domain.LENGTH_OF_WORD
import com.example.wordle.GameScreen.ui.components.SquareView
import com.example.wordle.GameScreen.ui.theme.WordleTheme
import com.example.wordle.R
import com.example.wordle.MainScreen.data.word

@Composable
fun ListOfRules() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var ruleState by remember { mutableStateOf(0) }


        // инфобокс
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .border(
                    width = dimensionResource(R.dimen.padding_super_extra_small),
                    color = MaterialTheme.colorScheme.surfaceDim,
                    shape = RoundedCornerShape(dimensionResource(R.dimen.padding_medium))
                ),
            contentAlignment = Alignment.Center
        ) {
            when (ruleState) {
                0 -> First()
                else -> " "
            }
        }
        // точки снизу

        Row(
            modifier = Modifier
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            for (i in 0 until 5) {
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .size(if (i == ruleState) 16.dp else 8.dp)
                        .clip(CircleShape)
                        .background(color = MaterialTheme.colorScheme.surfaceDim),
                )
            }
        }
    }
}


@Composable
fun First() {
    Column(
        modifier = Modifier
            .padding(dimensionResource(R.dimen.padding_medium))
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Box {
            Text(text = stringResource(R.string.first_rules_screen_title))
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(word.size),
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_extra_small)),
            contentPadding = PaddingValues(dimensionResource(R.dimen.padding_extra_small))
        ) {

            items(word.size) { index ->
                SquareView(
                    state = word[index], {}
                )
            }
        }


        Column() {
            Row() {
                SquareView(word[1]) { }
                Text(text = stringResource(R.string.first_rules_screen_correct))
            }
            Row() {
                SquareView(word[3]) { }
                Text(text = stringResource(R.string.first_rules_screen_near))
            }
            Row(
            ) {
                SquareView(word[0]) { }
                Text(text = stringResource(R.string.first_rules_screen_un_correct))
            }
        }


    }


}


@Preview(showBackground = true, widthDp = 427, heightDp = 952)
@Composable
fun GameScreenPreview() {
    WordleTheme {
        ListOfRules()
    }
}