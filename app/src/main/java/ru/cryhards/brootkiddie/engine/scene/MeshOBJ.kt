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
import java.nio.ShortBuffer

/**
 * Created with love by luna_koly on 10.11.2017.
 */
class MeshOBJ(
        private val vertices: FloatArray,
        private val vertexIndices: ShortArray,
//        private val vertexColors: FloatArray,
        private val vertexNormals: FloatArray) : Mesh {

    val shaderProgram = Shaders.OBJ
    val position = CoordProperty()
    var rotation = RotationProperty()

    private lateinit var vertexBuffer: FloatBuffer
    private lateinit var vertexIndicesBuffer: ShortBuffer
    private lateinit var vertexNormalsBuffer: FloatBuffer
//    private lateinit var vertexColorsBuffer: FloatBuffer

    init {
        genBuffers()
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
        Matrix.multiplyMM(modelMatrix, 0, translationMatrix, 0, rotationMatrix, 0)
        return modelMatrix
    }

    override fun draw(environment: Environment): Mesh {
        GLES30.glUseProgram(shaderProgram)

        val aPositionHandle = GLES30.glGetAttribLocation(shaderProgram, "aPosition")
        GLES30.glEnableVertexAttribArray(aPositionHandle)
        GLES30.glVertexAttribPointer(aPositionHandle, 3, GLES30.GL_FLOAT, false, 0, vertexBuffer)

        val aSurfaceNormalHandle = GLES30.glGetAttribLocation(shaderProgram, "aSurfaceNormal")
        GLES30.glEnableVertexAttribArray(aSurfaceNormalHandle)
        GLES30.glVertexAttribPointer(aSurfaceNormalHandle, 3, GLES30.GL_FLOAT, false, 0, vertexNormalsBuffer)

//        val aColorHandle = GLES30.glGetAttribLocation(shaderProgram, "aColor")
//        GLES30.glEnableVertexAttribArray(aColorHandle)
//        GLES30.glVertexAttribPointer(aColorHandle, 4, GLES30.GL_FLOAT, false, 0, vertexColorsBuffer)

        val modelMatrix = getMatrix()
        val uMMatrixHandle = GLES30.glGetUniformLocation(shaderProgram, "uMMatrix")
        val inverted = modelMatrix.clone()
        Matrix.invertM(inverted, 0, inverted, 0)
        GLES30.glUniformMatrix4fv(uMMatrixHandle, 1, false, inverted, 0)

        val uMVMatrixHandle = GLES30.glGetUniformLocation(shaderProgram, "uMVMatrix")
        GLES30.glUniformMatrix4fv(uMVMatrixHandle, 1, false, environment.mvpMatrix, 0)

        Matrix.multiplyMM(modelMatrix, 0, environment.mvpMatrix, 0, modelMatrix, 0)
        val uMVPMatrixHandle = GLES30.glGetUniformLocation(shaderProgram, "uMVPMatrix")
        GLES30.glUniformMatrix4fv(uMVPMatrixHandle, 1, false, modelMatrix, 0)

        val uAmbientLightHandle = GLES30.glGetUniformLocation(shaderProgram, "uAmbientLight")
        GLES30.glUniform3f(uAmbientLightHandle,
                environment.ambientLight.x.value,
                environment.ambientLight.y.value,
                environment.ambientLight.z.value)

        val uSunlightHandle = GLES30.glGetUniformLocation(shaderProgram, "uSunlight")
        GLES30.glUniform3f(uSunlightHandle,
                environment.sunlight.x.value,
                environment.sunlight.y.value,
                environment.sunlight.z.value)

        val uSunDirectionHandle = GLES30.glGetUniformLocation(shaderProgram, "uSunDirection")
        GLES30.glUniform3f(uSunDirectionHandle,
                environment.sunDirection.x.value,
                environment.sunDirection.y.value,
                environment.sunDirection.z.value)

        GLES30.glDrawElements(GLES30.GL_TRIANGLES, vertexIndices.size, GLES30.GL_UNSIGNED_SHORT, vertexIndicesBuffer)
        GLES30.glDisableVertexAttribArray(aPositionHandle)
        return this
    }

    override fun genBuffers(): Mesh {
        var bb = ByteBuffer.allocateDirect(vertices.size * 4)  //  4 vertices * 3 coordinates * 4 bytes
        bb.order(ByteOrder.nativeOrder())
        vertexBuffer = bb.asFloatBuffer()
        vertexBuffer.put(vertices)
        vertexBuffer.position(0)

        bb = ByteBuffer.allocateDirect(vertexIndices.size * 2)
        bb.order(ByteOrder.nativeOrder())
        vertexIndicesBuffer = bb.asShortBuffer()
        vertexIndicesBuffer.put(vertexIndices)
        vertexIndicesBuffer.position(0)

        bb = ByteBuffer.allocateDirect(vertexNormals.size * 4)
        bb.order(ByteOrder.nativeOrder())
        vertexNormalsBuffer = bb.asFloatBuffer()
        vertexNormalsBuffer.put(vertexNormals)
        vertexNormalsBuffer.position(0)

//        bb = ByteBuffer.allocateDirect(vertexColors.size * 4)
//        bb.order(ByteOrder.nativeOrder())
//        vertexColorsBuffer = bb.asFloatBuffer()
//        vertexColorsBuffer.put(vertexColors)
//        vertexColorsBuffer.position(0)
        return this
    }

    override fun build(src: FloatArray): Mesh {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}