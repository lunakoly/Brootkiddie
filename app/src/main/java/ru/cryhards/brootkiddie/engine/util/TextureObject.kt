package ru.cryhards.brootkiddie.engine.util

/**
 * Created with love by luna_koly on 19.11.2017.
 */
class TextureObject(
        private val frames: IntArray, var duration: Long,
        val frameWidth: Int, val frameHeight: Int) {

    fun getFrame(time: Long): Int {
        if (frames.size == 1)
            return frames[0]

        val status = (time % duration) / duration.toDouble()
        return frames[Math.floor(status * frames.size).toInt()]
    }

    fun getFrame(index: Int): Int = frames[index]
}