package ru.cryhards.brootkiddie.cleanup.ui

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import ru.cryhards.brootkiddie.cleanup.Assets
import ru.cryhards.brootkiddie.cleanup.Core

@Suppress("FunctionName")
/**
 * Created with love by luna_koly on 04.02.2018.
 */
object UI {

    fun RectButton(text: String): TextButton {
        val style = TextButton.TextButtonStyle()
        style.font = Assets.fonts.HACK_REGULAR
        val butt = RectButton(text, style)

        val pix = Pixmap(50, 50, Pixmap.Format.RGB888)
        pix.setColor(Color.BLACK)
        pix.fill()

        butt.label.style.background = Image(Texture(pix)).drawable
        butt.width *= 1.2f
        butt.height *= 1.2f

        butt.addListener(object : ClickListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                butt.shader = Assets.shaders.GLITCH
                Core.instance.noize.loop()
                return true
            }

            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                butt.shader = null
                Core.instance.noize.stop()
            }
        })
        return butt
    }

}