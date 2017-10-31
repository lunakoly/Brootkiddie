package ru.cryhards.brootkiddie.engine.scene.cam.behaviour

import android.view.MotionEvent
import ru.cryhards.brootkiddie.engine.android.EngineActivity
import ru.cryhards.brootkiddie.engine.scene.CameraBehaviour
import ru.cryhards.brootkiddie.engine.scene.Viewable
import ru.cryhards.brootkiddie.engine.scene.cam.FPSCamera

/**
 * Created with love by luna_koly on 31.10.2017.
 */
class BasicFPS(private val activity: EngineActivity) : CameraBehaviour {
    private lateinit var cam: Viewable

    override fun setCam(cam: Viewable) {
        this.cam = cam
    }

    private var dragStartX: Float = 0f
    private var dragStartY: Float = 0f
    private var oldHorizontal: Double = 0.0
    private var oldVertical: Double = 0.0

    override fun init() {
        activity.addOnTouchListener { event ->
            val theCam = cam as FPSCamera

            if (event.action == MotionEvent.ACTION_DOWN) {
                dragStartX = event.x
                dragStartY = event.y
                oldHorizontal = theCam.rotation.horizontal.value!!
                oldVertical = theCam.rotation.vertical.value!!
                return@addOnTouchListener
            }

            val dragEndX = event.x
            val dragEndY = event.y
            val dx = dragEndX - dragStartX
            val dy = dragEndY - dragStartY

            try {
                theCam.rotation.horizontal.value = oldHorizontal + dx / 1000
                theCam.rotation.vertical.value = oldVertical - dy / 1000
            } catch (e: Exception) {
                println("FUCK")
            }
        }
    }
}