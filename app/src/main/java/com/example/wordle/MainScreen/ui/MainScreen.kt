package com.example.wordle.MainScreen.ui

import android.util.Log
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
    var bottomIndex by remember { mutableStateOf(0) }
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

            when (bottomIndex) {
                0 -> SelectGame()
                1 -> ListOfRules(startIndex = 0)
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
                    val color = when (item.id) {
                        bottomIndex -> MaterialTheme.colorScheme.primary
                        else -> MaterialTheme.colorScheme.surfaceDim
                    }
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(extraSmallPadding)
                            .aspectRatio(1f)
                            .background(
                                color = color,
                                shape = RoundedCornerShape(smallPadding)
                            )
                            .border(
                                width = 2.dp,
                                color = MaterialTheme.colorScheme.inverseSurface,
                                shape = RoundedCornerShape(smallPadding)
                            )
                            .clickable(
                                onClick = {
                                    bottomIndex = item.id
                                    Log.d("TAG", "MainScreen: $title")
                                }
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
        modifier = Modifier
        ,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = item.icon,
            contentDescription = stringResource(item.title),
        )
        Spacer(Modifier.size(dimensionResource(R.dimen.padding_extra_small)))
        Text(
            text = stringResource(item.title),
            fontSize = 12.sp,
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