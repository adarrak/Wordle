package com.example.wordle.MainScreen.ui

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.wordle.GameScreen.ui.theme.WordleTheme
import com.example.wordle.MainScreen.data.BottomImage
import com.example.wordle.MainScreen.data.bottomImageList
import com.example.wordle.MainScreen.ui.components.ListOfRules
import com.example.wordle.MainScreen.ui.components.SelectGame
import com.example.wordle.R

@Composable
fun MainScreen() {
    val mediumPadding = dimensionResource(R.dimen.padding_medium)
    val smallPadding = dimensionResource(R.dimen.padding_small)
    val extraSmallPadding = dimensionResource(R.dimen.padding_extra_small)
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

        //бокс с финтифлюшками

        Box(
            modifier = Modifier

                .fillMaxSize()
                .weight(1f)
                .padding(vertical = mediumPadding)

        ) {
            val i = 1
            when (i) {
                0 -> SelectGame()
                1 -> ListOfRules()
            }

        }
        // нижная навигация
        Box(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                bottomImageList.forEach { item ->
                    val title = stringResource(item.title)
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(extraSmallPadding)
                            .clickable(
                                onClick = { Log.d("TAG", "MainScreen: $title") }
                            ),
                        contentAlignment = Alignment.Center,

                        ) {
                        BottomBarNavigation(item)
                    }
                }
            }
        }
    }
}


@Composable
fun BottomBarNavigation(item: BottomImage) {
    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(imageVector = item.icon, contentDescription = stringResource(item.title))
        Spacer(Modifier.size(dimensionResource(R.dimen.padding_extra_small)))
        Text(
            text = stringResource(item.title),
            fontSize = 12.sp
        )
    }
}

@Preview(showBackground = true, widthDp = 427, heightDp = 952)
@Composable
fun GameScreenPreview() {
    WordleTheme {
        MainScreen()
    }
}