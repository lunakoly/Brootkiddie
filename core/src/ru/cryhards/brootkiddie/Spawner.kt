@file:Suppress("MemberVisibilityCanBePrivate")

package ru.cryhards.brootkiddie

import com.badlogic.gdx.math.MathUtils.random
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions

/**
 * Created with love by luna_koly on 21.01.2018.
 */
object Spawner {

    fun spawnIcon(stage: Stage): ImageActor {
        val icon = getSomeDeviceIcon()
        val rand = randomXY(stage)

        icon.setPosition(rand.first, rand.second)
        icon.addAction(Actions.sequence(
                Actions.fadeOut(0f),
                Actions.parallel(
                        Actions.fadeIn(0.2f),
                        Actions.rotateBy(360f)
                )
        ))

        return icon
    }

    fun randomXY(stage: Stage) = randomXY(0f, 0f, stage.width, stage.height)

    fun randomXY(minX: Float, minY: Float, maxX: Float, maxY: Float) = Pair(random(minX, maxX), random(minY, maxY))

    fun getSomeDeviceIcon() = ImageActor("game-console.png")
}