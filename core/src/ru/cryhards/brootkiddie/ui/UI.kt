package ru.cryhards.brootkiddie.ui

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import ru.cryhards.brootkiddie.Assets
import ru.cryhards.brootkiddie.Core


@Suppress("FunctionName")
/**
 * Builder used for creating specific ui elements
 */
object UI {

    /**
     * Returns text button with glitch effect on touchDown
     */
    fun GlitchTextButton(text: String): ShaderableButton {
        val style = TextButton.TextButtonStyle()
        style.font = Assets.fonts.HACK_REGULAR
        val butt = ShaderableButton(text, style)

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


    /**
     * Returns image button with glitch effect on touchDown
     */
    fun GlitchImageButton(path: String): ShaderableImageButton {
        val style = com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle()
        val icon = TextureRegionDrawable( TextureRegion(Texture(path)) )
        style.imageUp = icon

        val butt = ShaderableImageButton(style)

        val pix = Pixmap(50, 50, Pixmap.Format.RGBA8888)
        pix.setColor(Color.BLACK)
        pix.fill()

        butt.background = Image(Texture(pix)).drawable

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


    /**
     * Returns label with glitch effect on touchDown
     */
    fun GlitchLabel(text: String): ShaderableLabel {
        val style = Label.LabelStyle()
        style.font = Assets.fonts.ROBOTOx2
        val lbl = ShaderableLabel(text, style)

        val pix = Pixmap(50, 50, Pixmap.Format.RGB888)
        pix.setColor(Color.BLACK)
        pix.fill()

        lbl.style.background = Image(Texture(pix)).drawable
        lbl.width *= 1.2f
        lbl.height *= 1.2f

        lbl.addListener(object : ClickListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                lbl.shader = Assets.shaders.GLITCH
                Core.instance.noize.loop()
                return true
            }

            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                lbl.shader = null
                Core.instance.noize.stop()
            }
        })

        return lbl
    }


    /**
     * Returns console with glitch effect on touchDown
     */
    fun Console(text: String): ShaderableConsole {
        val style = TextField.TextFieldStyle()
        style.font = Assets.fonts.ROBOTO
        val con = ShaderableConsole(text, style)

        val pix = Pixmap(50, 50, Pixmap.Format.RGB888)
        pix.setColor(Color.BLACK)
        pix.fill()

        con.style.background = Image(Texture(pix)).drawable

        con.width = 500f
        con.height = 230f

        // ! IMPORTANT
        con.style.fontColor = Color.WHITE
        con.isDisabled = true

        con.addListener(object : ClickListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                con.shader = Assets.shaders.GLITCH
                Core.instance.noize.loop()
                return true
            }

            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                con.shader = null
                Core.instance.noize.stop()
            }
        })

        return con
    }

}