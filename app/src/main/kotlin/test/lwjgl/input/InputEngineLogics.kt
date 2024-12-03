package test.lwjgl.input

import sp.kx.lwjgl.engine.Engine
import sp.kx.lwjgl.engine.EngineInputCallback
import sp.kx.lwjgl.engine.EngineLogics
import sp.kx.lwjgl.entity.Canvas
import sp.kx.lwjgl.entity.Color
import sp.kx.lwjgl.entity.copy
import sp.kx.lwjgl.entity.font.FontInfo
import sp.kx.lwjgl.entity.input.KeyboardButton
import sp.kx.math.Point
import sp.kx.math.measure.frequency
import sp.kx.math.measure.measureOf
import sp.kx.math.offsetOf
import sp.kx.math.plus
import sp.kx.math.pointOf
import sp.kx.math.sizeOf
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

    private fun onRenderGrid(
        canvas: Canvas,
        fontInfo: FontInfo,
    ) {
        val max = 24
        val color = Color.Green.copy(alpha = 0.5f)
        (1..max).forEach { number ->
            canvas.texts.draw(
                color = color,
                info = fontInfo,
                pointTopLeft = pointOf(x = number, y = 0),
                text = "$number",
                measure = measure,
            )
            canvas.texts.draw(
                color = color,
                info = fontInfo,
                pointTopLeft = pointOf(x = 0, y = number),
                text = "$number",
                measure = measure,
            )
            canvas.vectors.draw(
                color = color,
                vector = pointOf(x = 0, y = number) + pointOf(x = max, y = number),
                lineWidth = 0.1,
                measure = measure,
            )
            canvas.vectors.draw(
                color = color,
                vector = pointOf(x = number, y = 0) + pointOf(x = number, y = max),
                lineWidth = 0.1,
                measure = measure,
            )
        }
    }

    private fun getText(button: KeyboardButton): String {
        return when (button) {
            KeyboardButton.Number0 -> "0"
            KeyboardButton.Number1 -> "1"
            KeyboardButton.Number2 -> "2"
            KeyboardButton.Number3 -> "3"
            KeyboardButton.Number4 -> "4"
            KeyboardButton.Number5 -> "5"
            KeyboardButton.Number6 -> "6"
            KeyboardButton.Number7 -> "7"
            KeyboardButton.Number8 -> "8"
            KeyboardButton.Number9 -> "9"
            KeyboardButton.Minus -> "-"
            KeyboardButton.Equal -> "="
            else -> button.name
        }
    }

    private fun drawButton(
        canvas: Canvas,
        fontInfo: FontInfo,
        pointTopLeft: Point,
        height: Double = 1.0,
        width: Double,
        button: KeyboardButton,
        text: String = getText(button = button),
    ) {
        val isPressed = engine.input.keyboard.isPressed(button)
        val fontHeight = measure.units(fontInfo.height.toDouble())
        val textWidth = measure.units(engine.fontAgent.getTextWidth(fontInfo, text))
        canvas.texts.draw(
            info = fontInfo,
            color = if (isPressed) Color.Yellow else Color.Green,
            pointTopLeft = pointOf(
                x = pointTopLeft.x + width / 2 - textWidth / 2,
                y = pointTopLeft.y + height / 2 - fontHeight / 2,
            ),
            text = text,
            measure = measure,
        )
        if (isPressed) {
            canvas.polygons.drawRectangle(
                color = Color.Yellow,
                pointTopLeft = pointTopLeft,
                size = sizeOf(
                    width = width,
                    height = height,
                ),
                lineWidth = 0.1,
                measure = measure,
            )
        }
        val passed = engine.input.keyboard.whenPressed(button)?.let { whenPressed ->
            engine.property.time.b - whenPressed
        }
        if (passed != null) {
            val f = engine.fontAgent.getFontInfo(
                uri = URI("JetBrainsMono.ttf"),
                height = 0.75,
                measure = measure,
            )
            val t = String.format("%02d:%02d", passed.inWholeSeconds, (passed.inWholeMilliseconds % 1000) / 10)
            val fh = measure.units(f.height.toDouble())
            val tw = measure.units(engine.fontAgent.getTextWidth(f, t))
            canvas.texts.draw(
                info = f,
                color = Color.Yellow,
                pointTopLeft = pointOf(
                    x = pointTopLeft.x,
                    y = pointTopLeft.y + height,
                ),
                text = t,
                measure = measure,
            )
        }
    }

    private fun onRenderInput(
        canvas: Canvas,
        fontInfo: FontInfo,
    ) {
        listOf(
            listOf(KeyboardButton.Q, KeyboardButton.W, KeyboardButton.E, KeyboardButton.R, KeyboardButton.T, KeyboardButton.Y, KeyboardButton.U, KeyboardButton.I, KeyboardButton.O, KeyboardButton.P),
            listOf(KeyboardButton.A, KeyboardButton.S, KeyboardButton.D, KeyboardButton.F, KeyboardButton.G, KeyboardButton.H, KeyboardButton.J, KeyboardButton.K, KeyboardButton.L),
            listOf(KeyboardButton.Z, KeyboardButton.X, KeyboardButton.C, KeyboardButton.V, KeyboardButton.B, KeyboardButton.N, KeyboardButton.M)
        ).forEachIndexed { dY, row ->
            row.forEachIndexed { dX, button ->
                drawButton(
                    canvas = canvas,
                    fontInfo = fontInfo,
                    pointTopLeft = pointOf(
                        x = dX * 2,
                        y = dY * 2,
                    ) + offsetOf(dX = 2.0, dY = 2.0),
                    width = 1.0,
                    button = button,
                )
            }
        }
    }

    override fun onRender(canvas: Canvas) {
        val fontInfo = engine.fontAgent.getFontInfo(
            uri = URI("JetBrainsMono.ttf"),
            height = 1.0,
            measure = measure,
        )
//        canvas.texts.draw(
//            info = fontInfo,
//            pointTopLeft = Point.Center,
//            color = Color.Green,
//            text = String.format("%6.2f", engine.property.time.frequency()),
//            measure = measure,
//        )
        onRenderGrid(
            canvas = canvas,
            fontInfo = fontInfo,
        )
        onRenderInput(
            canvas = canvas,
            fontInfo = fontInfo,
        )
    }

    override fun shouldEngineStop(): Boolean {
        return ::shouldEngineStopUnit.isInitialized
    }

    companion object {
        private const val Tag = "[Input|Logics]"
    }
}
