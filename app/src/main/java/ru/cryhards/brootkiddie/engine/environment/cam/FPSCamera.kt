package ru.cryhards.brootkiddie.engine.environment.cam

import android.view.MotionEvent

/**
 * Camera with common PC controls
 *
 * Created with love by luna_koly on 31.10.2017.
 */
open class FPSCamera: PerspectiveCamera() {
    // saving values
    var dragStartX: Float = 0f
    var dragStartY: Float = 0f
    var oldHorizontalR: Double = 0.0
    var oldVerticalR: Double = 0.0

    override fun onTouchEvent(event: MotionEvent): Camera {
        if (event.action == MotionEvent.ACTION_DOWN) {
            dragStartX = event.x
            dragStartY = event.y
            oldHorizontalR = rotation.horizontal.value
            oldVerticalR = rotation.vertical.value
            return this
        }

        val dragEndX = event.x
        val dragEndY = event.y
        val dx = dragEndX - dragStartX
        val dy = dragEndY - dragStartY

        try {
            rotation.horizontal.value = oldHorizontalR + dx / 1000
            rotation.vertical.value = oldVerticalR - dy / 1000
        } catch (e: Exception) {}

        return this
    }
}