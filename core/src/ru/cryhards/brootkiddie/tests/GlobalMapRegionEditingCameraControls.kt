package ru.cryhards.brootkiddie.tests

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import ru.cryhards.brootkiddie.screens.globalmap.GlobalMap

/**
 * Used to manually edit regions
 */
class GlobalMapRegionEditingCameraControls(map: GlobalMap) : InputListener() {

    private val decr = 10f
    private var oldX = 0f
    private var oldY = 0f
    private var oldW = 0f
    private var oldH = 0f

    private var touchTime = 0L
    private var inMoveMode = true

    private val regionsTest = GlobalMapRegionTests(map)
    val region = regionsTest.highlightRegion(GlobalMap.Regions.AUSTRALIA)


    init {
        var m = regionsTest.mapCoordinates(region)
        println("xy: ${m.first} ${m.second}")

        m = regionsTest.mapSize(region)
        println("wh: ${m.first} ${m.second}")
    }


    override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {

        println("!!!!!")
        var m = regionsTest.mapCoordinates(region)
        println("xy: ${m.first} ${m.second}")

        m = regionsTest.mapSize(region)
        println("wh: ${m.first} ${m.second}")


        val newTouchTime = System.currentTimeMillis()

        if (newTouchTime - touchTime < 500) {
            inMoveMode = !inMoveMode
        }

        touchTime = newTouchTime

        oldX = region.x - x / decr
        oldY = region.y - y / decr
        oldW = region.width - x / decr
        oldH = region.height - y / decr

        return true
    }

    override fun touchDragged(event: InputEvent?, x: Float, y: Float, pointer: Int) {
        if (inMoveMode) {
            region.setPosition(
                    oldX + x / decr,
                    oldY + y / decr
            )

            val m = regionsTest.mapCoordinates(region)
            println("xy: ${m.first} ${m.second}")
        } else {
            region.setSize(
                    oldW + x / decr,
                    oldH + y / decr
            )

            val m = regionsTest.mapSize(region)
            println("wh: ${m.first} ${m.second}")
        }
    }
}
