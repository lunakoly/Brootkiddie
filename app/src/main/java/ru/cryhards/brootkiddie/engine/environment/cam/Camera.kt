package ru.cryhards.brootkiddie.engine.environment.cam

import android.view.MotionEvent
import ru.cryhards.brootkiddie.engine.android.EngineSurface
import ru.cryhards.brootkiddie.engine.environment.Object
import ru.cryhards.brootkiddie.engine.util.maths.Matrix4

/**
 * Created with love by luna_koly on 19.11.2017.
 */
abstract class Camera: Object() {
    abstract fun getProjectionMatrix() : Matrix4

    open fun onActivatedEvent(surface: EngineSurface): Camera {
        return this
    }

    open fun onTouchEvent(event: MotionEvent): Camera {
        return this
    }
}