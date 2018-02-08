package ru.cryhards.brootkiddie.screens.globalmap

import ru.cryhards.brootkiddie.ui.Cropper
import ru.cryhards.brootkiddie.ui.ImageActor

@Suppress("unused")
/**
 * Incapsulates global map object logic
 */
class GlobalMap : ImageActor("img/bg/map.jpg") {

    /**
     * Returns a rair of random coordinates within
     * a specified region
     */
    @Suppress("unused")
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
    
}