package ru.cryhards.brootkiddie.engine.environment.util

import ru.cryhards.brootkiddie.engine.util.components.Component
import ru.cryhards.brootkiddie.engine.util.components.prop.NotNullableProperty
import ru.cryhards.brootkiddie.engine.util.components.prop.Vec3

/**
 * Holds information about object material
 *
 * Created with love by luna_koly on 10.12.2017.
 */
class Material(var shaderProgram: ShaderProgram): Component() {
    /**
     * Reference to texture object
     */
    var texture: Texture? = null
    /**
     * Color of ambient light reflection
     */
    var ambientLight = Vec3(1f, 1f, 1f)
    /**
     * Color of diffuse light reflection
     */
    var diffuseLight = Vec3(1f, 1f, 1f)
    /**
     * Color of specular light reflection
     */
    var specularLight = Vec3(1f, 1f, 1f)
    /**
     * Scatter coefficient
     */
    var shininess = NotNullableProperty(8.0f)
    /**
     * If 1.0 than object tends to be fully opaque
     */
    var opacity = NotNullableProperty(1.0f)
    /**
     * If 1.0 than object tends to be fully opaque
     */
    var type = NotNullableProperty(Materials.STICKER)
}