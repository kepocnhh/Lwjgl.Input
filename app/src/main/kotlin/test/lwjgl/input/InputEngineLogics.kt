package test.lwjgl.input

import sp.kx.lwjgl.engine.Engine
import sp.kx.lwjgl.engine.EngineInputCallback
import sp.kx.lwjgl.engine.EngineLogics
import sp.kx.lwjgl.entity.Canvas
import sp.kx.lwjgl.entity.Color
import sp.kx.lwjgl.entity.input.KeyboardButton
import sp.kx.math.Point
import sp.kx.math.measure.frequency
import sp.kx.math.measure.measureOf
import java.net.URI

internal class InputEngineLogics(
    private val engine: Engine,
) : EngineLogics {
    private lateinit var shouldEngineStopUnit: Unit
    override val inputCallback = object : EngineInputCallback {
        override fun onKeyboardButton(button: KeyboardButton, isPressed: Boolean) {
            if (isPressed) return
            when (button) {
                KeyboardButton.Escape -> {
                    shouldEngineStopUnit = Unit
                }
                else -> Unit
            }
        }
    }
    private val measure = measureOf(24.0)

    override fun onRender(canvas: Canvas) {
        val fontInfo = engine.fontAgent.getFontInfo(
            uri = URI("JetBrainsMono.ttf"),
            height = 1.0,
            measure = measure,
        )
        canvas.texts.draw(
            info = fontInfo,
            pointTopLeft = Point.Center,
            color = Color.Green,
            text = String.format("%6.2f", engine.property.time.frequency()),
            measure = measure,
        )
        // todo
    }

    override fun shouldEngineStop(): Boolean {
        return ::shouldEngineStopUnit.isInitialized
    }

    companion object {
        private const val Tag = "[Input|Logics]"
    }
}
