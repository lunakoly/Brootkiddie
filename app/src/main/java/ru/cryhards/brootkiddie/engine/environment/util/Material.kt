package ru.cryhards.brootkiddie.engine.environment.util

import ru.cryhards.brootkiddie.engine.util.components.prop.NotNullableProperty
import ru.cryhards.brootkiddie.engine.util.components.prop.Vec3

/**
 * Created with love by luna_koly on 10.12.2017.
 */
class Material(var shaderProgram: ShaderProgram) {
    var texture: Texture? = null
    var ambientLight = Vec3(1f, 1f, 1f)
    var diffuseLight = Vec3(1f, 1f, 1f)
    var specularLight = Vec3(1f, 1f, 1f)
    var shininess = NotNullableProperty(8.0f)
    var opacity = NotNullableProperty(1.0f)
}