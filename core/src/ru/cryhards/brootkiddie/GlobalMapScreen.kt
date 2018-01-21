package ru.cryhards.brootkiddie

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.*
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable


/**
 * Created with love by luna_koly on 21.01.2018.
 */
class GlobalMapScreen : Screen {

    private val cam = OrthographicCamera()  // width & height are not important due to FitViewport
    private val stage = Stage(FitViewport(
            Gdx.graphics.width.toFloat(),
            Gdx.graphics.height.toFloat(),
            cam))


    private val map = ImageActor("map.jpg")


    init {
        Gdx.input.inputProcessor = stage

        stage.addListener(FloatingCameraControls(cam, stage))
        stage.addActor(map)


        val stageAspect = stage.width / stage.height
        val mapAspect = map.width / map.height

        if (mapAspect >= stageAspect) {
            map.setSize(stage.height * mapAspect, stage.height)
        } else {
            map.setSize(stage.width, stage.width / mapAspect)
        }

        map.setPosition(stage.width / 2, stage.height / 2, Align.center)

    }


    override fun hide() {

    }

    override fun show() {

    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        stage.act(delta)
        stage.draw()
    }

    override fun pause() {

    }

    override fun resume() {

    }

    override fun resize(width: Int, height: Int) {
        stage.viewport.update(width, height, true)
    }

    override fun dispose() {
        map.texture.dispose()
    }

}