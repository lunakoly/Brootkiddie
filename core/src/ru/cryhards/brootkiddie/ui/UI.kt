package ru.cryhards.brootkiddie.ui

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.utils.Drawable
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import ru.cryhards.brootkiddie.Assets
import ru.cryhards.brootkiddie.Environment
import ru.cryhards.brootkiddie.items.Item
import ru.cryhards.brootkiddie.items.Malware
import ru.cryhards.brootkiddie.items.Script
import ru.cryhards.brootkiddie.screens.market.MarketItems


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
     * Returns popup dialog with glitch effect
     */
    fun GlitchPopupDialog(caption: String, callback: (String) -> Unit, text: String = ""): ShaderablePopupTextInput {
        val style = Label.LabelStyle()
        style.font = Assets.Fonts.ROBOTOx2

        val dialog = object : ShaderablePopupTextInput(caption, WindowStyle(Assets.Fonts.ROBOTOx2, Color.WHITE, colorToDrawable(Color.BLACK))) {
            override fun result(`object`: Any?) {
                super.result(`object`)
                callback((`object` as TextArea).text)
            }
        }
        dialog.align(Align.top)
        dialog.setOrigin(Align.top)

        dialog.row()

        dialog.style.background = colorToDrawable(Color.BLACK)

        val ta = TextArea(text, TextField.TextFieldStyle(Assets.Fonts.ROBOTO, Color.WHITE, Image(Texture("img/ui/back.png")).drawable, Image(Texture("img/ui/back.png")).drawable, colorToDrawable(Color.BLACK)))
        ta.height *= 2



        dialog.add(ta)
        dialog.row()
        dialog.button(UI.GlitchTextButton("OK"), ta)
        dialog.row()

        /*dialog.addListener(object : ClickListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                dialog.shader = Assets.Shaders.GLITCH
                Assets.Sounds.NOIZE.loop()
                return true
            }

            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                dialog.shader = null
                Assets.Sounds.NOIZE.stop()
            }
        })*/

        dialog.pack()

        return dialog
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
     * Returns Item debug example
     */
    fun emptyItem() = Script(
            "<Name>",
            "<Some info about item>",
            Texture("img/ui/empty.png"), 1f)


    /**
     * Returns Item debug example 2
     */
    fun loremItem(): Script {
        val logger = UI.GlitchTextButton("LOL")

        logger.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                Gdx.app.log("test", "lol")
            }
        })

        val block = Script(
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus fermentum at metus at dapibus.",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus fermentum at metus at dapibus. Morbi consequat in eros nec rutrum. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Morbi porttitor, metus eget luctus pretium, ligula est sollicitudin risus, id accumsan justo enim eu ante. Aliquam sit amet magna lacus. In commodo rhoncus quam quis faucibus. Sed et odio sit amet tellus consequat egestas id vitae diam. Cras in risus velit. Vestibulum eget tincidunt eros. Integer congue massa vitae nibh interdum, a suscipit eros iaculis. Nullam facilisis consectetur lectus, id venenatis turpis mollis ac. Suspendisse eleifend nunc rutrum sem scelerisque accumsan. Mauris nec vestibulum mi.",
                Texture("img/ui/back.png"), 1f)

        block.actions.add(logger)
        return block
    }



    /**
     * Returns ItemBlock with Player's malware inside
     */
    fun malwareItem(malware: Malware): Item {
        val logger = UI.GlitchTextButton("KUKAREKU v log")

        logger.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                Gdx.app.log("ahaha", "rabotaet")
            }
        })

        malware.actions.add(logger)

        val setGlobalMapMalware = if (Environment.activeMalware == malware) UI.GlitchTextButton("DEACTIVATE") else UI.GlitchTextButton("ACTIVATE")
        setGlobalMapMalware.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                if (Environment.activeMalware == malware) {
                    Gdx.app.log("activeMalware", "deactivated")
                    Environment.UI.console?.log("${malware.name} deactivated")
                    Environment.activeMalware = null
                    setGlobalMapMalware.setText("ACTIVATE")
                } else {
                    Gdx.app.log("activeMalware", "activated")
                    Environment.activeMalware = malware
                    Environment.UI.console?.log("${malware.name} activated")
                    setGlobalMapMalware.setText("DEACTIVATE")
                }
            }
        })

        malware.actions.add(setGlobalMapMalware)

        return malware
    }

    /**
     * Returns ItemBlock with market's item inside
     */
    fun marketItem(item: Item): Item {
        val buybutt = UI.GlitchTextButton("BUY")

        buybutt.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                MarketItems.buy(item)
            }
        })

        item.actions.add(buybutt)

        return item
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
}