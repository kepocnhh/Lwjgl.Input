package test.lwjgl.input

import sp.kx.lwjgl.engine.Engine
import sp.kx.math.sizeOf

fun main() {
    Engine.run(::InputEngineLogics, size = sizeOf(640, 480), title = "Input")
}
