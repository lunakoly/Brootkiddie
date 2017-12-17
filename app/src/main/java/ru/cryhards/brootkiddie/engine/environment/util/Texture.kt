package ru.cryhards.brootkiddie.engine.environment.util

/**
 * Object that holds reference to the texture
 * And manages raster animations
 *
 * Created with love by luna_koly on 19.11.2017.
 */
class Texture(
        private val frames: IntArray,
        @Suppress("MemberVisibilityCanPrivate") var period: Long,
        @Suppress("unused") val frameWidth: Int,
        @Suppress("unused")val frameHeight: Int) {

    /**
     * Returns frame for a particular time moment
     */
    fun getFrame(time: Long): Int {
        if (frames.size == 1)
            return frames[0]

        val status = (time % period) / period.toDouble()
        return frames[Math.floor(status * frames.size).toInt()]
    }

    /**
     * Returns frame by index
     */
    @Suppress("unused")
    fun getFrame(index: Int): Int = frames[index]
}