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
import ru.cryhards.brootkiddie.Assets
import ru.cryhards.brootkiddie.items.Item
import ru.cryhards.brootkiddie.items.Malware
import ru.cryhards.brootkiddie.items.Script
import ru.cryhards.brootkiddie.items.effects.MiningEffect
import ru.cryhards.brootkiddie.items.effects.SpreadingEffect


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
     * Returns Item debug example
     */
    fun emptyItem() = Script(
            "<Name>",
            "<Some info about item>",
            Texture("img/ui/empty.png"), 1)


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
                Texture("img/ui/back.png"), 1)

        block.actions.add(logger)
        return block
    }


    /**
     * Basic spreading script
     */
    fun SpreaderV3000(): Script {
        val block = Script(
                "Spreader V3000",
                "adds 50 to spreading",
                Texture("img/items/worm.png"), 1)

        block.applyDependency = { script, sides, i ->
            Gdx.app.log("UI", "DABDM CALLED")

            when (sides) {
                Script.SIDES.BOTTOM -> {
                    if (script.title == "<Name>") {
                        Gdx.app.log("UI", "DABDM")
                        script.temporaryEffects.add(MiningEffect())
                    }
                }
                else -> {
                    // nothing
                }
            }
        }

        block.dependencyDescription = "Adds extra Mining Effect if located to the top of <Name> test item"
        block.effects.add(SpreadingEffect())
        return block
    }


    /**
     * Spreading Multiplier
     */
    fun spreadingMultiplier(side: Script.SIDES): Script {
        val block = Script(
                "Spreading Mult",
                "Multiplies spreading effect of the item to the ${side.text} of script",
                Texture("img/ui/back.png"), 1)

        block.sideAffection = { script, sides, i ->
            Gdx.app.log("UI", "YAHOOO CALLED")

            when (sides) {
                side -> {
                    val affection = object : Item.Effect(
                            "Affected Spreading (Spreading Mult)",
                            "x2 | Add-on given by Spreading Mult") {
                        override fun affect(target: Any?, vararg dependencies: Any?): Item.Effect {
                            (target as Malware.Stats).spreadingSpeed *= 2
                            return super.affect(target, *dependencies)
                        }
                    }

                    Gdx.app.log("UI", "YAHOOO")

                    script.temporaryEffects.add(affection)
                }
                else -> {
                    // nothing
                }
            }
        }

        block.sideEffectsDescription = "Affects item located to the $side of it"
        block.dependencyDescription = "Np dep provided"
        return block
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