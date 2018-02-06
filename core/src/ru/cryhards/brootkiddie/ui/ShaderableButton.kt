package ru.cryhards.brootkiddie.ui

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.badlogic.gdx.scenes.scene2d.ui.TextButton

/**
 * TextButton UI element template that can accept shader effects
 */
class ShaderableButton(text: String, style: TextButtonStyle) : TextButton(text, style) {
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