package ru.cryhards.brootkiddie.screens.cameras

import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import ru.cryhards.brootkiddie.ui.ImageActor

/**
 * Restricts camera movement to [ x y ] axises
 */
class FloatingCameraControls(private val cam: Camera, private val restriction: ImageActor? = null) : InputListener() {
    private val decr = 10f
    private var oldX = 0f
    private var oldY = 0f

    override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
        oldX = cam.position.x + x / decr
        oldY = cam.position.y + y / decr
        return true
    }

    override fun touchDragged(event: InputEvent?, x: Float, y: Float, pointer: Int) {
        var nx = oldX - x / decr
        var ny = oldY - y / decr

        if (restriction != null) {
            if (nx + cam.viewportWidth / 2f > restriction.x + restriction.width)
                nx = restriction.x + restriction.width - cam.viewportWidth / 2f

            if (ny + cam.viewportHeight / 2f > restriction.y + restriction.height)
                ny = restriction.y + restriction.height - cam.viewportHeight / 2f

            if (nx - cam.viewportWidth / 2f < restriction.x)
                nx = restriction.x + cam.viewportWidth / 2f
//
            if (ny - cam.viewportHeight / 2f < restriction.y)
                ny = restriction.y + cam.viewportHeight / 2f
        }

        cam.position.x = nx
        cam.position.y = ny
    }
}