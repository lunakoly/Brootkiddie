package ru.cryhards.brootkiddie.engine.util

import android.content.Context
import android.opengl.GLES30

/**
 * Created with love by luna_koly on 29.10.2017.
 */
object Shaders {
    var BASIC = 0
    var COLOR_TRANSITION = 0

    fun init() {
        BASIC = genShaderProgram(
                genShader(GLES30.GL_VERTEX_SHADER,
                        "attribute vec4 aPosition;\n" +
                        "\n" +
                        "void main() {\n" +
                        "    gl_Position = aPosition;\n" +
                        "}"),
                genShader(GLES30.GL_FRAGMENT_SHADER,
                        "precision mediump float;\n" +
                        "uniform vec4 uColor;\n" +
                        "\n" +
                        "void main() {\n" +
                        "    gl_FragColor = uColor;\n" +
                        "}")
        )
//        BASIC = genShaderProgram(
//                genShader(GLES30.GL_VERTEX_SHADER, loadFile(context, "basic_vertex_shader.vsh")),
//                genShader(GLES30.GL_FRAGMENT_SHADER, loadFile(context, "basic_fragment_shader.fsh"))
//        )
        COLOR_TRANSITION = genShaderProgram(
                genShader(GLES30.GL_VERTEX_SHADER,
                        "attribute vec4 aPosition;\n" +
                                "attribute vec4 aColor;" +
                                "varying vec4 vColor;" +
                                "\n" +
                                "void main() {\n" +
                                "    vColor = aColor;" +
                                "    gl_Position = aPosition;\n" +
                                "}"),
                genShader(GLES30.GL_FRAGMENT_SHADER,
                        "precision mediump float;\n" +
                                "varying vec4 vColor;\n" +
                                "\n" +
                                "void main() {\n" +
                                "    gl_FragColor = vColor;\n" +
                                "}")
        )
    }

    private fun loadFile(context: Context, path: String): String {
        println("============")
        context.assets.list(path).forEach(::println)

        val input = context.assets.open(path)
        return input.bufferedReader().readText()
    }

    private fun genShader(type: Int, shaderSource: String): Int {
        val shader = GLES30.glCreateShader(type)
        GLES30.glShaderSource(shader, shaderSource)
        GLES30.glCompileShader(shader)
        return shader
    }

    private fun genShaderProgram(vertexShader: Int, fragmentShader: Int): Int {
        val program = GLES30.glCreateProgram()
        GLES30.glAttachShader(program, vertexShader)
        GLES30.glAttachShader(program, fragmentShader)
        GLES30.glLinkProgram(program)
        return program
    }
}