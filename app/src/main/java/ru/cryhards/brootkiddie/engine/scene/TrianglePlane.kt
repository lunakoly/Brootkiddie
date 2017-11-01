package ru.cryhards.brootkiddie.engine.scene

import android.opengl.GLES30
import android.opengl.Matrix
import ru.cryhards.brootkiddie.engine.util.Environment
import ru.cryhards.brootkiddie.engine.util.MoreMatrix
import ru.cryhards.brootkiddie.engine.util.Shaders
import ru.cryhards.brootkiddie.engine.util.prop.CoordProperty
import ru.cryhards.brootkiddie.engine.util.prop.RotationProperty
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

/**
 * Created with love by luna_koly on 29.10.2017.
 */
class TrianglePlane : Mesh {
    val shaderProgram = Shaders.BASIC
    val position = CoordProperty()
    var rotation = RotationProperty()
    val v1 = CoordProperty()
    val v2 = CoordProperty()
    val v3 = CoordProperty()

    private lateinit var vertexBuffer: FloatBuffer
    private val testColor = floatArrayOf(0.7f, 0.7f, 1.0f, 1.0f)

    override fun draw(environment: Environment): Mesh {
        GLES30.glUseProgram(shaderProgram)

        val aPositionHandle = GLES30.glGetAttribLocation(shaderProgram, "aPosition")
        GLES30.glEnableVertexAttribArray(aPositionHandle)
        GLES30.glVertexAttribPointer(aPositionHandle, 3, GLES30.GL_FLOAT, false, 3 * 4, vertexBuffer)

        val uColorHandle = GLES30.glGetUniformLocation(shaderProgram, "uColor")
        GLES30.glUniform4fv(uColorHandle, 1, testColor, 0)

        val modelMatrix = getMatrix()
        Matrix.multiplyMM(modelMatrix, 0, environment.mvpMatrix, 0, modelMatrix, 0)

        val uMVPMatrixHandle = GLES30.glGetUniformLocation(shaderProgram, "uMVPMatrix")
        GLES30.glUniformMatrix4fv(uMVPMatrixHandle, 1, false, modelMatrix, 0)

        val uAmbientLightHandle = GLES30.glGetUniformLocation(shaderProgram, "uAmbientLight")
        GLES30.glUniform3f(uAmbientLightHandle,
                environment.ambientLight.x.value,
                environment.ambientLight.y.value,
                environment.ambientLight.z.value)

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

    private fun getCoordArray(): FloatArray {
        return floatArrayOf(
                v1.x.value, v1.y.value, v1.z.value,
                v2.x.value, v2.y.value, v2.z.value,
                v3.x.value, v3.y.value, v3.z.value
        )
    }

    override fun getMatrix(): FloatArray {
        val rotationMatrix = MoreMatrix.getLookAroundRotationM(
                rotation.horizontal.value,
                rotation.vertical.value)
        val translationMatrix = MoreMatrix.getTranslationM(
                position.x.value,
                position.y.value,
                position.z.value)

        val modelMatrix = FloatArray(16)
        Matrix.multiplyMM(modelMatrix, 0, rotationMatrix, 0, translationMatrix, 0)
        return modelMatrix
    }
}