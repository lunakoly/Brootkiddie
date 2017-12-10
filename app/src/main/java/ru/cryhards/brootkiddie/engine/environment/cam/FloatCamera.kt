package ru.cryhards.brootkiddie.engine.environment.cam

import android.view.MotionEvent

/**
 * Created with love by luna_koly on 03.12.2017.
 */
class FloatCamera: FrustumCamera() {
    private var dragStartX: Float = 0f
    private var dragStartY: Float = 0f
    private var oldHorizontal: Float = 0f
    private var oldVertical: Float = 0f

    override fun onTouchEvent(event: MotionEvent): Camera {
        if (event.action == MotionEvent.ACTION_DOWN) {
            dragStartX = event.x
            dragStartY = event.y
            oldHorizontal = transform.x.value
            oldVertical = transform.y.value
            return this
        }

        val dragEndX = event.x
        val dragEndY = event.y
        val dx = dragEndX - dragStartX
        val dy = dragEndY - dragStartY

        try {
            transform.x.value = oldHorizontal - dx / 500
            transform.y.value = oldVertical + dy / 500
        } catch (e: Exception) {}

        return this
    }
}