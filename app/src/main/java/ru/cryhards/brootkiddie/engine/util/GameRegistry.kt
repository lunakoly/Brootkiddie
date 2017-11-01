package ru.cryhards.brootkiddie.engine.util

import android.content.Context
import ru.cryhards.brootkiddie.engine.android.EngineActivity
import ru.cryhards.brootkiddie.engine.android.GLSurface
import ru.cryhards.brootkiddie.engine.android.MainRenderer
import ru.cryhards.brootkiddie.engine.scene.Mesh
import ru.cryhards.brootkiddie.engine.scene.Viewable

/**
 * Created with love by luna_koly on 29.10.2017.
 */
class GameRegistry(var context: Context) {
    lateinit var renderer: MainRenderer
    lateinit var surface: GLSurface
    lateinit var activity: EngineActivity

    private var task: Task? = null

    fun setTask(task: Task) {
        this.task = task
    }

    fun runTask() {
        task?.execute(this)
    }

    val primaryLayer = ArrayList<Mesh>()
    lateinit var activeCamera: Viewable

    var environment = Environment()
}