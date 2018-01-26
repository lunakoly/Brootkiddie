package ru.cryhards.brootkiddie

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.ui.Image

/**
 * Created with love by luna_koly on 21.01.2018.
 */
class ImageActor(private val texture: Texture) : Image(texture) {
    constructor(path: String) : this(Texture(path))
    fun dispose() = texture.dispose()
}