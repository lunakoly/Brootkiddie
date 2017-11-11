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
 * Created with love by luna_koly on 29.10.2017.
 */
class RectangleColoredObject(
        @Suppress("MemberVisibilityCanPrivate") val v1: CoordProperty,
        @Suppress("MemberVisibilityCanPrivate")val v2: CoordProperty,
        @Suppress("MemberVisibilityCanPrivate")val v3: CoordProperty,
        @Suppress("MemberVisibilityCanPrivate")val v4: CoordProperty) : Mesh {

    constructor(src: FloatArray) : this(
            CoordProperty(src[0], src[1], src[2]),
            CoordProperty(src[3], src[4], src[5]),
            CoordProperty(src[6], src[7], src[8]),
            CoordProperty(src[9], src[10], src[11]))

    @Suppress("unused")
    constructor() : this(
            CoordProperty(0.0f, 0.0f, 0.0f),
            CoordProperty(0.0f, 0.0f, 0.0f),
            CoordProperty(0.0f, 0.0f, 0.0f),
            CoordProperty(0.0f, 0.0f, 0.0f))

    @Suppress("MemberVisibilityCanPrivate")
    val shaderProgram = Shaders.COLOR_TRANSITION
    val position = CoordProperty()
    var rotation = RotationProperty()
    private var surfaceNormal = FloatArray(12)

    @Suppress("MemberVisibilityCanPrivate")
    var testColor = floatArrayOf(
            0.7f, 0.7f, 0.0f, 1.0f,
            0.7f, 0.0f, 0.7f, 1.0f,
            0.0f, 0.7f, 0.7f, 1.0f,
            1.0f, 1.0f, 1.0f, 1.0f)

    init {
        genNormal()
    }

    private lateinit var vertexBuffer: FloatBuffer
    private lateinit var vertexIndicesBuffer: ShortBuffer
    private lateinit var vertexColorsBuffer: FloatBuffer
    private lateinit var vertexNormalsBuffer: FloatBuffer

    private val vertexIndices = shortArrayOf(0, 1, 2, 0, 2, 3)

    override fun genBuffers(): Mesh {
        var bb = ByteBuffer.allocateDirect(48)  //  4 vertices * 3 coordinates * 4 bytes
        bb.order(ByteOrder.nativeOrder())
        vertexBuffer = bb.asFloatBuffer()
        vertexBuffer.put(genCoordArray())
        vertexBuffer.position(0)

        bb = ByteBuffer.allocateDirect(12) // vertexIndices.size * 2
        bb.order(ByteOrder.nativeOrder())
        vertexIndicesBuffer = bb.asShortBuffer()
        vertexIndicesBuffer.put(vertexIndices)
        vertexIndicesBuffer.position(0)

        bb = ByteBuffer.allocateDirect(surfaceNormal.size * 4)
        bb.order(ByteOrder.nativeOrder())
        vertexNormalsBuffer = bb.asFloatBuffer()
        vertexNormalsBuffer.put(surfaceNormal)
        vertexNormalsBuffer.position(0)

        bb = ByteBuffer.allocateDirect(64) // color * values per color * 4 bytes
        bb.order(ByteOrder.nativeOrder())
        vertexColorsBuffer = bb.asFloatBuffer()
        vertexColorsBuffer.put(testColor)
        vertexColorsBuffer.position(0)
        return this
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

    private fun genNormal(): RectangleColoredObject {
        val vec1 = floatArrayOf(
                v2.z.value - v1.z.value,
                v2.y.value - v1.y.value,
                v2.z.value - v1.z.value
        )
        val vec2 = floatArrayOf(
                v3.x.value - v1.x.value,
                v3.y.value - v1.y.value,
                v3.z.value - v1.z.value
        )

        surfaceNormal[0] = vec1[1] * vec2[2] - vec2[1] * vec1[2]
        surfaceNormal[1] = vec1[2] * vec2[0] - vec2[2] * vec1[0]
        surfaceNormal[2] = vec1[0] * vec2[1] - vec2[0] * vec1[1]

        surfaceNormal[3] = surfaceNormal[0]
        surfaceNormal[4] = surfaceNormal[1]
        surfaceNormal[5] = surfaceNormal[2]

        surfaceNormal[6] = surfaceNormal[0]
        surfaceNormal[7] = surfaceNormal[1]
        surfaceNormal[8] = surfaceNormal[2]

        surfaceNormal[9] = surfaceNormal[0]
        surfaceNormal[10] = surfaceNormal[1]
        surfaceNormal[11] = surfaceNormal[2]
        return this
    }

    private fun genCoordArray(): FloatArray {
        return floatArrayOf(
                v1.x.value, v1.y.value, v1.z.value,
                v2.x.value, v2.y.value, v2.z.value,
                v3.x.value, v3.y.value, v3.z.value,
                v4.x.value, v4.y.value, v4.z.value
        )
    }

    override fun draw(environment: Environment): Mesh {
        shaderProgram.use()

        val aPositionHandle = shaderProgram.setAttribute("aPosition", 3, GLES30.GL_FLOAT, vertexBuffer)
        val aSurfaceNormalHandle = shaderProgram.setAttribute("aSurfaceNormal", 3, GLES30.GL_FLOAT, vertexNormalsBuffer)
        val aColorHandle = shaderProgram.setAttribute("aColor", 4, GLES30.GL_FLOAT, vertexColorsBuffer)

        val modelMatrix = getMatrix()
        val inverted = modelMatrix.clone()
        Matrix.invertM(inverted, 0, inverted, 0)
        shaderProgram.setUniformMatrix4fv("uMMatrix", inverted)
        shaderProgram.setUniformMatrix4fv("uMVMatrix", environment.mvpMatrix)
        Matrix.multiplyMM(modelMatrix, 0, environment.mvpMatrix, 0, modelMatrix, 0)
        shaderProgram.setUniformMatrix4fv("uMVPMatrix", modelMatrix)

        shaderProgram.setUniform3f("uAmbientLight",
                environment.ambientLight.x.value,
                environment.ambientLight.y.value,
                environment.ambientLight.z.value)

        shaderProgram.setUniform3f("uSunlight",
                environment.sunlight.x.value,
                environment.sunlight.y.value,
                environment.sunlight.z.value)

        shaderProgram.setUniform3f("uSunDirection",
                environment.sunDirection.x.value,
                environment.sunDirection.y.value,
                environment.sunDirection.z.value)

        shaderProgram.drawElements(GLES30.GL_TRIANGLES, 6, GLES30.GL_UNSIGNED_SHORT, vertexIndicesBuffer)
        shaderProgram.disableAttribute(aPositionHandle)
        shaderProgram.disableAttribute(aSurfaceNormalHandle)
        shaderProgram.disableAttribute(aColorHandle)
        return this
    }
}