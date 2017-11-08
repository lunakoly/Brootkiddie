package ru.cryhards.brootkiddie.engine.util

import android.content.Context
import android.opengl.GLES30
import android.util.Log

/**
 * Created with love by luna_koly on 29.10.2017.
 */
object Shaders {
    var BASIC = 0
    var COLOR_TRANSITION = 0

    fun init(context: Context) {
        BASIC = genShaderProgram(
                genShader(GLES30.GL_VERTEX_SHADER, loadFile(context,
                        "shaders/basic_vertex_shader.vsh")),
                genShader(GLES30.GL_FRAGMENT_SHADER, loadFile(context,
                        "shaders/basic_fragment_shader.fsh"))
        )
        COLOR_TRANSITION = genShaderProgram(
                genShader(GLES30.GL_VERTEX_SHADER, loadFile(context,
                        "shaders/color_transition_vertex_shader.vsh")),
                genShader(GLES30.GL_FRAGMENT_SHADER, loadFile(context,
                        "shaders/color_transition_fragment_shader.fsh"))
        )
    }

    private fun loadFile(context: Context, path: String): String {
        val input = context.assets.open(path)
        return input.bufferedReader().readText()
    }

    private fun genShader(type: Int, shaderSource: String): Int {
        val shader = GLES30.glCreateShader(type)
        GLES30.glShaderSource(shader, shaderSource)
        GLES30.glCompileShader(shader)

        val status = IntArray(1)
        GLES30.glGetShaderiv(shader, GLES30.GL_COMPILE_STATUS, status, 0)

        if (status[0] == 0)
            Log.e("SHADER", GLES30.glGetShaderInfoLog(shader))

        return shader
    }

    private fun genShaderProgram(vertexShader: Int, fragmentShader: Int): Int {
        val program = GLES30.glCreateProgram()
        GLES30.glAttachShader(program, vertexShader)
        GLES30.glAttachShader(program, fragmentShader)
        GLES30.glLinkProgram(program)

        val status = IntArray(1)
        GLES30.glGetProgramiv(program, GLES30.GL_LINK_STATUS, status, 0)

        if (status[0] == 0)
            Log.e("PROGRAM", GLES30.glGetProgramInfoLog(program))

        return program
    }
}