package ru.cryhards.brootkiddie.screens.inventory

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import ru.cryhards.brootkiddie.Assets
import ru.cryhards.brootkiddie.Core
import ru.cryhards.brootkiddie.Player
import ru.cryhards.brootkiddie.items.Malware
import ru.cryhards.brootkiddie.items.effects.DisguiseEffect
import ru.cryhards.brootkiddie.items.effects.MiningEffect
import ru.cryhards.brootkiddie.items.effects.SpreadingEffect
import ru.cryhards.brootkiddie.ui.UI

/**
 * One of the screens that guys needed
 */
class InventoryScreen : ScreenAdapter() {
    private val stage = Stage()

    private val background = Image(UI.colorToDrawable(Color(.07f, .07f, .07f, 1f)))

    private val crypto = UI.GlitchLabel("  $100  ")
    private val backButton = UI.GlitchImageButton("img/ui/back.png")
    private val openBrowserButton = UI.GlitchImageButton("img/ui/browser.png")

    private val explorer = ItemExplorer()
    private val blockSpace = InventoryBlockSpace(explorer)

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
        openBrowserButton.setPosition(50f, stage.height - 50f, Align.topLeft)
        stage.addActor(openBrowserButton)

        openBrowserButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                Core.instance.toBrowser()
            }
        })

        // crypto
        crypto.setPosition(50f, stage.height - openBrowserButton.height - 100f, Align.topLeft)
        crypto.setSize(openBrowserButton.width, crypto.height)
        crypto.style.background = null
        stage.addActor(crypto)

        // explorer
        explorer.setSize(stage.width / 3.5f, stage.height)
        explorer.setPosition(stage.width, stage.height, Align.topRight)
        stage.addActor(explorer)

        // blockSpace
        val pane = ScrollPane(blockSpace)
        blockSpace.setSize(stage.width - explorer.width - backButton.width - 100f, stage.height)
        pane.debug = true
        pane.setSize(stage.width - explorer.width - backButton.width - 100f, stage.height)
        pane.setPosition(stage.width - explorer.width, stage.height, Align.topRight)

        blockSpace.shader = Assets.Shaders.WAVE
        stage.addActor(pane)


        // test inventory
        Player.Inventory.items.add(UI.emptyItem())
        Player.Inventory.items.add(UI.loremItem())
        val exmalw = Malware("PETYA", "MINER", Texture("img/items/malware.png"))
        exmalw.combine(DisguiseEffect())
        exmalw.combine(MiningEffect())
        exmalw.combine(SpreadingEffect())
        Player.Inventory.items.add(exmalw)
    }


    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        stage.act(delta)
        stage.draw()

        super.render(delta)
    }

    override fun show() {
        blockSpace.fill(Player.Inventory.items)
        Gdx.input.inputProcessor = stage
        super.show()
    }

}