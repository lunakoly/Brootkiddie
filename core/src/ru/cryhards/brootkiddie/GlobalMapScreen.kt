package ru.cryhards.brootkiddie

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.FitViewport
import ru.cryhards.brootkiddie.malware.Distribution
import ru.cryhards.brootkiddie.malware.MalwareExample
import ru.cryhards.brootkiddie.utils.AssetManager


/**
 * Created with love by luna_koly on 21.01.2018.
 */
class GlobalMapScreen : Screen {
    val player = Player()

    val realSecondsPerDay = 0.5f
    var currentDayTime = 0f

    val infectedLabel : Label
    val cryptoLabel : Label

    val labelFont: BitmapFont
    val labelStyle: Label.LabelStyle

    private val cam = OrthographicCamera()  // width & height are not important due to FitViewport
    private val stage = Stage(FitViewport(
            Gdx.graphics.width.toFloat(),
            Gdx.graphics.height.toFloat(),
            cam))

    private val map = ImageActor("map.jpg")

    init {
        AssetManager.loadFont("fonts/roboto.ttf", "roboto")
        val fontParameter = FreeTypeFontGenerator.FreeTypeFontParameter()
        fontParameter.size = 70
        labelFont = AssetManager.makeFont("roboto", fontParameter)

        labelStyle = Label.LabelStyle(labelFont, Color(0f, 0f, 0f, 1f))

        Gdx.input.inputProcessor = stage

        stage.addListener(FloatingCameraControls(cam, stage))
        stage.addActor(map)

        infectedLabel = Label("INFECTED : ${player.infectedNodes} DAY : ${player.days}", labelStyle)
        infectedLabel.x = 0f
        infectedLabel.y = 0f
        stage.addActor(infectedLabel)

        cryptoLabel = Label("CRYPTO : ${player.crypto}", labelStyle)
        infectedLabel.x = 0f
        infectedLabel.y = 100f
        stage.addActor(cryptoLabel)

        val stageAspect = stage.width / stage.height
        val mapAspect = map.width / map.height

        if (mapAspect >= stageAspect) {
            map.setSize(stage.height * mapAspect, stage.height)
        } else {
            map.setSize(stage.width, stage.width / mapAspect)
        }

        map.setPosition(stage.width / 2, stage.height / 2, Align.center)

        player.distribution = Distribution(player)
        val malware = MalwareExample(player)
        player.addMalware(malware)
    }


    override fun hide() {

    }

    override fun show() {

    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        stage.act(delta)
        act(delta)
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

    fun act(deltaT: Float) {
        currentDayTime += deltaT
        while (currentDayTime > realSecondsPerDay) {
            player.days += 1
            player.doDay()
            updateUI()
            currentDayTime -= realSecondsPerDay
        }
    }

    private fun updateUI() {
        infectedLabel.setText("INFECTED : ${player.infectedNodes} DAY : ${player.days}")
        cryptoLabel.setText("CRYPTO : ${player.crypto}")
    }
}