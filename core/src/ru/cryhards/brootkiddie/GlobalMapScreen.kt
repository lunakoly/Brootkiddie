package ru.cryhards.brootkiddie

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.FitViewport
import ru.cryhards.RegionEditorCameraControls
import ru.cryhards.brootkiddie.levels.GlobalMapLevel
import ru.cryhards.brootkiddie.utils.AssetManager
import ru.cryhards.brootkiddie.utils.fixed
import ru.cryhards.brootkiddie.utils.humanReadable

/**
 * Created with love by luna_koly on 21.01.2018.
 */
class GlobalMapScreen(val player: Player, val game : ReallyGame) : Screen {

    val level = GlobalMapLevel(player)

    val infectedLabel : Label
    val cryptoLabel : Label
    val dayLabel: Label

    val labelFont: BitmapFont
    val labelStyle: Label.LabelStyle

    private val cam = OrthographicCamera()  // width & height are not important due to FitViewport
    private val stage = Stage(FitViewport(
            Gdx.graphics.width.toFloat(),
            Gdx.graphics.height.toFloat(),
            cam))

    init {
        AssetManager.loadFont("fonts/roboto.ttf", "roboto")
        val fontParameter = FreeTypeFontGenerator.FreeTypeFontParameter()
        fontParameter.size = 70
        labelFont = AssetManager.makeFont("roboto", fontParameter)

        labelStyle = Label.LabelStyle(labelFont, Color(224 / 255f, 35f / 255f, 45f / 255f, 1f))

        Gdx.input.inputProcessor = stage



        // IMPORTANT ORDER

        stage.addActor(GlobalMap.map)

        val stageAspect = stage.width / stage.height
        val mapAspect = GlobalMap.map.width / GlobalMap.map.height

        if (mapAspect >= stageAspect) {
            GlobalMap.map.setSize(stage.height * mapAspect, stage.height)
        } else {
            GlobalMap.map.setSize(stage.width, stage.width / mapAspect)
        }

        GlobalMap.map.setPosition(stage.width / 2, stage.height / 2, Align.center)


        // IMPORTANT ORDER
        stage.addListener(FloatingCameraControls(cam, stage))
//        stage.addListener(RegionEditorCameraControls(cam, stage))


        infectedLabel = Label("INFECTED", labelStyle)
        infectedLabel.x = 0f
        infectedLabel.y = 0f
        stage.addActor(infectedLabel)

        dayLabel = Label("DAY", labelStyle)
        dayLabel.x = 0f
        dayLabel.y = 200f
        stage.addActor(dayLabel)

        cryptoLabel = Label("CRYPTO", labelStyle) // update UI will be called later to set up right label texts
        infectedLabel.x = 0f
        infectedLabel.y = 100f
        stage.addActor(cryptoLabel)

        val editorButton = Label("EDITOR", labelStyle)
        editorButton.y = 300f

        editorButton.addListener(object : InputListener(){
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                game.openEditor()
                return super.touchDown(event, x, y, pointer, button)
            }
        })

        stage.addActor(editorButton)

        updateUI()
    }


    override fun hide() {

    }

    override fun show() {
        Gdx.input.inputProcessor = stage
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
        stage.viewport.update(width, height, false)
    }

    override fun dispose() {
        GlobalMap.map.dispose()
    }

    fun act(deltaT: Float) {
        level.act(deltaT)
        updateUI()
    }

    private fun updateUI() {
        infectedLabel.setText("INFECTED : (${(level.totalInfectedNodes * 100f / level.totalNodes).fixed(2)}%) ${level.totalInfectedNodes.humanReadable()}")
        cryptoLabel.setText("CRYPTO : ${player.crypto.humanReadable()}")
        dayLabel.setText("DAY : ${level.days}")
    }
}