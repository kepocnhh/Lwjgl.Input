package test.lwjgl.input

import sp.kx.lwjgl.entity.font.FontAgent
import sp.kx.lwjgl.entity.font.FontInfo
import sp.kx.math.measure.Measure
import java.net.URI

private data class CompositeKey(
    val uri: URI,
    val height: Float,
)

private val infos = mutableMapOf<CompositeKey, FontInfo>()

internal fun FontAgent.getFontInfo(uri: URI, height: Double, measure: Measure<Double, Double>): FontInfo {
    return getFontInfo(uri = uri, height = measure.transform(height).toFloat())
}

internal fun FontAgent.getFontInfo(uri: URI, height: Float): FontInfo {
    return infos.getOrPut(CompositeKey(uri = uri, height = height)) {
        ResourceFontInfo(
            uri = uri,
            height = height,
        )
    }
}
