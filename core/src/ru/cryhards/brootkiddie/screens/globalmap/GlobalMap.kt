package ru.cryhards.brootkiddie.screens.globalmap

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import ru.cryhards.brootkiddie.Core
import ru.cryhards.brootkiddie.Environment
import ru.cryhards.brootkiddie.Environment.SUSPICIOUSNESS_DETECT
import ru.cryhards.brootkiddie.Environment.TOTAL_NODES
import ru.cryhards.brootkiddie.Environment.currentSuspiciousness
import ru.cryhards.brootkiddie.Environment.infectedNodes
import ru.cryhards.brootkiddie.Environment.isMalwareDetected
import ru.cryhards.brootkiddie.Player
import ru.cryhards.brootkiddie.items.effects.Converter.sigmoid
import ru.cryhards.brootkiddie.ui.Cropper
import ru.cryhards.brootkiddie.ui.ImageActor
import java.lang.Math.*
import java.util.*

@Suppress("unused")
/**
 * Incapsulates global map object logic
 */
class GlobalMap : ImageActor("img/bg/map.jpg") {
    private val shapeBatch = ShapeRenderer()
    private var projectionMatrixSet = false

    private val points = mutableListOf<Pair<Float, Float>>()
    private val MAX_POINTS = 1000

    init {
        Core.instance.addTask(Core.Task(-1, Environment.DAY_TASK_PERIOD, {
            nextDay()
            false
        }))

        shapeBatch.color = Color.RED
    }

    private fun nextDay() {
        Environment.activeMalware?.run {
            Gdx.app.log("GlobalMapDay", "running day-logic")
            currentSuspiciousness += deltaSuspiciousness(stats.suspiciousness)
            if (currentSuspiciousness > 0.55f) {
                isMalwareDetected = true
                Environment.UI.console?.log("YOUR MALWARE IS DETECTED")
            }

            infectedNodes += deltaInfected(stats.spreadingSpeed, stats.infectiousness)

            Player.money += (pow(infectedNodes.toDouble(), 0.9) * stats.miningSpeed).toFloat()

            Gdx.app.log("DAY", "infected: $infectedNodes money: ${Player.money} susp: $currentSuspiciousness")
        }

        val ptd = pointsToDraw()
        while (points.size > ptd) {
            points.removeAt(Random().nextInt(points.size))
        }
        while (points.size < ptd) {
            points.add(randomXYInRegions())
        }
        Gdx.app.log("PTD", "drawing ${points.size} points on global map")
    }

    private fun deltaSuspiciousness(suspiciousness: Float) = sqrt(sigmoid(infectedNodes.toFloat() / TOTAL_NODES + if (isMalwareDetected) 0.1f else 0f).toDouble()).toFloat() * suspiciousness * (1f / 8f)

    private fun deltaInfected(spreadingSpeed: Float, infectiousness: Float): Long {
        val aware = currentSuspiciousness / SUSPICIOUSNESS_DETECT

        val shouldBeInfected = min(
                (TOTAL_NODES * exp(-pow(aware * 1.23, 9.0))).toLong(), // suspiciousness limit
                ((infectedNodes + 5) * (1 + infectiousness)).toLong()
        )

        var delta = (shouldBeInfected - infectedNodes)

        if (delta > 0)
            delta = max(1L, (delta * spreadingSpeed).toLong())

        return delta
    }

    private fun pointsToDraw(): Int {
        return (pow(infectedNodes / TOTAL_NODES.toDouble(), 0.37) * MAX_POINTS).toInt()
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        super.draw(batch, parentAlpha)
        if (!projectionMatrixSet) {
            batch?.run {
                shapeBatch.projectionMatrix = projectionMatrix
                projectionMatrixSet = true
            }
        }
        if (projectionMatrixSet) {
            batch?.end()
            shapeBatch.begin(ShapeRenderer.ShapeType.Filled)

            points.forEach {
                shapeBatch.rect(it.first, it.second, 5f, 5f)
            }

            shapeBatch.end()
            batch?.begin()
        }
    }

    fun randomXYInRegions(): Pair<Float, Float> {
        return randomXYInRegion(regionList[Random().nextInt(regionList.size)])
    }

    /**
     * Returns a pair of random coordinates within
     * a specified region
     */
    fun randomXYInRegion(region: Regions): Pair<Float, Float> {
        val size = unmapSize(region)
        val pos = unmapCoordinates(region)
        return Cropper.randomXYNear(pos.first, size.first / 2, pos.second, size.second / 2)
    }

    /**
     * Transforms region coordinates from map space to absolute
     */
    fun unmapCoordinates(region: Regions): Pair<Float, Float> {
        return Pair(
                x + width/2 + region.x * width/2,
                y + height/2 + region.y * height/2
        )
    }

    /**
     * Transforms region size from map space to absolute
     */
    fun unmapSize(region: Regions): Pair<Float, Float> {
        return Pair(
                region.width * width,
                region.height * height
        )
    }


    /**
     * Holds all geographical regions available to use
     */
    enum class Regions(
            val x: Float,
            val y: Float,
            val width: Float,
            val height: Float) {

        SIBERIA(0.6f, 0.7f, 0.2f, 0.245f),
        EASTERN_RUSSIA(0.25f, 0.6f, 0.08f, 0.2f),
        WESTERN_RUSSIA(0.85f, 0.7f, 0.12f, 0.17f),

        USA(-0.55f, 0.227f, 0.14f, 0.12f),

        EASTERN_CANADA(-0.64f, 0.6f, 0.138f, 0.24f),
        WESTERN_CANADA(-0.42f, 0.44f, 0.103f, 0.17f),
        NORTHERN_CANADA(-0.43f, 0.78f, 0.103f, 0.17f),

        AUSTRALIA(0.74f, -0.54f, 0.12f, 0.17f),
        GREENLAND(-0.21f, 0.83f, 0.1f, 0.16f),
        BRASILIA(-0.28f, -0.39f, 0.07f, 0.16f),
        ARGENTINA(-0.348f, -0.69f, 0.043f, 0.177f),

        COLUMBIA(-0.357f, -0.227f, 0.079f, 0.093f),

        NORTHERN_AFRICA(0.047f, -0.024f, 0.14f, 0.19f),
        SOUTHERN_AFRICA(0.15f, -0.44f, 0.086f, 0.24f),
        MADAGASCAR(0.26f, -0.48f, 0.028f, 0.09f),

        CHINA(0.592f, 0.135f, 0.11f, 0.145f)
    }

    val regionList = arrayListOf<Regions>(
            Regions.SIBERIA,
            Regions.EASTERN_RUSSIA,
            Regions.WESTERN_RUSSIA,
            Regions.USA,
            Regions.EASTERN_CANADA,
            Regions.WESTERN_CANADA,
            Regions.NORTHERN_CANADA,
            Regions.AUSTRALIA,
            Regions.GREENLAND,
            Regions.BRASILIA,
            Regions.ARGENTINA,
            Regions.COLUMBIA,
            Regions.NORTHERN_AFRICA,
            Regions.SOUTHERN_AFRICA,
            Regions.MADAGASCAR,
            Regions.CHINA)
    
}