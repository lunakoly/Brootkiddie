package ru.cryhards.brootkiddie.ui

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import ru.cryhards.brootkiddie.Assets
import ru.cryhards.brootkiddie.items.Item
import ru.cryhards.brootkiddie.ui.items.ItemBlock


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
                Assets.sounds.NOIZE.loop()
                return true
            }

            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                butt.shader = null
                Assets.sounds.NOIZE.stop()
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
                butt.shader = Assets.shaders.GLITCH
                Assets.sounds.NOIZE.loop()
                return true
            }

            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                butt.shader = null
                Assets.sounds.NOIZE.stop()
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
                Assets.sounds.NOIZE.loop()
                return true
            }

            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                lbl.shader = null
                Assets.sounds.NOIZE.stop()
            }
        })

        return lbl
    }


    /**
     * Returns console with glitch effect on touchDown
     */
    fun GlitchConsole(text: String): ShaderableConsole {
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
                Assets.sounds.NOIZE.loop()
                return true
            }

            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                con.shader = null
                Assets.sounds.NOIZE.stop()
            }
        })

        return con
    }


    /**
     * Returns label with common style
     */
    fun StaticLabel(text: String): ShaderableLabel {
        val style = Label.LabelStyle()
        style.font = Assets.fonts.ROBOTOx2
        val lbl = ShaderableLabel(text, style)

        val pix = Pixmap(50, 50, Pixmap.Format.RGB888)
        pix.setColor(Color.BLACK)
        pix.fill()

        lbl.style.background = Image(Texture(pix)).drawable
        lbl.width *= 1.2f
        lbl.height *= 1.2f
        return lbl
    }


    /**
     * Returns textarea that increasess its height if needed
     */
    fun ExtendingTextArea(text: String): ExtendingTextArea {
        val style = TextField.TextFieldStyle()
        style.font = Assets.fonts.ROBOTOx2
        return ExtendingTextArea(text, style)
    }


    /**
     * Returns ItemBlock debug example
     */
    fun emptyItem(): ItemBlock {
        val item = Item("<Name>", "<Some info about item>", Item.Type.SCRIPT)
        return ItemBlock(item, Texture("img/ui/empty.png"))
    }


    /**
     * Returns ItemBlock debug example 2
     */
    fun loremItem(): ItemBlock {
        val item = Item(
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus fermentum at metus at dapibus.",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus fermentum at metus at dapibus. Morbi consequat in eros nec rutrum. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Morbi porttitor, metus eget luctus pretium, ligula est sollicitudin risus, id accumsan justo enim eu ante. Aliquam sit amet magna lacus. In commodo rhoncus quam quis faucibus. Sed et odio sit amet tellus consequat egestas id vitae diam. Cras in risus velit. Vestibulum eget tincidunt eros. Integer congue massa vitae nibh interdum, a suscipit eros iaculis. Nullam facilisis consectetur lectus, id venenatis turpis mollis ac. Suspendisse eleifend nunc rutrum sem scelerisque accumsan. Mauris nec vestibulum mi.",
                Item.Type.SCRIPT)

        val block = ItemBlock(item, Texture("img/ui/back.png"))
        val logger = UI.GlitchTextButton("LOL")

        logger.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                Gdx.app.log("test", "lol")
            }
        })

        block.actions.add(logger)
        return block
    }
}