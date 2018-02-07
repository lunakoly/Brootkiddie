package ru.cryhards.brootkiddie.ui

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.ui.Image

/**
 * Represents Image with dispose method
 */
open class ImageActor(private val texture: Texture) : Image(texture) {
    constructor(path: String) : this(Texture(path))
    fun dispose() = texture.dispose()
}