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
    private val openInventoryButton = UI.GlitchImageButton("img/ui/bench.png")
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
        UI.globalMap = background
        mapStage.addActor(background)

        // camera controls
        mapStage.addListener(FloatingCameraControls(camera, background))


        // inventory
        openInventoryButton.setPosition(50f, 50f, Align.bottomLeft)
        openInventoryButton.isVisible = false
        uiStage.addActor(openInventoryButton)

        openInventoryButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                Core.instance.toInventory()
            }
        })

        // browser
        openBrowserButton.setPosition(Gdx.graphics.width - 50f, 50f, Align.bottomRight)
        openBrowserButton.isVisible = false
        uiStage.addActor(openBrowserButton)

        openBrowserButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                Core.instance.toBrowser()
            }
        })

        // crypto
        crypto.setPosition(Gdx.graphics.width - 50f, Gdx.graphics.height - 50f, Align.topRight)
        crypto.isVisible = false
        uiStage.addActor(crypto)

        // infected
        infected.setPosition(Gdx.graphics.width - 50f, Gdx.graphics.height - 150f, Align.topRight)
        infected.isVisible = false
        uiStage.addActor(infected)

        // console
        console.setPosition(50f, Gdx.graphics.height - 50f, Align.topLeft)
        console.addListener(object : ClickListener(){
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                if (Environment.instance.consoleCounter < 4) Environment.instance.consoleCounter++
                if (Environment.instance.consoleCounter == 3) {
                    showUI(0)
                    UI.console?.log("Now it should be fine.")
                }
            }
        })

        UI.console = console
        uiStage.addActor(console)
        updateUI()
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
        infected.setText(humanReadable(Environment.instance.infectedNodes))
        crypto.setText("$" + humanReadable(Environment.instance.player.money.toFloat()))
        if (Environment.instance.consoleCounter >= 4) {
            showUI(0)
        }
        if (Environment.instance.consoleCounter >= 5) {
            showUI(1)
        }
    }

    override fun show() {
        Gdx.input.inputProcessor = InputMultiplexer(uiStage, mapStage)
        super.show()
    }

    fun showUI(a : Int) {
        when (a) {
            0 -> {
                openBrowserButton.isVisible = true
                crypto.isVisible = true
                infected.isVisible = true
            }

            1 -> {
                openInventoryButton.isVisible = true
            }
        }
    }

}