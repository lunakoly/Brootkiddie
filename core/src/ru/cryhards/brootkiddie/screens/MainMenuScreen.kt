package ru.cryhards.brootkiddie.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import ru.cryhards.brootkiddie.Assets
import ru.cryhards.brootkiddie.Core
import ru.cryhards.brootkiddie.ui.Cropper
import ru.cryhards.brootkiddie.ui.ImageActor
import ru.cryhards.brootkiddie.ui.UI

/**
 * Main menu screen
 */
class MainMenuScreen : ScreenAdapter() {
    private val background = ImageActor("img/bg/logo.png")
    private var playButton: TextButton
    private var loadButton: TextButton
    private val stage = Stage()

    init {
        Gdx.input.inputProcessor = stage

        // bg
        val bounds = Cropper.fitCenter(
                stage.width, stage.height,
                background.width, background.height)
        background.setPosition(bounds[0], bounds[1])
        background.setSize(bounds[2], bounds[3])
        stage.addActor(background)


        // play button
        playButton = UI.GlitchTextButton("    NEW    ")
        playButton.setPosition(stage.width / 2f, (stage.height - 200 + playButton.height + 20) / 2f, Align.center)
        stage.addActor(playButton)

        // load button
        loadButton = UI.GlitchTextButton("    LOAD    ")
        loadButton.setPosition(stage.width / 2f, (stage.height - 200 - loadButton.height - 20) / 2f, Align.center)
        stage.addActor(loadButton)


        // shader
        stage.batch.shader = Assets.Shaders.ABERRATION


        // actions
        playButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                Core.instance.newGame()
            }
        })

        loadButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                Core.instance.loadGame()
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