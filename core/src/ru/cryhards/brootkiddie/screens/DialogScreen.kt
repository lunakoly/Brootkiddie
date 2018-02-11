package ru.cryhards.brootkiddie.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import ru.cryhards.brootkiddie.Core
import ru.cryhards.brootkiddie.events.dialogs.Dialog
import ru.cryhards.brootkiddie.ui.Cropper

/**
 * Created by remmargorp on 11.02.18.
 */

class DialogScreen : ScreenAdapter() {
    val dialog = Dialog.readFromFile("dialogs/example.json")

    private val background = Texture("img/bg/logo.png")

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        Core.instance.batch.begin()
        val bounds = Cropper.fitCenter(
                Gdx.graphics.width.toFloat(),
                Gdx.graphics.height.toFloat(),
                background.width.toFloat(),
                background.height.toFloat())
        Core.instance.batch.draw(background, bounds[0], bounds[1], bounds[2], bounds[3])
        Core.instance.batch.end()
    }

    override fun dispose() {
        background.dispose()
    }
}