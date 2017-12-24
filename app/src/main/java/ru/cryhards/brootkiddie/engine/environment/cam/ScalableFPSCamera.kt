package ru.cryhards.brootkiddie.engine.environment.cam

import android.util.Log
import android.view.MotionEvent

/**
 * Created by Dima on 24.12.2017.
 */
class ScalableFPSCamera : FPSCamera(){

    private var scaleD:Float = 0f
    private var oldHorizontal = 0f
    private var oldVertical = 0f
    private var scaleStartZ = -1f
    private val minZ = -10f
    private val maxZ = -0.1f

    override fun onTouchEvent(event: MotionEvent): Camera {
        if (event.pointerCount == 1) {
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

                transform.x.value = newX
                transform.y.value = newY
            } catch (e: Exception) {
            }

            return this
        }

        else if(event.pointerCount == 2)
        {
            val x1 = event.getX(0)
            val y1 = event.getY(0)
            val x2 = event.getX(1)
            val y2 = event.getY(1)
            if (event.actionMasked == MotionEvent.ACTION_POINTER_DOWN) {
                scaleD = (x1-x2)*(x1-x2) + (y1-y2)*(y1-y2)
                scaleStartZ = transform.z.value
                return this
            }

            val d = (x1-x2)*(x1-x2) + (y1-y2)*(y1-y2)
            val r = Math.sqrt((scaleD/d).toDouble()).toFloat()
            val z = r*scaleStartZ
            if  (z < maxZ && z > minZ) transform.z.value=z
            Log.d("scale", "" + z)
        }
        return this
    }

}