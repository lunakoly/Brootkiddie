package ru.cryhards.brootkiddie.engine.scene.cam.behaviour

import ru.cryhards.brootkiddie.engine.scene.Viewable

/**
 * Created with love by luna_koly on 31.10.2017.
 */
interface CameraBehaviour {
    fun init()
    fun setCam(cam: Viewable)
}