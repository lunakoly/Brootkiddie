package ru.cryhards.brootkiddie.engine.environment

import android.opengl.GLES30
import android.opengl.Matrix
import ru.cryhards.brootkiddie.engine.environment.interfaces.Mesh
import ru.cryhards.brootkiddie.engine.util.MoreMatrix
import ru.cryhards.brootkiddie.engine.util.Shaders
import ru.cryhards.brootkiddie.engine.util.prop.CoordProperty
import ru.cryhards.brootkiddie.engine.util.prop.RotationProperty
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.nio.ShortBuffer

/**
 * Created with love by luna_koly on 10.11.2017.
 */
class StaticColoredObject(
        private val vertexBuffer: FloatBuffer,
        private val vertexIndicesSize: Int,
        private val vertexIndicesBuffer: ShortBuffer,
        private val vertexNormalsBuffer: FloatBuffer,
        private val vertexColorsBuffer: FloatBuffer) : Mesh {

    private val delegate = StaticObject(
            vertexBuffer,
            vertexIndicesSize,
            vertexIndicesBuffer,
            vertexNormalsBuffer)

    var shaderProgram = Shaders.OBJ_COLOR
        set(value) {
            delegate.shaderProgram = value
        }
    val position = delegate.position
    val rotation = delegate.rotation

    init {
        delegate.shaderProgram = shaderProgram
    }

    override fun getMatrix(): FloatArray {
        return delegate.getMatrix()
    }

    override fun draw(environment: Environment): Mesh {
        delegate.draw(environment)
        // TODO
        return this
    }
}