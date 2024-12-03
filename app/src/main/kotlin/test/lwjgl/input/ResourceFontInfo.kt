package test.lwjgl.input

import sp.kx.lwjgl.entity.font.FontInfo
import java.io.InputStream
import java.net.URI

internal class ResourceFontInfo(
    private val uri: URI,
    override val height: Float,
) : FontInfo {
    override val id: String = uri.toString()

    override fun getInputStream(): InputStream {
        return Thread.currentThread().contextClassLoader.getResourceAsStream(uri.toString())
            ?: error("Resource by uri: $uri does not exist!")
    }
}
