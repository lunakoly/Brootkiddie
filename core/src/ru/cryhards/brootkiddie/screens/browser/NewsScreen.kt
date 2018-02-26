package ru.cryhards.brootkiddie.screens.browser

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup
import com.badlogic.gdx.utils.Align
import ru.cryhards.brootkiddie.ui.UI

/**
 * Created by user on 2/19/18.
 */

class NewsScreen : ScreenAdapter() {
    private val background = Texture("img/bg/logo.png")
    private val backButton = UI.GlitchImageButton("img/ui/back.png")
    private val uiGroup : VerticalGroup = VerticalGroup()
    private val stage = Stage()

    init {
        uiGroup.columnLeft()
        uiGroup.align(Align.topLeft)
        uiGroup.pad(10f)
        uiGroup.space(10f)
        uiGroup.setFillParent(true)
        stage.addActor(uiGroup)
}

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(45/255f, 45/255f, 45/255f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        stage.act()
        stage.draw()
    }

    override fun dispose() {
        background.dispose()
    }
}