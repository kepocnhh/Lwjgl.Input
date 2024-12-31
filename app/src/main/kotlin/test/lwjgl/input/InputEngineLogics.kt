package test.lwjgl.input

import sp.kx.lwjgl.engine.Engine
import sp.kx.lwjgl.engine.EngineInputCallback
import sp.kx.lwjgl.engine.EngineLogics
import sp.kx.lwjgl.entity.Canvas
import sp.kx.lwjgl.entity.Color
import sp.kx.lwjgl.entity.input.KeyboardButton
import sp.kx.math.Point
import sp.kx.math.measure.measureOf
import sp.kx.math.offsetOf
import sp.kx.math.plus
import sp.kx.math.pointOf
import sp.kx.math.sizeOf

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
            KeyboardButton.Escape -> "Esc"
            KeyboardButton.Control -> "Ctrl"
            else -> button.name
        }
    }

    private fun drawButton(
        canvas: Canvas,
        pointTopLeft: Point,
        height: Double = 1.0,
        width: Double,
        button: KeyboardButton,
        text: String = getText(button = button),
    ) {
        val isPressed = engine.input.keyboard.isPressed(button)
        val fontHeight = 1.0
        val textWidth = canvas.texts.getTextWidth(fontHeight = fontHeight, text = text, measure = measure)
        canvas.texts.draw(
            fontHeight = fontHeight,
            color = if (isPressed) Color.Yellow else Color.Green,
            pointTopLeft = pointOf(
                x = pointTopLeft.x + width / 2 - textWidth / 2,
                y = pointTopLeft.y + height / 2 - fontHeight / 2,
            ),
            text = text,
            measure = measure,
        )
        canvas.polygons.drawRectangle(
            color = if (isPressed) Color.Yellow else Color.Green,
            pointTopLeft = pointTopLeft,
            size = sizeOf(
                width = width,
                height = height,
            ),
            lineWidth = if (isPressed) 0.1 else 0.05,
            measure = measure,
        )
        val passed = engine.input.keyboard.whenPressed(button)?.let { whenPressed ->
            engine.property.time.b - whenPressed
        }
        if (passed != null) {
            canvas.texts.draw(
                fontHeight = 0.75,
                color = Color.Yellow,
                pointTopLeft = pointOf(
                    x = pointTopLeft.x,
                    y = pointTopLeft.y + height,
                ),
                text = String.format("%02d:%d", passed.inWholeSeconds, (passed.inWholeMilliseconds % 1000) / 100),
                measure = measure,
            )
        }
    }

    override fun onRender(canvas: Canvas) {
        listOf(
            KeyboardButton.Number1,
            KeyboardButton.Number2,
            KeyboardButton.Number3,
            KeyboardButton.Number4,
            KeyboardButton.Number5,
            KeyboardButton.Number6,
            KeyboardButton.Number7,
            KeyboardButton.Number8,
            KeyboardButton.Number9,
            KeyboardButton.Number0,
            KeyboardButton.Minus,
            KeyboardButton.Equal,
        ).forEachIndexed { index, button ->
            drawButton(
                canvas = canvas,
                pointTopLeft = pointOf(
                    x = index * 2,
                    y = 0,
                ) + offsetOf(dX = 2.0, dY = 2.0),
                width = 1.0,
                button = button,
            )
        }
        listOf(
            listOf(KeyboardButton.Q, KeyboardButton.W, KeyboardButton.E, KeyboardButton.R, KeyboardButton.T, KeyboardButton.Y, KeyboardButton.U, KeyboardButton.I, KeyboardButton.O, KeyboardButton.P),
            listOf(KeyboardButton.A, KeyboardButton.S, KeyboardButton.D, KeyboardButton.F, KeyboardButton.G, KeyboardButton.H, KeyboardButton.J, KeyboardButton.K, KeyboardButton.L),
            listOf(KeyboardButton.Z, KeyboardButton.X, KeyboardButton.C, KeyboardButton.V, KeyboardButton.B, KeyboardButton.N, KeyboardButton.M)
        ).forEachIndexed { dY, row ->
            row.forEachIndexed { dX, button ->
                drawButton(
                    canvas = canvas,
                    pointTopLeft = pointOf(
                        x = dX * 2,
                        y = dY * 2,
                    ) + offsetOf(dX = 2.0, dY = 4.0),
                    width = 1.0,
                    button = button,
                )
            }
        }
        drawButton(
            canvas = canvas,
            pointTopLeft = pointOf(
                x = 17,
                y = 8,
            ),
            width = 2.0,
            height = 2.0,
            button = KeyboardButton.Up,
            text = "/\\"
        )
        drawButton(
            canvas = canvas,
            pointTopLeft = pointOf(
                x = 14,
                y = 10,
            ),
            width = 2.0,
            height = 2.0,
            button = KeyboardButton.Left,
            text = "<-"
        )
        drawButton(
            canvas = canvas,
            pointTopLeft = pointOf(
                x = 20,
                y = 10,
            ),
            width = 2.0,
            height = 2.0,
            button = KeyboardButton.Right,
            text = "->"
        )
        drawButton(
            canvas = canvas,
            pointTopLeft = pointOf(
                x = 17,
                y = 12,
            ),
            width = 2.0,
            height = 2.0,
            button = KeyboardButton.Down,
            text = "\\/"
        )
        listOf(
            KeyboardButton.Escape,
            KeyboardButton.Tab,
            KeyboardButton.Shift,
        ).forEachIndexed { index, button ->
            drawButton(
                canvas = canvas,
                pointTopLeft = pointOf(
                    x = 2,
                    y = 10 + index * 2,
                ),
                width = 3.0,
                button = button,
            )
        }
        listOf(
            KeyboardButton.Control,
            KeyboardButton.Alt,
            KeyboardButton.Super,
        ).forEachIndexed { index, button ->
            drawButton(
                canvas = canvas,
                pointTopLeft = pointOf(
                    x = 2 + index * 4,
                    y = 16,
                ),
                width = 3.0,
                button = button,
            )
        }
        drawButton(
            canvas = canvas,
            pointTopLeft = pointOf(
                x = 14,
                y = 16,
            ),
            width = 7.0,
            button = KeyboardButton.Space,
        )
        drawButton(
            canvas = canvas,
            pointTopLeft = pointOf(
                x = 20,
                y = 6,
            ),
            width = 3.0,
            button = KeyboardButton.Backspace,
            text = "<x"
        )
        drawButton(
            canvas = canvas,
            pointTopLeft = pointOf(
                x = 20,
                y = 8,
            ),
            width = 3.0,
            button = KeyboardButton.Enter,
        )
    }

    override fun shouldEngineStop(): Boolean {
        return ::shouldEngineStopUnit.isInitialized
    }

    companion object {
        private const val Tag = "[Input|Logics]"
    }
}
