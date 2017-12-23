package ru.cryhards.brootkiddie.engine.environment.cam

import android.view.MotionEvent

/**
 * Camera with common PC controls
 *
 * Created with love by luna_koly on 31.10.2017.
 */
open class FPSCamera: PerspectiveCamera() {
    // saving values
    private var dragStartX: Float = 0f
    private var dragStartY: Float = 0f
    private var oldHorizontal: Double = 0.0
    private var oldVertical: Double = 0.0

    override fun onTouchEvent(event: MotionEvent): Camera {
        if (event.action == MotionEvent.ACTION_DOWN) {
            dragStartX = event.x
            dragStartY = event.y
            oldHorizontal = rotation.horizontal.value
            oldVertical = rotation.vertical.value
            return this
        }

        val dragEndX = event.x
        val dragEndY = event.y
        val dx = dragEndX - dragStartX
        val dy = dragEndY - dragStartY

        try {
            rotation.horizontal.value = oldHorizontal + dx / 1000
            rotation.vertical.value = oldVertical - dy / 1000
        } catch (e: Exception) {}

        return this
    }
}