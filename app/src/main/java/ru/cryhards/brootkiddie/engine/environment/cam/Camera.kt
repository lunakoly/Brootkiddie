package ru.cryhards.brootkiddie.engine.environment.cam

import android.view.MotionEvent
import ru.cryhards.brootkiddie.engine.android.EngineSurface
import ru.cryhards.brootkiddie.engine.environment.Object
import ru.cryhards.brootkiddie.engine.util.maths.Matrix4

/**
 * Object that has a Projection matrix
 * and can be used as a camera
 *
 * Created with love by luna_koly on 19.11.2017.
 */
abstract class Camera: Object() {
    /**
     * Represents the way that scene
     * is projected
     */
    abstract fun getProjectionMatrix() : Matrix4

    /**
     * Called when the camera is switched to
     */
    open fun onActivatedEvent(surface: EngineSurface): Camera {
        return this
    }

    /**
     * Used to track cursor events
     */
    open fun onTouchEvent(event: MotionEvent): Camera {
        return this
    }
}