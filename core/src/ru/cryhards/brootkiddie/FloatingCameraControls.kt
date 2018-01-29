package ru.cryhards.brootkiddie

import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Stage

/**
 * Created with love by luna_koly on 21.01.2018.
 */
class FloatingCameraControls(private val cam: Camera, private val stage: Stage) : InputListener() {

    private val DECR = 10f
    private var oldX = 0f
    private var oldY = 0f

    private var touchTime = 0L

    override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
        oldX = cam.position.x + x /DECR
        oldY = cam.position.y + y /DECR

        val newTouchTime = System.currentTimeMillis()

        if (newTouchTime - touchTime < 500) {
            //stage.addActor(Spawner.spawnIcon(stage))
        }

        touchTime = newTouchTime

        return true
    }

    override fun touchDragged(event: InputEvent?, x: Float, y: Float, pointer: Int) {
        cam.position.x = oldX - x /DECR
        cam.position.y = oldY - y /DECR
    }
}