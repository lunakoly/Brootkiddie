package ru.cryhards.brootkiddie.screens

/**
 * Utility object that has methods for aligning content
 */
object Cropper {

    /**
     * Aligns target rectangle to the center of viewport
     * Returns target bounds [ x y width height ] according to lower
     * left corner
     */
    fun fitCenter(
            viewportWidth: Float,
            viewportHeight: Float,
            targetWidth: Float,
            targetHeight: Float): FloatArray {

        val viewportAspect = viewportWidth / viewportHeight
        val targetAspect = targetWidth / targetHeight

        return if (targetAspect >= viewportAspect) {
            val bounds = FloatArray(4)
            bounds[3] = viewportHeight
            bounds[2] = viewportHeight * targetAspect
            bounds[1] = 0f
            bounds[0] = (viewportWidth - bounds[2]) / 2f
            bounds
        } else {
            val bounds = FloatArray(4)
            bounds[2] = viewportWidth
            bounds[3] = viewportWidth / targetAspect
            bounds[0] = 0f
            bounds[1] = (viewportHeight - bounds[3]) / 2f
            bounds
        }
    }

}