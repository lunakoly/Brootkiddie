package ru.cryhards.brootkiddie.engine.environment

import android.opengl.GLES30
import ru.cryhards.brootkiddie.engine.environment.interfaces.Mesh
import ru.cryhards.brootkiddie.engine.util.Shaders
import ru.cryhards.brootkiddie.engine.util.maths.Mat4
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
    override val position = CoordProperty()
    val rotation = RotationProperty()

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

    override fun draw(environment: Environment): Mesh {
        shaderProgram.use()

        val aPositionHandle = shaderProgram.setAttribute("aPosition", 3, GLES30.GL_FLOAT, vertexBuffer)
        val aSurfaceNormalHandle = shaderProgram.setAttribute("aSurfaceNormal", 3, GLES30.GL_FLOAT, vertexNormalsBuffer)

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

        shaderProgram.setUniform3f("uEyePosition",
                environment.activeCamera.position.x.value,
                environment.activeCamera.position.y.value,
                environment.activeCamera.position.z.value)

        shaderProgram.setUniformMatrix4fv("uEyePositionMatrix", environment.activeCameraPositionMatrix.m)

        shaderProgram.drawElements(GLES30.GL_TRIANGLES, vertexIndicesSize, GLES30.GL_UNSIGNED_SHORT, vertexIndicesBuffer)
        shaderProgram.disableAttribute(aPositionHandle)
        shaderProgram.disableAttribute(aSurfaceNormalHandle)
        return this
    }
}