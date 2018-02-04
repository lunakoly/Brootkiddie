package ru.cryhards.brootkiddie.cleanup.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.FitViewport
import ru.cryhards.brootkiddie.ImageActor
import ru.cryhards.brootkiddie.cleanup.cameras.FloatingCameraControls
import ru.cryhards.brootkiddie.cleanup.ui.UI

/**
 * Just Global Map
 */
class GlobalMapScreen : ScreenAdapter() {
    private val background = ImageActor("img/map.jpg")
    private val camera = OrthographicCamera()

    /**
     * For scene 'fixed' objects
     */
    private val mapStage = Stage(FitViewport(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat(), camera))
    /**
     * For floating UI
     */
    private val uiStage = Stage()


    private val openEditorButton = UI.RectButton("Editor")


    init {
        Gdx.input.inputProcessor = InputMultiplexer(uiStage, mapStage)

        val bounds = Cropper.fitCenter(
                mapStage.width, mapStage.height,
                background.width, background.height)
        background.setPosition(bounds[0], bounds[1])
        background.setSize(bounds[2], bounds[3])
        mapStage.addActor(background)

        mapStage.addListener(FloatingCameraControls(camera, background))
        openEditorButton.height /= 1.5f
        openEditorButton.width /= 1.5f

        openEditorButton.setPosition(50f, 50f, Align.bottomLeft)
        uiStage.addActor(openEditorButton)


        openEditorButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                println("FEAG")
            }
        })
    }


    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        mapStage.act(delta)
        mapStage.draw()
        uiStage.act(delta)
        uiStage.draw()

        super.render(delta)
    }
}