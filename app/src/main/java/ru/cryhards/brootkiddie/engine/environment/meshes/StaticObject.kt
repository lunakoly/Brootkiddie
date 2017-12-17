package ru.cryhards.brootkiddie.engine.environment.meshes

import android.opengl.GLES30
import ru.cryhards.brootkiddie.engine.environment.Environment
import ru.cryhards.brootkiddie.engine.environment.Object
import ru.cryhards.brootkiddie.engine.environment.util.Material
import ru.cryhards.brootkiddie.engine.util.components.Rotation
import ru.cryhards.brootkiddie.engine.util.components.Scale
import ru.cryhards.brootkiddie.engine.util.maths.Matrix4
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
    val scale = Scale(1f, 1f, 1f)

    init {
        components.add(rotation)
        components.add(scale)
    }


    override fun getModelMatrix(): Matrix4 {
        val camScaleMatrix = Matrix4.getScale(
                scale.x.value,
                scale.y.value,
                scale.z.value)
        val camRotationMatrix = Matrix4.getFPSRotation(
                rotation.horizontal.value,
                rotation.vertical.value)
        val camTransitionMatrix = Matrix4.getTranslation(
                transform.x.value,
                transform.y.value,
                transform.z.value)
        return camTransitionMatrix
                .x(camRotationMatrix)
                .x(camScaleMatrix)
    }

    override fun draw(environment: Environment, parentModelMatrix: Matrix4): Object {
        val prog = material.shaderProgram
        prog.use()

        val aPositionHandle = prog.setAttribute("aPosition", 3, GLES30.GL_FLOAT, vertexBuffer)
        val aSurfaceNormalHandle = prog.setAttribute("aSurfaceNormal", 3, GLES30.GL_FLOAT, vertexNormalsBuffer)

        var aTextureCoordHandle: Int? = null
        if (material.texture != null)
            aTextureCoordHandle = prog.setAttribute("aTextureCoord", 2, GLES30.GL_FLOAT, textureUVsBuffer)

        val modelMatrix = parentModelMatrix.x(getModelMatrix())
        prog.setUniformMatrix4fv("uMMatrix", modelMatrix.convert())
        prog.setUniformMatrix4fv("uVMatrix", environment.vMatrix.convert())
        prog.setUniformMatrix4fv("uPMatrix", environment.pMatrix.convert())

        prog.setUniform1f("uScene.ambientCoefficient",
                environment.ambientCoefficient.value)

        prog.setUniform3f("uScene.sunlight",
                environment.sunlight.x.value,
                environment.sunlight.y.value,
                environment.sunlight.z.value)

        prog.setUniform4f("uScene.eyePosition",
                environment.eyePosition.x.value,
                environment.eyePosition.y.value,
                environment.eyePosition.z.value,
                1f) // POINT

        prog.setUniform4f("uSunDirection",
                environment.sunDirection.x.value,
                environment.sunDirection.y.value,
                environment.sunDirection.z.value,
                0f) // VECTOR

        prog.setUniform3f("uMaterial.ambientLight",
                material.ambientLight.x.value,
                material.ambientLight.y.value,
                material.ambientLight.z.value)

        prog.setUniform3f("uMaterial.diffuseLight",
                material.diffuseLight.x.value,
                material.diffuseLight.y.value,
                material.diffuseLight.z.value)

        prog.setUniform3f("uMaterial.specularLight",
                material.specularLight.x.value,
                material.specularLight.y.value,
                material.specularLight.z.value)

        prog.setUniform1f("uMaterial.shininess", material.shininess.value)
        prog.setUniform1f("uMaterial.opacity", material.opacity.value)

        prog.drawElements(GLES30.GL_TRIANGLES, vertexIndicesSize, GLES30.GL_UNSIGNED_SHORT, vertexIndicesBuffer)
        prog.disableAttribute(aPositionHandle)
        prog.disableAttribute(aSurfaceNormalHandle)

        if (aTextureCoordHandle != null)
            prog.disableAttribute(aTextureCoordHandle)

        return this
    }
}