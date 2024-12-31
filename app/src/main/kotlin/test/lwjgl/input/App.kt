package test.lwjgl.input

import sp.kx.lwjgl.engine.Engine
import sp.kx.math.sizeOf

fun main() {
    Engine.run(
        title = "Input",
        supplier = ::InputEngineLogics,
        size = sizeOf(640, 480),
        defaultFontName = "JetBrainsMono.ttf",
//        defaultFontName = "OpenSans.ttf",
    )
}
