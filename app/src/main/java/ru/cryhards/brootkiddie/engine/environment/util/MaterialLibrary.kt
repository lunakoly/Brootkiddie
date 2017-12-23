package ru.cryhards.brootkiddie.engine.environment.util

import ru.cryhards.brootkiddie.engine.util.components.Component

/**
 * Holds information about object material
 *
 * Created with love by luna_koly on 10.12.2017.
 */
class MaterialLibrary(var shaderProgram: ShaderProgram) : Component() {
    var materials: HashMap<String, Material> = HashMap()
}