package com.example.wordle.MainScreen.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import com.example.wordle.GameScreen.ui.components.SquareView
import com.example.wordle.GameScreen.ui.theme.WordleTheme
import com.example.wordle.MainScreen.data.hintMap
import com.example.wordle.MainScreen.data.word
import com.example.wordle.R
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.roundToInt

@Composable
fun ListOfRules(startIndex: Int) {
    var currentIndex by remember { mutableIntStateOf(startIndex) }
    val offsetX = remember { Animatable(0f) }
    val items: List<@Composable () -> Unit> =
        listOf({ First() }, { Second() }, { Third() }, { Fourth() }, { Fifth() })
    Column(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(currentIndex) {
                coroutineScope {
                    detectHorizontalDragGestures(
                        onDragEnd = {
                            val threshold = size.width / 4
                            if (abs(offsetX.value) > threshold) {
                                currentIndex =
                                    (currentIndex + if (offsetX.value < 0) 1 else -1).coerceIn(
                                        0,
                                        items.lastIndex
                                    )
                            }
                            launch {
                                offsetX.animateTo(0f) // Возврат смещения к нулю
                            }

                        },
                        onHorizontalDrag = { _, dragAmount ->
                            launch {
                                offsetX.snapTo(offsetX.value + dragAmount)
                            }
                        }
                    )
                }
            },
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {

        LaunchedEffect(currentIndex) {
            offsetX.snapTo(0f)
        }

        // инфобокс
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .offset { IntOffset(offsetX.value.roundToInt(), 0) }
                .border(
                    width = dimensionResource(R.dimen.padding_super_extra_small),
                    color = MaterialTheme.colorScheme.surfaceDim,
                    shape = RoundedCornerShape(dimensionResource(R.dimen.padding_medium))
                ),
            contentAlignment = Alignment.Center
        ) {
            items[currentIndex]()
        }
        // точки снизу

        Row(
            modifier = Modifier
                .padding(dimensionResource(R.dimen.padding_medium)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            for (i in 0 until items.size) {
                val size by animateDpAsState(if (i == currentIndex) dimensionResource(R.dimen.padding_medium) else dimensionResource(R.dimen.padding_small))
                val color by animateColorAsState(if (i == currentIndex) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.outline)

                Box(
                    modifier = Modifier
                        .padding(dimensionResource(R.dimen.padding_small))
                        .size(size)
                        .clip(CircleShape)
                        .background(color)
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
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Box {
            Text(
                text = stringResource(R.string.first_rules_screen_title),
                textAlign = TextAlign.Center
            )
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(word.size),
            modifier = Modifier.padding(vertical = dimensionResource(R.dimen.padding_medium)),
            contentPadding = PaddingValues(dimensionResource(R.dimen.padding_extra_small))
        ) {
            items(word.size) { index ->
                SquareView(
                    state = word[index], {}
                )
            }
        }
        Column(modifier = Modifier.padding(dimensionResource(R.dimen.padding_extra_small))) {
            hintMap.forEach { it ->
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.weight(1f)) { SquareView(it.key) { } }
                    Box(
                        modifier = Modifier
                            .weight(5f)
                            .padding(start = dimensionResource(R.dimen.padding_medium))
                    ) { Text(text = stringResource(it.value)) }
                }
            }
        }
    }
}

@Composable
fun Second() {
    Column(
        modifier = Modifier
            .padding(dimensionResource(R.dimen.padding_medium))
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween

    ) {
        Text(
            text = stringResource(R.string.second_rules_screen_title),
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.size(dimensionResource(R.dimen.padding_medium)))
        Image(
            painter = painterResource(R.drawable.field),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(0.7f),
            contentScale = ContentScale.Fit
        )
        Spacer(Modifier.size(dimensionResource(R.dimen.padding_medium)))
        Text(text = stringResource(R.string.second_rules_screen_text), textAlign = TextAlign.Center)
        Spacer(Modifier.size(dimensionResource(R.dimen.padding_medium)))
        Image(
            painter = painterResource(R.drawable.keyboard),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(0.7f),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
fun Third() {
    Fifth()
}


@Composable
fun Fourth() {
    Fifth()
}

@Composable
fun Fifth() {
    Box(Modifier.padding(dimensionResource(R.dimen.padding_medium))) {
        Text(
            text = stringResource(R.string.soon),
            textAlign = TextAlign.Center
        )
    }
}


@Preview(showBackground = true, widthDp = 427, heightDp = 952)
@Composable
fun GameScreenPreview() {
    WordleTheme {
        ListOfRules(0)
    }
}