package ru.cryhards.brootkiddie.screens.inventory

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import ru.cryhards.brootkiddie.Assets
import ru.cryhards.brootkiddie.Core
import ru.cryhards.brootkiddie.Environment
import ru.cryhards.brootkiddie.items.Script
import ru.cryhards.brootkiddie.ui.UI

/**
 * One of the screens that guys needed
 */
class InventoryScreen : ScreenAdapter() {
    private val stage = Stage()

    private val background = Image(UI.colorToDrawable(Color(.07f, .07f, .07f, 1f)))

    private val crypto = UI.GlitchLabel("  ${Environment.player.money}  ")
    private val backButton = UI.GlitchImageButton("img/ui/back.png")
    private val openBrowserButton = UI.GlitchImageButton("img/ui/browser.png")
    private val bin = UI.GlitchImageButton("img/ui/bin.png")

    private val explorer = ItemExplorer()
    private val blockSpace = InventoryBlockSpace(explorer, bin)

    init {
        // background
        background.setPosition(0f, 0f)
        background.setSize(stage.width, stage.height)
        stage.addActor(background)


        // back
        backButton.setPosition(50f, 50f, Align.bottomLeft)
        stage.addActor(backButton)

        backButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                Core.instance.toGlobalMap()
            }
        })

        // browser
        openBrowserButton.setPosition(50f, stage.height - 30f, Align.topLeft)
        stage.addActor(openBrowserButton)

        openBrowserButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                Core.instance.toBrowser()
            }
        })

        // crypto
        bin.setPosition(50f, openBrowserButton.y - 30f, Align.topLeft)
        stage.addActor(bin)
        crypto.setPosition(50f,   bin.y - 30f, Align.topLeft)
        crypto.setSize(openBrowserButton.width, crypto.height)
        crypto.style.background = null
        stage.addActor(crypto)

        // explorer
        explorer.setSize(stage.width / 3.5f, stage.height)
        explorer.setPosition(stage.width, stage.height, Align.topRight)
        stage.addActor(explorer)
        explorer.explore(Environment.player.inventory.items.firstOrNull() ?: UI.emptyItem())

        // blockSpace
        val pane = ScrollPane(blockSpace)
        blockSpace.setSize(stage.width - explorer.width - backButton.width - 100f, stage.height)
        pane.debug = true
        pane.setSize(stage.width - explorer.width - backButton.width - 100f, stage.height)
        pane.setPosition(stage.width - explorer.width, stage.height, Align.topRight)

        blockSpace.shader = Assets.Shaders.WAVE
        stage.addActor(pane)

        // test inventory
        Environment.player.inventory.items.add(UI.emptyItem())
        Environment.player.inventory.items.add(UI.loremItem())
        Environment.player.inventory.items.add(UI.SpreaderV3000())
        Environment.player.inventory.items.add(UI.spreadingMultiplier(Script.SIDES.LEFT))
    }


    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        stage.act(delta)
        stage.draw()

        super.render(delta)
    }

    override fun show() {
        blockSpace.fill(Environment.player.inventory.items)
        explorer.reexplore()
        Gdx.input.inputProcessor = stage
        crypto.setText("${Environment.player.money}")
        super.show()
    }

}