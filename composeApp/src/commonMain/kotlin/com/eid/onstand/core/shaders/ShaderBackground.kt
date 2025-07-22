package com.eid.onstand.core.shaders

import androidx.compose.animation.core.withInfiniteAnimationFrameMillis
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import com.eid.onstand.core.utils.round

@Composable
fun Modifier.shaderBackground(
    shader: Shader,
    speed: Float = 1f,
    fallback: () -> Brush = {
        Brush.horizontalGradient(listOf(Color.Transparent, Color.Transparent))
    },
): Modifier {
    val runtimeEffect = remember(shader) { buildEffect(shader) }
    var size: Size by remember { mutableStateOf(Size(-1f, -1f)) }
    val speedModifier = shader.speedModifier

    val time by if (runtimeEffect.supported) {
        var startMillis = remember(shader) { -1L }
        produceState(0f, speedModifier) {
            while (true) {
                withInfiniteAnimationFrameMillis {
                    if (startMillis < 0) startMillis = it
                    value = ((it - startMillis) / 16.6f) / 10f
                }
            }
        }
    } else {
        mutableStateOf(-1f)
    }

    return this then Modifier.onGloballyPositioned {
        size = Size(it.size.width.toFloat(), it.size.height.toFloat())
    }.drawBehind {
        runtimeEffect.update(
            shader,
            (time * speed * speedModifier).round(3),
            size.width,
            size.height
        )
        if (runtimeEffect.ready) {
            drawRect(brush = runtimeEffect.build())
        } else {
            drawRect(brush = fallback())
        }
    }
}