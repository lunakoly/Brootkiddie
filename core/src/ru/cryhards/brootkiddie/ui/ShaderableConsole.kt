package ru.cryhards.brootkiddie.ui

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.badlogic.gdx.scenes.scene2d.ui.TextArea

/**
 * GlitchConsole UI element template that can accept shader effects
 */
class ShaderableConsole(text: String, style: TextFieldStyle) : TextArea(text, style) {
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

    /**
     * Prints message to the console
     */
    fun log(message: String): ShaderableConsole {
        val lines = getText().split("\n").toMutableList()

        if (lines.size >= 5)
            lines.removeAt(0)

        lines.add("> " + message)
        setText(lines.joinToString("\n"))
        return this
    }
}