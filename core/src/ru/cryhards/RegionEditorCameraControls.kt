package ru.cryhards

import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Stage
import ru.cryhards.brootkiddie.GlobalMap

/**
 * Created with love by luna_koly on 23.01.2018.
 */
class RegionEditorCameraControls(private val cam: Camera, private val stage: Stage) : InputListener() {

    private val DECR = 10f
    private var oldX = 0f
    private var oldY = 0f
    private var oldW = 0f
    private var oldH = 0f

    private var touchTime = 0L
    private var inMoveMode = true

    private val region = GlobalMap.highlightRegion(GlobalMap.Regions.AUSTRALIA)

    init {
        stage.addActor(region)

        var m = GlobalMap.mapCoordinates(region)
        println("xy: ${m.first} ${m.second}")

        m = GlobalMap.mapSize(region)
        println("wh: ${m.first} ${m.second}")
    }

    override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {

        println("!!!!!")
        var m = GlobalMap.mapCoordinates(region)
        println("xy: ${m.first} ${m.second}")

        m = GlobalMap.mapSize(region)
        println("wh: ${m.first} ${m.second}")


        val newTouchTime = System.currentTimeMillis()

        if (newTouchTime - touchTime < 500) {
            inMoveMode = !inMoveMode
        }

        touchTime = newTouchTime

        oldX = region.x - x /DECR
        oldY = region.y - y /DECR
        oldW = region.width - x /DECR
        oldH = region.height - y /DECR

        return true
    }

    override fun touchDragged(event: InputEvent?, x: Float, y: Float, pointer: Int) {
        if (inMoveMode) {
            region.setPosition(
                    oldX + x /DECR,
                    oldY + y /DECR
            )

            val m = GlobalMap.mapCoordinates(region)
            println("xy: ${m.first} ${m.second}")
        } else {
            region.setSize(
                    oldW + x /DECR,
                    oldH + y /DECR
            )

            val m = GlobalMap.mapSize(region)
            println("wh: ${m.first} ${m.second}")
        }
    }
}