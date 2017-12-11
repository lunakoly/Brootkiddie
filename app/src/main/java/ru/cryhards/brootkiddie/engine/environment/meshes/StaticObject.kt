package ru.cryhards.brootkiddie.engine.environment.meshes

import android.opengl.GLES30
import android.util.Log
import ru.cryhards.brootkiddie.engine.environment.Environment
import ru.cryhards.brootkiddie.engine.environment.Object
import ru.cryhards.brootkiddie.engine.environment.util.Material
import ru.cryhards.brootkiddie.engine.util.Logger
import ru.cryhards.brootkiddie.engine.util.components.Rotation
import ru.cryhards.brootkiddie.engine.util.maths.Mat4
import java.nio.FloatBuffer
import java.nio.ShortBuffer

/**
 * Created with love by luna_koly on 10.11.2017.
 */
class StaticObject(
        private val vertexBuffer: FloatBuffer,
        private val vertexIndicesSize: Int,
        private val vertexIndicesBuffer: ShortBuffer,
        private val vertexNormalsBuffer: FloatBuffer,
        private val textureUVsBuffer: FloatBuffer,
        var material: Material) : Object() {

    val rotation = Rotation()

    init {
        components.add(rotation)
    }


    override fun getModelMatrix(): Mat4 {
        val rotationMatrix = Mat4.lookAroundRotation(
                rotation.horizontal.value,
                rotation.vertical.value)
        val translationMatrix = Mat4.translate(
                transform.x.value,
                transform.y.value,
                transform.z.value)
        return translationMatrix.multiply(rotationMatrix)
    }

    override fun draw(environment: Environment, parentModelMatrix: Mat4): Object {
        val prog = material.shaderProgram
        prog.use()

        val aPositionHandle = prog.setAttribute("aPosition", 3, GLES30.GL_FLOAT, vertexBuffer)
        val aSurfaceNormalHandle = prog.setAttribute("aSurfaceNormal", 3, GLES30.GL_FLOAT, vertexNormalsBuffer)

        var aTextureCoordHandle: Int? = null
        if (material.texture != null)
            aTextureCoordHandle = prog.setAttribute("aTextureCoord", 2, GLES30.GL_FLOAT, textureUVsBuffer)

        var modelMatrix = parentModelMatrix.multiply(getModelMatrix())
        val inverted = modelMatrix.invert()!!
        prog.setUniformMatrix4fv("uMMatrix", inverted.m)
        prog.setUniformMatrix4fv("uMVMatrix", environment.mvpMatrix.m)
        modelMatrix = environment.mvpMatrix.multiply(modelMatrix)
        prog.setUniformMatrix4fv("uMVPMatrix", modelMatrix.m)

        prog.setUniform3f("uAmbientLight",
                environment.ambientLight.x.value,
                environment.ambientLight.y.value,
                environment.ambientLight.z.value)

        prog.setUniform3f("uSunlight",
                environment.sunlight.x.value,
                environment.sunlight.y.value,
                environment.sunlight.z.value)

        prog.setUniform3f("uSunDirection",
                environment.sunDirection.x.value,
                environment.sunDirection.y.value,
                environment.sunDirection.z.value)

        prog.setUniform3f("uEyePosition",
                environment.eyePosition.x.value,
                environment.eyePosition.y.value,
                environment.eyePosition.z.value)

        prog.setUniformMatrix4fv("uEyePositionMatrix", environment.activeCameraPositionMatrix.m)

        prog.drawElements(GLES30.GL_TRIANGLES, vertexIndicesSize, GLES30.GL_UNSIGNED_SHORT, vertexIndicesBuffer)
        prog.disableAttribute(aPositionHandle)
        prog.disableAttribute(aSurfaceNormalHandle)

        if (aTextureCoordHandle != null)
            prog.disableAttribute(aTextureCoordHandle)

        return this
    }
}