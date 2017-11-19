package ru.cryhards.brootkiddie.engine.android

import android.content.Context
import ru.cryhards.brootkiddie.engine.environment.Environment
import ru.cryhards.brootkiddie.engine.environment.interfaces.Mesh
import ru.cryhards.brootkiddie.engine.util.Task

/**
 * Created with love by luna_koly on 29.10.2017.
 */
class EngineRegistry(var context: Context) {
    lateinit var renderer: EngineRenderer
    lateinit var surface: EngineSurface
    lateinit var activity: EngineActivity

    private var task: Task? = null

    fun setTask(task: Task) {
        this.task = task
    }

    fun runTask() {
        task?.execute(this)
    }

    val primaryLayer = ArrayList<Mesh>()
    var environment = Environment()
}