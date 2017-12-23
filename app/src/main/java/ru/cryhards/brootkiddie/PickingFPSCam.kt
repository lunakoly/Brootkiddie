package ru.cryhards.brootkiddie

import android.view.MotionEvent
import ru.cryhards.brootkiddie.engine.environment.cam.Camera
import ru.cryhards.brootkiddie.engine.environment.cam.FPSCamera
import ru.cryhards.brootkiddie.engine.util.Logger
import ru.cryhards.brootkiddie.engine.util.maths.Vector4

/**
 * Created with love by luna_koly on 23.12.2017.
 */
class PickingFPSCam: FPSCamera() {
    override fun onTouchEvent(event: MotionEvent): Camera {
        if (event.action == MotionEvent.ACTION_DOWN) {

            val srcRay = Vector4(
                    2 * (event.x) / surface.width - 1.0f,
                    -2 * (event.y) / surface.height + 1.0f,
                    1f, 0f)

            val eyeRay = getProjectionMatrix().invert().x(srcRay)
            eyeRay.z = 1f
            eyeRay.w = 0f

            val worldRay = getAbsoluteModelMatrix().x(srcRay).normalize()


            val objs = getAbsoluteParent().objects
            for (o in objs) {
//            val objectMat = o.getAbsoluteModelMatrix().invert()
            }

//        env.pMatrix = getProjectionMatrix()

//        println(event.x)
        }

        return super.onTouchEvent(event)
    }
}