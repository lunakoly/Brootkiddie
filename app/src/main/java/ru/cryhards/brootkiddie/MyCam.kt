package ru.cryhards.brootkiddie

import android.view.MotionEvent
import ru.cryhards.brootkiddie.engine.environment.cam.Camera
import ru.cryhards.brootkiddie.engine.environment.cam.FloatCamera

/**
 * Created with love by luna_koly on 23.12.2017.
 */
class MyCam: FloatCamera() {

    override fun onTouchEvent(event: MotionEvent): Camera {
        println("Yea boy ur shit goes here")
        return super.onTouchEvent(event)
    }

}