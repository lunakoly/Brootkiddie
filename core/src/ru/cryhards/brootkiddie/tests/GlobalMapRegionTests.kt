package ru.cryhards.brootkiddie.tests

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.utils.Align
import ru.cryhards.brootkiddie.screens.globalmap.GlobalMap
import ru.cryhards.brootkiddie.ui.ImageActor

/**
 * Created with love by luna_koly on 07.02.2018.
 */
class GlobalMapRegionTests(private val map: GlobalMap) {

    /**
     * ---< DEBUG >---
     * Puts debug square on the map to show
     * region location
     */
    fun highlightRegion(region: GlobalMap.Regions): ImageActor {
        val reg = ImageActor("img/debug_square.png")
        val pos = map.unmapCoordinates(region)
        val size = map.unmapSize(region)

        reg.setSize(size.first, size.second)
        reg.setPosition(pos.first, pos.second, Align.center)
        return reg
    }


    /**
     * ---< DEBUG >---
     * Transforms target coordinates from absolute to map space
     */
    fun mapCoordinates(target: Actor): Pair<Float, Float> {
        return Pair(
                (target.x + target.width/2 - map.x - map.width/2) / (map.width/2),
                (target.y + target.height/2 - map.y - map.height/2) / (map.height/2)
        )
    }

    /**
     * ---< DEBUG >---
     * Transforms target size from absolute to map space
     */
    fun mapSize(target: Actor): Pair<Float, Float> {
        return Pair(
                target.width / map.width,
                target.height / map.height
        )
    }

}