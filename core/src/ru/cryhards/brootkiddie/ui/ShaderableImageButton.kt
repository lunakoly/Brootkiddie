package ru.cryhards.brootkiddie.ui

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton

/**
 * ImageButton UI element template that can accept shader effects
 */
class ShaderableImageButton(style: ImageButtonStyle) : ImageButton(style) {
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