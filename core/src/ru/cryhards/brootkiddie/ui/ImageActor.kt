package ru.cryhards.brootkiddie.ui

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.badlogic.gdx.scenes.scene2d.ui.Image

/**
 * Represents Image with dispose method
 */
open class ImageActor(private val texture: Texture) : Image(texture) {
    constructor(path: String) : this(Texture(path))
    fun dispose() = texture.dispose()

    /**
     * Use for adding shader effects
     */
    var shader: ShaderProgram? = null

    override fun draw(batch: Batch?, parentAlpha: Float) {
        if (shader != null) {
            val old = batch?.shader
            batch?.shader = shader
            super.draw(batch, parentAlpha)
            batch?.shader = old

        } else
            super.draw(batch, parentAlpha)
    }
}