package ru.cryhards.brootkiddie.screens.browser

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import ru.cryhards.brootkiddie.Core
import ru.cryhards.brootkiddie.ui.UI

/**
 * Created by Dima on 11.02.2018.
 */
class BrowserScreen : ScreenAdapter() {
    private val stage = Stage()
    private val backButton = UI.GlitchImageButton("img/ui/back.png")

    init {
        val label = UI.StaticLabel("Shitty browser")
        label.setPosition(Gdx.graphics.width/2f, Gdx.graphics.height/2f, Align.center)
        label.style.background = null
        stage.addActor(label)

        backButton.setPosition(50f, 50f, Align.bottomLeft)
        stage.addActor(backButton)

        backButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                Core.instance.toGlobalMap()
            }
        })
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(45/255f, 45/255f, 45/255f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        stage.act(delta)
        stage.draw()
    }
}