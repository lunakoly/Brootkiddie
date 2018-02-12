package ru.cryhards.brootkiddie.screens.bench

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import ru.cryhards.brootkiddie.Core
import ru.cryhards.brootkiddie.Player
import ru.cryhards.brootkiddie.ui.Cropper
import ru.cryhards.brootkiddie.ui.ImageActor
import ru.cryhards.brootkiddie.ui.UI
import ru.cryhards.brootkiddie.ui.items.ItemExplorer

/**
 * One of the screens that guys needed
 */
class BenchScreen : ScreenAdapter() {
    private val stage = Stage()

    private val background = ImageActor("img/bg/noisy_surface.png")
    private val crypto = UI.GlitchLabel("  $100  ")
    private val backButton = UI.GlitchImageButton("img/ui/back.png")
    private val openBrowserButton = UI.GlitchImageButton("img/ui/browser.png")

    private val explorer = ItemExplorer()
    private val blockSpace = BlockSpace(explorer)


    init {
        // background
        val bounds = Cropper.fitCenter(
                stage.width, stage.height,
                background.width, background.height)
        background.setPosition(bounds[0], bounds[1])
        background.setSize(bounds[2], bounds[3])
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
        stage.addActor(crypto)

        // explorer
        explorer.setSize(stage.width / 3.5f, stage.height)
        //explorer.squeezeUI()

        explorer.setPosition(stage.width, stage.height, Align.topRight)
        stage.addActor(explorer)

        // blockSpace
        blockSpace.setSize(stage.width - explorer.width - backButton.width - 100f, stage.height)
        blockSpace.squeezeUI()
        blockSpace.setPosition(stage.width - explorer.width, stage.height, Align.topRight)
        stage.addActor(blockSpace)


        // test bench
        Player.inventory.items.add(UI.emptyItem())
        Player.inventory.items.add(UI.loremItem())
        blockSpace.buildBlockSpace(Player.inventory.items)
    }


    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        stage.act(delta)
        stage.draw()

        super.render(delta)
    }

    override fun show() {
        Gdx.input.inputProcessor = stage
        super.show()
    }

}