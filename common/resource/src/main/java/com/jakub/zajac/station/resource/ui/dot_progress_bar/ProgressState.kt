package com.jakub.zajac.station.resource.ui.dot_progress_bar

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableFloatStateOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.PI


@Stable
class ProgressState(
    private val dotsNumber: Int,
    private val animationDuration: Int,
) {
    private val animationValues: List<MutableState<Float>> = List(dotsNumber) {
        mutableFloatStateOf(0f)
    }

    operator fun get(index: Int) = animationValues[index].value

    fun start(scope: CoroutineScope) {
        repeat(dotsNumber) { index ->
            scope.launch {
                animate(
                    initialValue = 0f,
                    targetValue = (2f * PI).toFloat(),
                    animationSpec = infiniteRepeatable(
                        animation = keyframes {
                            durationMillis = animationDuration
                            0f at 0
                            (.5 * PI).toFloat() at 2 * animationDuration / 10
                            PI.toFloat() at 3 * animationDuration / 10
                            (1.5 * PI).toFloat() at 4 * animationDuration / 10
                            (2f * PI).toFloat() at 6 * animationDuration / 10
                        },
                        repeatMode = RepeatMode.Restart,
                        initialStartOffset = StartOffset(offsetMillis = 100 * index)
                    ),
                ) { value, _ ->
                    animationValues[index].value = value
                }
            }
        }
    }
}