package ru.cryhards.brootkiddie.engine.environment

import android.opengl.GLES30
import ru.cryhards.brootkiddie.engine.environment.interfaces.Mesh
import ru.cryhards.brootkiddie.engine.util.Shaders
import ru.cryhards.brootkiddie.engine.util.maths.Mat4
import ru.cryhards.brootkiddie.engine.util.prop.CoordProperty
import ru.cryhards.brootkiddie.engine.util.prop.RotationProperty
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

/**
 * Created with love by luna_koly on 29.10.2017.
 */
class TriangleColoredObject(
        @Suppress("MemberVisibilityCanPrivate")val v1: CoordProperty,
        @Suppress("MemberVisibilityCanPrivate")val v2: CoordProperty,
        @Suppress("MemberVisibilityCanPrivate")val v3: CoordProperty) : Mesh {

    constructor(src: FloatArray) : this(
            CoordProperty(src[0], src[1], src[2]),
            CoordProperty(src[3], src[4], src[5]),
            CoordProperty(src[6], src[7], src[8]))

    @Suppress("unused")
    constructor() : this(
            CoordProperty(0.0f, 0.0f, 0.0f),
            CoordProperty(0.0f, 0.0f, 0.0f),
            CoordProperty(0.0f, 0.0f, 0.0f))

    @Suppress("MemberVisibilityCanPrivate")
    var shaderProgram = Shaders.COLOR_TRANSITION
    val position = CoordProperty()
    val rotation = RotationProperty()
    private var surfaceNormal = FloatArray(9)

    @Suppress("MemberVisibilityCanPrivate")
    var testColor = floatArrayOf(
            0.7f, 0.5f, 0.5f, 1.0f,
            0.5f, 0.7f, 0.5f, 1.0f,
            0.5f, 0.5f, 0.7f, 1.0f)

    init {
        genNormal()
    }

    private lateinit var vertexBuffer: FloatBuffer
    private lateinit var vertexColorsBuffer: FloatBuffer
    private lateinit var vertexNormalsBuffer: FloatBuffer

    override fun genBuffers(): Mesh {
        var bb = ByteBuffer.allocateDirect(36)  //  3 vertices * 3 coordinates * 4 bites
        bb.order(ByteOrder.nativeOrder())
        vertexBuffer = bb.asFloatBuffer()
        vertexBuffer.put(genCoordArray())
        vertexBuffer.position(0)

        bb = ByteBuffer.allocateDirect(surfaceNormal.size * 4)
        bb.order(ByteOrder.nativeOrder())
        vertexNormalsBuffer = bb.asFloatBuffer()
        vertexNormalsBuffer.put(surfaceNormal)
        vertexNormalsBuffer.position(0)

        bb = ByteBuffer.allocateDirect(testColor.size * 4)
        bb.order(ByteOrder.nativeOrder())
        vertexColorsBuffer = bb.asFloatBuffer()
        vertexColorsBuffer.put(testColor)
        vertexColorsBuffer.position(0)
        return this
    }

    override fun getMatrix(): Mat4 {
        val rotationMatrix = Mat4.lookAroundRotation(
                rotation.horizontal.value,
                rotation.vertical.value)
        val translationMatrix = Mat4.translate(
                position.x.value,
                position.y.value,
                position.z.value)
        return translationMatrix.multiply(rotationMatrix)
    }

    private fun genNormal(): TriangleColoredObject {
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
        return this
    }

    private fun genCoordArray(): FloatArray {
        return floatArrayOf(
                v1.x.value, v1.y.value, v1.z.value,
                v2.x.value, v2.y.value, v2.z.value,
                v3.x.value, v3.y.value, v3.z.value
        )
    }

    override fun draw(environment: Environment): Mesh {
        shaderProgram.use()

        val aPositionHandle = shaderProgram.setAttribute("aPosition", 3, GLES30.GL_FLOAT, vertexBuffer)
        val aSurfaceNormalHandle = shaderProgram.setAttribute("aSurfaceNormal", 3, GLES30.GL_FLOAT, vertexNormalsBuffer)
        val aColorHandle = shaderProgram.setAttribute("aColor", 4, GLES30.GL_FLOAT, vertexColorsBuffer)

        var modelMatrix = getMatrix()
        val inverted = modelMatrix.invert()!!
        shaderProgram.setUniformMatrix4fv("uMMatrix", inverted.m)
        shaderProgram.setUniformMatrix4fv("uMVMatrix", environment.mvpMatrix.m)
        modelMatrix = environment.mvpMatrix.multiply(modelMatrix)
        shaderProgram.setUniformMatrix4fv("uMVPMatrix", modelMatrix.m)

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

        shaderProgram.drawArrays(GLES30.GL_TRIANGLES, 0, 3)
        shaderProgram.disableAttribute(aPositionHandle)
        shaderProgram.disableAttribute(aSurfaceNormalHandle)
        shaderProgram.disableAttribute(aColorHandle)
        return this
    }
}