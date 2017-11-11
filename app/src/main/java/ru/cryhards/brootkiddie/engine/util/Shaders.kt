package ru.cryhards.brootkiddie.engine.util

import android.content.Context
import android.opengl.GLES30
import android.util.Log
import ru.cryhards.brootkiddie.engine.scene.Mesh
import ru.cryhards.brootkiddie.engine.scene.MeshOBJ
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * Created with love by luna_koly on 29.10.2017.
 */
object Shaders {
    var BASIC = 0
    var COLOR_TRANSITION = 0
    var OBJ = 0
    var OBJ_COLOR = 0

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
        OBJ = genShaderProgram(
                genShader(GLES30.GL_VERTEX_SHADER, loadFile(context,
                        "shaders/obj_vertex_shader.vsh")),
                genShader(GLES30.GL_FRAGMENT_SHADER, loadFile(context,
                        "shaders/obj_fragment_shader.fsh"))
        )
        OBJ_COLOR = genShaderProgram(
                genShader(GLES30.GL_VERTEX_SHADER, loadFile(context,
                        "shaders/obj_color_vertex_shader.vsh")),
                genShader(GLES30.GL_FRAGMENT_SHADER, loadFile(context,
                        "shaders/obj_color_fragment_shader.fsh"))
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

        if (status[0] == 0) {
            if (type == GLES30.GL_FRAGMENT_SHADER)
                Log.e("FRAGMENT_SHADER", GLES30.glGetShaderInfoLog(shader))
            else
                Log.e("VERTEX_SHADER", GLES30.glGetShaderInfoLog(shader))
        }

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

    private fun getArr(arr: ArrayList<Float>): String {
        var out = "\nSize: " + arr.size.toString()
        for (i in 0 until arr.size / 3) {
            out += "\n" + i + ": "
            for (j in 0 until 3)
                out += arr[i * 3 + j].toString() + " "
        }
        return out
    }

    fun decodeObj(context: Context, path: String): MeshOBJ {
        val br = BufferedReader(InputStreamReader(context.assets.open(path)))
        var line = br.readLine()

        val verticesData = ArrayList<Float>()
        val texturesData = ArrayList<Float>()
        val normalsData = ArrayList<Float>()
        val colorsData = ArrayList<Float>()

        val vertices = ArrayList<Float>()
        val theirIndices = ArrayList<Short>()
        val theirColors = ArrayList<Float>()
        val paranormals = ArrayList<Float>()

        while (line != null) {
            if (line.length < 2) continue

            when {
                line[0].toString() + line[1] == "vt" -> {
                    val values = line.split(" ")
                    texturesData.add(values[1].toFloat())
                    texturesData.add(values[2].toFloat())
                }

                line[0].toString() + line[1] == "vn" -> {
                    val values = line.split(" ")
                    normalsData.add(values[1].toFloat())
                    normalsData.add(values[2].toFloat())
                    normalsData.add(values[3].toFloat())
                }

                line[0] == 'v' -> {
                    val values = line.split(" ")
                    verticesData.add(values[1].toFloat())
                    verticesData.add(values[2].toFloat())
                    verticesData.add(values[3].toFloat())
                }

                line[0] == 'c' -> {
                    val values = line.split(" ")
                    colorsData.add(values[1].toFloat())
                    colorsData.add(values[2].toFloat())
                    colorsData.add(values[3].toFloat())
                    colorsData.add(values[4].toFloat())
                }

                line[0] == 's' -> {

                }

                line[0] == 'f' -> {
                    val values = line.split(" ")
                    for (i in 1 until values.size) {
                        val pieces = values[i].split("/")
                        theirIndices.add(theirIndices.size.toShort())

                        val v = pieces[0].toInt() - 1
                        vertices.add(verticesData[v * 3])
                        vertices.add(verticesData[v * 3 + 1])
                        vertices.add(verticesData[v * 3 + 2])

                        val n = pieces[2].toInt() - 1
                        paranormals.add(normalsData[n * 3])
                        paranormals.add(normalsData[n * 3 + 1])
                        paranormals.add(normalsData[n * 3 + 2])

                        if (pieces.size >= 4) {
                            val c = pieces[3].toInt() - 1
                            theirColors.add(colorsData[c * 4])
                            theirColors.add(colorsData[c * 4 + 1])
                            theirColors.add(colorsData[c * 4 + 2])
                            theirColors.add(colorsData[c * 4 + 3])
                        }
                    }
                }

                line.startsWith("usemtl") -> {

                }

                line.startsWith("mtllib") -> {

                }
            }
            line = br.readLine()
        }

        return MeshOBJ(
                vertices.toFloatArray(),
                theirIndices.toShortArray(),
//                theirColors.toFloatArray(),
                paranormals.toFloatArray())
    }
}