@file:Suppress("MemberVisibilityCanBePrivate")

package ru.cryhards.brootkiddie

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.MathUtils.random
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions

/**
 * Created with love by luna_koly on 21.01.2018.
 */
object Spawner {

    fun spawnIcon(stage: Stage): ImageActor {
        val icon = getSomeDeviceIcon()
        val region = GlobalMap.Regions.values()[ random(GlobalMap.Regions.values().size - 1) ]
        val rand = randomXY(region)

        println(region)

        icon.setPosition(rand.first, rand.second)
        icon.addAction(Actions.sequence(
                Actions.fadeOut(0f),
                Actions.parallel(
                        Actions.fadeIn(0.2f)
                )
        ))

        icon.color = Color(random(), random(), random(), 1f)
        return icon
    }

    fun randomXY(region: GlobalMap.Regions): Pair<Float, Float> {
        val size = GlobalMap.unmapSize(region)
        val pos = GlobalMap.unmapCoordinates(region)
        return randomXYNear(pos.first, size.first/2, pos.second, size.second/2)
    }

    fun randomXYNear(x: Float, dx: Float, y: Float, dy: Float) = randomXY(x - dx, y - dy,x + dx,y + dy)

    fun randomXY(minX: Float, minY: Float, maxX: Float, maxY: Float) = Pair(random(minX, maxX), random(minY, maxY))

    fun getSomeDeviceIcon() = ImageActor("game-console.png")

    fun getRegionOverlappingSquare(): ImageActor {
        val d = ImageActor("square.png")
        d.setSize(100f, 100f)
        d.setPosition(100f, 100f)
        d.addAction(Actions.alpha(0.5f))
        return d
    }
}