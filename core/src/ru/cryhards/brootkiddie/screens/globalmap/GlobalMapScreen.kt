package ru.cryhards.brootkiddie.screens.globalmap

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
import ru.cryhards.brootkiddie.Assets
import ru.cryhards.brootkiddie.Core
import ru.cryhards.brootkiddie.Environment
import ru.cryhards.brootkiddie.Player
import ru.cryhards.brootkiddie.items.effects.Converter.humanReadable
import ru.cryhards.brootkiddie.screens.cameras.FloatingCameraControls
import ru.cryhards.brootkiddie.ui.Cropper
import ru.cryhards.brootkiddie.ui.UI

/**
 * Just Global Map
 */
class GlobalMapScreen : ScreenAdapter() {
    private val background = GlobalMap()
    private val camera = OrthographicCamera()

    /**
     * For scene 'fixed' objects
     */
    private val mapStage = Stage(FitViewport(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat(), camera))
    /**
     * For floating UI
     */
    private val uiStage = Stage()


    private val openBrowserButton = UI.GlitchImageButton("img/ui/browser.png")
    private val openBenchButton = UI.GlitchImageButton("img/ui/bench.png")
    private val console = UI.GlitchConsole("=== MEGA SHELL V8000 ===")
    private val crypto = UI.GlitchLabel("$888M")
    private val infected = UI.GlitchLabel("888M")

    init {
        // map
        val bounds = Cropper.fitCenter(
                mapStage.width, mapStage.height,
                background.width, background.height)
        background.setPosition(bounds[0], bounds[1])
        background.setSize(bounds[2], bounds[3])
        mapStage.batch.shader = Assets.Shaders.WAVE
        Environment.UI.globalMap = background
        mapStage.addActor(background)

        // camera controls
        mapStage.addListener(FloatingCameraControls(camera, background))


        // bench
        openBenchButton.setPosition(50f, 50f, Align.bottomLeft)
        uiStage.addActor(openBenchButton)

        openBenchButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                Core.instance.toBench()
            }
        })

        // browser
        openBrowserButton.setPosition(Gdx.graphics.width - 50f, 50f, Align.bottomRight)
        uiStage.addActor(openBrowserButton)

        openBrowserButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                Core.instance.toBrowser()
            }
        })

        // crypto
        crypto.setPosition(Gdx.graphics.width - 50f, Gdx.graphics.height - 50f, Align.topRight)
        uiStage.addActor(crypto)

        // infected
        infected.setPosition(Gdx.graphics.width - 50f, Gdx.graphics.height - 150f, Align.topRight)
        uiStage.addActor(infected)

        // console
        console.setPosition(50f, Gdx.graphics.height - 50f, Align.topLeft)
        Environment.UI.console = console
        uiStage.addActor(console)
    }



    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        updateUI()

        mapStage.act(delta)
        mapStage.draw()
        uiStage.act(delta)
        uiStage.draw()

        super.render(delta)
    }

    fun updateUI() {
        infected.setText(humanReadable(Environment.infectedNodes))
        crypto.setText("$" + humanReadable(Player.money.toFloat()))
    }

    override fun show() {
        Gdx.input.inputProcessor = InputMultiplexer(uiStage, mapStage)
        super.show()
    }
}