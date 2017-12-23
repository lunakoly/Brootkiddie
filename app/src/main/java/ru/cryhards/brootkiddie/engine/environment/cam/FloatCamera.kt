package ru.cryhards.brootkiddie.engine.environment.cam

import android.view.MotionEvent
import ru.cryhards.brootkiddie.engine.util.components.prop.NullableProperty

/**
 * Camera with 'observing' controls
 *
 * Created with love by luna_koly on 03.12.2017.
 */
open class FloatCamera: OrthographicCamera() {
    // saving values
    private var dragStartX: Float = 0f
    private var dragStartY: Float = 0f
    private var oldHorizontal: Float = 0f
    private var oldVertical: Float = 0f

    /**
     * Sets max X coordinate that camera
     * cannot move through
     */
    var maxX = NullableProperty<Float>(null)
    /**
     * Sets min X coordinate that camera
     * cannot move through
     */
    var minX = NullableProperty<Float>(null)
    /**
     * Sets max Y coordinate that camera
     * cannot move through
     */
    var maxY = NullableProperty<Float>(null)
    /**
     * Sets min Y coordinate that camera
     * cannot move through
     */
    var minY = NullableProperty<Float>(null)


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
            var newX = oldHorizontal - dx / 500
            var newY = oldVertical + dy / 500

            if (maxX.value != null && newX > maxX.value!!)
                newX = maxX.value!!
            else if (minX.value != null && newX < minX.value!!)
                newX = minX.value!!

            if (maxY.value != null && newY > maxY.value!!)
                newY = maxY.value!!
            else if (minY.value != null && newY < minY.value!!)
                newY = minY.value!!

            transform.x.value = newX
            transform.y.value = newY
        } catch (e: Exception) {}

        return this
    }
}