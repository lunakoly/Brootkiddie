package ru.cryhards.brootkiddie.engine.environment

import android.opengl.GLES30
import android.opengl.Matrix
import ru.cryhards.brootkiddie.engine.environment.interfaces.Mesh
import ru.cryhards.brootkiddie.engine.util.MoreMatrix
import ru.cryhards.brootkiddie.engine.util.Shaders
import ru.cryhards.brootkiddie.engine.util.prop.CoordProperty
import ru.cryhards.brootkiddie.engine.util.prop.RotationProperty
import java.nio.FloatBuffer
import java.nio.ShortBuffer

/**
 * Created with love by luna_koly on 10.11.2017.
 */
class StaticObject(
        private val vertexBuffer: FloatBuffer,
        private val vertexIndicesSize: Int,
        private val vertexIndicesBuffer: ShortBuffer,
        private val vertexNormalsBuffer: FloatBuffer) : Mesh {

    var shaderProgram = Shaders.OBJ
    val position = CoordProperty()
    val rotation = RotationProperty()

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
        shaderProgram.use()

        val aPositionHandle = shaderProgram.setAttribute("aPosition", 3, GLES30.GL_FLOAT, vertexBuffer)
        val aSurfaceNormalHandle = shaderProgram.setAttribute("aSurfaceNormal", 3, GLES30.GL_FLOAT, vertexNormalsBuffer)

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

        shaderProgram.drawElements(GLES30.GL_TRIANGLES, vertexIndicesSize, GLES30.GL_UNSIGNED_SHORT, vertexIndicesBuffer)
        shaderProgram.disableAttribute(aPositionHandle)
        shaderProgram.disableAttribute(aSurfaceNormalHandle)
        return this
    }
}