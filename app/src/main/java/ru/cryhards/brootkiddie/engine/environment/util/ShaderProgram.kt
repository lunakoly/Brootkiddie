package ru.cryhards.brootkiddie.engine.environment.util

import android.opengl.GLES30
import java.nio.Buffer

/**
 * Wrapper for OpenGl calls needed before rendering
 *
 * Created with love by luna_koly on 11.11.2017.
 */
class ShaderProgram(private val program: Int) {
    fun use() = GLES30.glUseProgram(program)

    fun setAttribute(aName: String, perVertex: Int, type: Int,
                     buffer: Buffer, normalized: Boolean = false, stride: Int = 0): Int {
        val aNameHandle = GLES30.glGetAttribLocation(program, aName)
        GLES30.glEnableVertexAttribArray(aNameHandle)
        GLES30.glVertexAttribPointer(aNameHandle, perVertex, type, normalized, stride, buffer)
        return aNameHandle
    }

    @Suppress("unused")
    fun setUniform4fv(uName: String, value: FloatArray, offset: Int = 0): Int {
        val uNameHandle = GLES30.glGetUniformLocation(program, uName)
        GLES30.glUniform4fv(uNameHandle, 1, value, offset)
        return uNameHandle
    }

    fun setUniformMatrix4fv(uName: String, value: FloatArray,
                            transpose: Boolean = false, offset: Int = 0): Int {
        val uNameHandle = GLES30.glGetUniformLocation(program, uName)
        GLES30.glUniformMatrix4fv(uNameHandle, 1, transpose, value, offset)
        return uNameHandle
    }

    fun setUniform3f(uName: String, x: Float, y: Float, z: Float): Int {
        val uNameHandle = GLES30.glGetUniformLocation(program, uName)
        GLES30.glUniform3f(uNameHandle, x, y, z)
        return uNameHandle
    }

    fun setUniform4f(uName: String, x: Float, y: Float, z: Float, w: Float): Int {
        val uNameHandle = GLES30.glGetUniformLocation(program, uName)
        GLES30.glUniform4f(uNameHandle, x, y, z, w)
        return uNameHandle
    }

    fun setUniform1f(uName: String, f: Float): Int {
        val uNameHandle = GLES30.glGetUniformLocation(program, uName)
        GLES30.glUniform1f(uNameHandle, f)
        return uNameHandle
    }

    fun setUniform1i(uName: String, i: Int): Int {
        val uNameHandle = GLES30.glGetUniformLocation(program, uName)
        GLES30.glUniform1i(uNameHandle, i)
        return uNameHandle
    }

    fun drawElements(mode: Int, count: Int, type: Int, indicesBuffer: Buffer) {
        GLES30.glDrawElements(mode, count, type, indicesBuffer)
    }

    fun disableAttribute(attribute: Int) {
        GLES30.glDisableVertexAttribArray(attribute)
    }

    fun setTexture(uName: String, unit: Int, glunit: Int, type: Int, id: Int): Int {
        val uNameHandle = GLES30.glGetUniformLocation(program, uName)
        GLES30.glActiveTexture(glunit)
        GLES30.glBindTexture(type, id)
        GLES30.glUniform1i(uNameHandle, unit)
        return uNameHandle
    }
}