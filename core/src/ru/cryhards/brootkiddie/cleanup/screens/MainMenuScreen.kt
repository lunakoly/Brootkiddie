package ru.cryhards.brootkiddie.cleanup.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import ru.cryhards.brootkiddie.ImageActor
import ru.cryhards.brootkiddie.cleanup.Assets
import ru.cryhards.brootkiddie.cleanup.Core
import ru.cryhards.brootkiddie.cleanup.ui.UI

/**
 * Main menu screen
 */
class MainMenuScreen : ScreenAdapter() {
    private val background = ImageActor("img/logo.png")
    private var playButton: TextButton
    private var loadButton: TextButton
    private val stage = Stage()

    init {
        Gdx.input.inputProcessor = stage

        val bounds = Cropper.fitCenter(
                stage.width, stage.height,
                background.width, background.height)
        background.setPosition(bounds[0], bounds[1])
        background.setSize(bounds[2], bounds[3])
        stage.addActor(background)


        playButton = UI.RectButton("    NEW    ")
        playButton.setPosition(stage.width / 2f, (stage.height - 200 + playButton.height + 20) / 2f, Align.center)
        stage.addActor(playButton)

        loadButton = UI.RectButton("    LOAD    ")
        loadButton.setPosition(stage.width / 2f, (stage.height - 200 - loadButton.height - 20) / 2f, Align.center)
        stage.addActor(loadButton)

        stage.batch.shader = Assets.shaders.ABERRATION


        playButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                Core.instance.openMap()
            }
        })

    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        stage.act(delta)
        stage.draw()
    }

    override fun dispose() {
        background.dispose()
    }
}