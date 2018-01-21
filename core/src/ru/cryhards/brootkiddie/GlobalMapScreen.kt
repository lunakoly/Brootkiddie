package ru.cryhards.brootkiddie

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.*
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import ru.cryhards.brootkiddie.malware.Distribution
import ru.cryhards.brootkiddie.malware.MalwareExample


/**
 * Created with love by luna_koly on 21.01.2018.
 */
class GlobalMapScreen : Screen {

    val player = Player()

    val ticksInDay = 20

    var ticksToNextDay = ticksInDay

    lateinit var infectedLabel : Label


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


        val ftfg = FreeTypeFontGenerator(Gdx.files.internal("fonts/roboto.ttf"))
        val par = FreeTypeFontGenerator.FreeTypeFontParameter()
        par.size = 70

        infectedLabel = Label("INFECTED : ${player.infectedNodes} DAY : ${player.days}", Label.LabelStyle(ftfg.generateFont(par), Color(0f, 0f, 0f, 1f)))
        infectedLabel.x = 0f
        infectedLabel.y = 0f

        stage.addActor(infectedLabel)

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
        player.malwareList+=malware

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
        ticksToNextDay-=1
        if (ticksToNextDay == 0) {
            player.days += 1
            player.doDay()
            updateUi()
            ticksToNextDay = ticksInDay
        }
    }

    fun calcInfectionSpeed(): Int {
        return 1
    }

    private fun updateUi(){
        infectedLabel.setText("INFECTED : ${player.infectedNodes} DAY : ${player.days}")
    }
}