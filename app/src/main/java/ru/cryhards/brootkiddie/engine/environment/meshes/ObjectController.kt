package ru.cryhards.brootkiddie.engine.environment.meshes

/**
 * Controls object behaviour
 *
 * Created with love by luna_koly on 17.12.2017.
 */
interface ObjectController {
    /**
     * Called when object is being
     * loaded the first time
     */
    fun init()
    /**
     * Called when scene loads
     */
    fun load()
    /**
     * Called when scene unloads
     */
    fun unload()
    /**
     * Called before drawing a frame
     */
    fun update(dt: Long)
}