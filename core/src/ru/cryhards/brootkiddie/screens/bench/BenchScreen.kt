package ru.cryhards.brootkiddie.screens.bench

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import ru.cryhards.brootkiddie.Core
import ru.cryhards.brootkiddie.ui.ItemExplorer
import ru.cryhards.brootkiddie.ui.UI

/**
 * One of the screens that guys needed
 */
class BenchScreen : ScreenAdapter() {
    private val stage = Stage()


    private val crypto = UI.GlitchLabel("  $100  ")
    private val backButton = UI.GlitchImageButton("img/ui/back.png")
    private val openBrowserButton = UI.GlitchImageButton("img/ui/browser.png")
    private val explorer = ItemExplorer()


    init {
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
//        val tab = ScrollPane(explorer)
//        tab.setSize(stage.width / 3f, stage.height)
//        tab.setPosition(0f, 0f, Align.bottomRight)
//        stage.addActor(tab)
        explorer.setSize(stage.width / 3f, stage.height)
//        explorer.setPosition(0f, 0f, Align.bottomRight)
        stage.addActor(explorer)

        // TODO table
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