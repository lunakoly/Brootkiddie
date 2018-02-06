package ru.cryhards.brootkiddie.screens

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
import ru.cryhards.brootkiddie.ui.ImageActor
import ru.cryhards.brootkiddie.Assets
import ru.cryhards.brootkiddie.Core
import ru.cryhards.brootkiddie.screens.cameras.FloatingCameraControls
import ru.cryhards.brootkiddie.ui.UI

/**
 * Just Global Map
 */
class GlobalMapScreen : ScreenAdapter() {
    var day = 0


    private val background = ImageActor("img/bg/map.jpg")
    private val camera = OrthographicCamera()

    /**
     * For scene 'fixed' objects
     */
    private val mapStage = Stage(FitViewport(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat(), camera))
    /**
     * For floating UI
     */
    private val uiStage = Stage()


    private val openBrowserButton = UI.GlitchImageButton("img/icons/bench.png")
    private val console = UI.Console("=== MEGA SHELL V8000 ===")
    private val openBenchButton = UI.GlitchImageButton("img/icons/bench.png")
    private val crypto = UI.GlitchLabel("  $100  ")


    init {
        Gdx.input.inputProcessor = InputMultiplexer(uiStage, mapStage)

        // map
        val bounds = Cropper.fitCenter(
                mapStage.width, mapStage.height,
                background.width, background.height)
        background.setPosition(bounds[0], bounds[1])
        background.setSize(bounds[2], bounds[3])
        mapStage.batch.shader = Assets.shaders.WAVE
        mapStage.addActor(background)

        // camera controls
        mapStage.addListener(FloatingCameraControls(camera, background))


        // bench
        openBenchButton.setPosition(50f, 50f, Align.bottomLeft)
        uiStage.addActor(openBenchButton)

        openBenchButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                Core.instance.openBench()
            }
        })

        // browser
        openBrowserButton.setPosition(uiStage.width - 50f, 50f, Align.bottomRight)
        uiStage.addActor(openBrowserButton)

        openBrowserButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                println("> OPEN BROWSER")
            }
        })

        // crypto
        crypto.setPosition(uiStage.width - 50f, uiStage.height - 50f, Align.topRight)
        uiStage.addActor(crypto)

        // console
        console.setPosition(50f, uiStage.height - 50, Align.topLeft)
        uiStage.addActor(console)

        var i = 1
        Core.instance.tasks.add(Core.Task(3, 1000) {
            console.log("HI $i this is long text with important data here")
//            console.log("HI $i")
            i++
        })


        // run day updator
        Core.instance.tasks.add(Core.Task(-1, 3000, {
            day++
            console.log("Day $day")
        }))

        // TODO: console, handlers for ui
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