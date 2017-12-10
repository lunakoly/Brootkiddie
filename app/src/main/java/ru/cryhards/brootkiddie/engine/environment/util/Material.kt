package ru.cryhards.brootkiddie.engine.environment.util

import android.opengl.GLES30
import ru.cryhards.brootkiddie.engine.util.components.prop.NotNullableProperty
import ru.cryhards.brootkiddie.engine.util.components.prop.Vec3

/**
 * Created with love by luna_koly on 10.12.2017.
 */
class Material(var shaderProgram: ShaderProgram) {
    var texture: Texture? = null
    var ambientLight = Vec3(0f, 0f, 0f)
    var diffuseLight = Vec3(0f, 0f, 0f)
    var specularLight = Vec3(0f, 0f, 0f)
    var shininess = NotNullableProperty(0.0f)
    var transparency = NotNullableProperty(0.0f)
}