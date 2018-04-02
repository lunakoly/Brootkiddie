package ru.cryhards.brootkiddie.ui

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.utils.Drawable
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import ru.cryhards.brootkiddie.Assets
import ru.cryhards.brootkiddie.screens.globalmap.GlobalMap


@Suppress("FunctionName")
/**
 * Builder used for creating specific ui elements
 */
object UI {

    /**
     * Returns drawable made of specified color
     */
    fun colorToDrawable(color: Color): Drawable {
        val pix = Pixmap(50, 50, Pixmap.Format.RGB888)
        pix.setColor(color)
        pix.fill()
        return Image(Texture(pix)).drawable
    }


    /**
     * Returns text button with glitch effect on touchDown
     */
    fun GlitchTextButton(text: String): ShaderableButton {
        val style = TextButton.TextButtonStyle()
        style.font = Assets.Fonts.HACK_REGULAR
        val butt = ShaderableButton(text, style)

        butt.label.style.background = colorToDrawable(Color.BLACK)
        butt.width *= 1.2f
        butt.height *= 1.2f

        butt.addListener(object : ClickListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                butt.shader = Assets.Shaders.GLITCH
                Assets.Sounds.NOIZE.loop()
                return true
            }

            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                butt.shader = null
                Assets.Sounds.NOIZE.stop()
            }
        })

        return butt
    }


    /**
     * Returns compact text button with glitch effect on touchDown
     */
    fun GlitchTextButtonCompact(text: String): ShaderableButton {
        val style = TextButton.TextButtonStyle()
        style.font = Assets.Fonts.HACK_COMPACT
        val butt = ShaderableButton(text, style)

        butt.label.style.background = colorToDrawable(Color.BLACK)
        butt.width *= 1.2f
        butt.height *= 1.2f

        butt.addListener(object : ClickListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                butt.shader = Assets.Shaders.GLITCH
                Assets.Sounds.NOIZE.loop()
                return true
            }

            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                butt.shader = null
                Assets.Sounds.NOIZE.stop()
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
        style.up = icon
        val butt = ShaderableImageButton(style)
        butt.image.setFillParent(true)

        butt.width *= 1.3f
        butt.height *= 1.3f


        butt.addListener(object : ClickListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                butt.shader = Assets.Shaders.GLITCH
                Assets.Sounds.NOIZE.loop()
                return true
            }

            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                butt.shader = null
                Assets.Sounds.NOIZE.stop()
            }
        })

        return butt
    }


    /**
     * Returns label with glitch effect on touchDown
     */
    fun GlitchLabel(text: String): ShaderableLabel {
        val style = Label.LabelStyle()
        style.font = Assets.Fonts.ROBOTOx2
        val lbl = ShaderableLabel(text, style)

        lbl.style.background = colorToDrawable(Color.BLACK)
        lbl.width *= 1.2f
        lbl.height *= 1.2f

        lbl.addListener(object : ClickListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                lbl.shader = Assets.Shaders.GLITCH
                Assets.Sounds.NOIZE.loop()
                return true
            }

            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                lbl.shader = null
                Assets.Sounds.NOIZE.stop()
            }
        })

        return lbl
    }


    /**
     * Returns console with glitch effect on touchDown
     */
    fun GlitchConsole(text: String): ShaderableConsole {
        val style = TextField.TextFieldStyle()
        style.font = Assets.Fonts.ROBOTO
        val con = ShaderableConsole(text, style)

        con.style.background = colorToDrawable(Color.BLACK)

        con.width = 500f
        con.height = 230f

        // ! IMPORTANT
        con.style.fontColor = Color.WHITE
        con.isDisabled = true

        con.addListener(object : ClickListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                con.shader = Assets.Shaders.GLITCH
                Assets.Sounds.NOIZE.loop()
                return true
            }

            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                con.shader = null
                Assets.Sounds.NOIZE.stop()
            }
        })

        return con
    }


    /**
     * Returns label with common style
     */
    fun StaticLabel(text: String): ShaderableLabel {
        val style = Label.LabelStyle()
        style.font = Assets.Fonts.ROBOTOx2
        val lbl = ShaderableLabel(text, style)

        lbl.style.background = colorToDrawable(Color.BLACK)
        lbl.width *= 1.2f
        lbl.height *= 1.2f
        return lbl
    }


    /**
     * Returns button with common style
     */

    fun StaticTextButton(text: String) : ShaderableButton{
        val style = TextButton.TextButtonStyle()
        style.font = Assets.Fonts.ROBOTOx2
        val but = ShaderableButton(text, style)

        val pixUp = colorToDrawable(Color.BLACK)
        val pixDown = colorToDrawable(Color.LIGHT_GRAY)

        but.label.style.background = pixUp
        but.width *= 1.2f
        but.height *= 1.2f

        but.addListener(object : ClickListener(){
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                but.label.style.background = pixDown
                return true
            }

            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                but.label.style.background = pixUp
            }
        })

        return but
    }

    /**
     * Gives quick access to main ui
     */

    var console: ShaderableConsole? = null
    var globalMap: GlobalMap? = null
}