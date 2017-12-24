package ru.cryhards.brootkiddie

import android.view.MotionEvent
import ru.cryhards.brootkiddie.engine.environment.MeshFactory
import ru.cryhards.brootkiddie.engine.environment.Object
import ru.cryhards.brootkiddie.engine.environment.cam.Camera
import ru.cryhards.brootkiddie.engine.environment.cam.FPSCamera

/**
 * Created with love by luna_koly on 23.12.2017.
 */
class PickingFPSCam: FPSCamera() {
    override fun onTouchEvent(event: MotionEvent): Camera {
        if (event.action == MotionEvent.ACTION_UP) {
            val worldRay = getDirectionRay(event.x, event.y)
            val camOrigin = getGlobalPosition()
            val scene = getAbsoluteParent()

            val objs = scene.objects
            val adding = ArrayList<Object>()

            for (o in objs) {
                if (o.collider != null) {
                    val p = o.collider!!.intersect(camOrigin, worldRay)

                    if (p.w == 1f) {
                        val s = MeshFactory.loadObj(surface.context, "models/sphere.obj")
                        s.scale.x.value = 0.3f
                        s.scale.y.value = 0.3f
                        s.scale.z.value = 0.3f
                        s.transform.x.value = p.x
                        s.transform.y.value = p.y
                        s.transform.z.value = p.z
                        adding.add(s)
                    }
                }
            }

            surface.registry.renderer.addJob {
                adding.forEach { scene.objects.add(it) }
            }
        }

        return super.onTouchEvent(event)
    }
}