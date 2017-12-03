package ru.cryhards.brootkiddie.engine.environment.cam.behaviour

import android.view.MotionEvent
import ru.cryhards.brootkiddie.engine.android.EngineRegistry
import ru.cryhards.brootkiddie.engine.environment.cam.FPSCamera
import ru.cryhards.brootkiddie.engine.environment.cam.FloatCamera
import ru.cryhards.brootkiddie.engine.environment.interfaces.CameraBehaviour
import ru.cryhards.brootkiddie.engine.environment.interfaces.Viewable

/**
 * Created with love by luna_koly on 03.12.2017.
 */
class BasicFloatBehaviour(private val registry: EngineRegistry) : CameraBehaviour {
    private lateinit var cam: Viewable

    override fun setCam(cam: Viewable) {
        this.cam = cam
    }

    private var dragStartX: Float = 0f
    private var dragStartY: Float = 0f
    private var oldHorizontal: Float = 0f
    private var oldVertical: Float = 0f

    override fun init() {
        registry.activity.addOnTouchListener { event ->
            val theCam = cam as FloatCamera

            if (event.action == MotionEvent.ACTION_DOWN) {
                dragStartX = event.x
                dragStartY = event.y
                oldHorizontal = theCam.position.x.value
                oldVertical = theCam.position.y.value
                return@addOnTouchListener
            }

            val dragEndX = event.x
            val dragEndY = event.y
            val dx = dragEndX - dragStartX
            val dy = dragEndY - dragStartY

            try {
                theCam.position.x.value = oldHorizontal - dx / 500
                theCam.position.y.value = oldVertical + dy / 500
            } catch (e: Exception) {}
        }
    }
}