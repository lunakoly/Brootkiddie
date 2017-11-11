package ru.cryhards.brootkiddie.engine.util

import ru.cryhards.brootkiddie.engine.android.EngineRegistry

/**
 * Created with love by luna_koly on 29.10.2017.
 */
interface Task {
    fun execute(registry: EngineRegistry)
}