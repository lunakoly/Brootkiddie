package ru.cryhards.brootkiddie.engine.shapes

import android.opengl.GLES30
import ru.cryhards.brootkiddie.engine.util.Shaders
import ru.cryhards.brootkiddie.engine.util.prop.CoordProperty
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

/**
 * Created with love by luna_koly on 29.10.2017.
 */
class TrianglePlane : Mesh {
    val shaderProgram = Shaders.BASIC
    val position = CoordProperty()
    val v1 = CoordProperty()
    val v2 = CoordProperty()
    val v3 = CoordProperty()

    private lateinit var vertexBuffer: FloatBuffer
    private val testColor = floatArrayOf(0.7f, 0.7f, 1.0f, 1.0f)

    private fun getCoordArray(): FloatArray {
        return floatArrayOf(
                v1.x.value!!, v1.y.value!!, v1.z.value!!,
                v2.x.value!!, v2.y.value!!, v2.z.value!!,
                v3.x.value!!, v3.y.value!!, v3.z.value!!
        )
    }

    override fun draw(): Mesh {
        GLES30.glUseProgram(shaderProgram)

        val aPositionHandle = GLES30.glGetAttribLocation(shaderProgram, "aPosition")
        GLES30.glEnableVertexAttribArray(aPositionHandle)
        GLES30.glVertexAttribPointer(aPositionHandle, 3, GLES30.GL_FLOAT, false, 3 * 4, vertexBuffer)

        val uColorHandle = GLES30.glGetUniformLocation(shaderProgram, "uColor")
        GLES30.glUniform4fv(uColorHandle, 1, testColor, 0)

        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, 3)
        GLES30.glDisableVertexAttribArray(aPositionHandle)
        return this
    }

    override fun genBuffers(): Mesh {
        val bb = ByteBuffer.allocateDirect(36)  //  3 vertices * 3 coordinates * 4 bites
        bb.order(ByteOrder.nativeOrder())

        vertexBuffer = bb.asFloatBuffer()
        vertexBuffer.put(getCoordArray())
        vertexBuffer.position(0)
        return this
    }

    override fun build(floatArrayOf: FloatArray): Mesh {
        v1.x.value = floatArrayOf[0]
        v1.y.value = floatArrayOf[1]
        v1.z.value = floatArrayOf[2]

        v2.x.value = floatArrayOf[3]
        v2.y.value = floatArrayOf[4]
        v2.z.value = floatArrayOf[5]

        v3.x.value = floatArrayOf[6]
        v3.y.value = floatArrayOf[7]
        v3.z.value = floatArrayOf[8]
        return this
    }
}