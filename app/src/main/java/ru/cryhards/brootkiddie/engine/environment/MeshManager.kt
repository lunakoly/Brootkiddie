package ru.cryhards.brootkiddie.engine.environment

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.opengl.GLES30
import android.opengl.GLUtils
import ru.cryhards.brootkiddie.engine.util.TextureObject
import ru.cryhards.brootkiddie.engine.util.prop.CoordProperty
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.nio.ByteBuffer
import java.nio.ByteOrder

/**
 * Created with love by luna_koly on 11.11.2017.
 */
object MeshManager {
    private fun genObject(
            vertices: FloatArray,
            vertexIndices: ShortArray,
            vertexNormals: FloatArray): StaticObject {

        var bb = ByteBuffer.allocateDirect(vertices.size * 4)  //  4 vertices * 3 coordinates * 4 bytes
        bb.order(ByteOrder.nativeOrder())
        val vertexBuffer = bb.asFloatBuffer()
        vertexBuffer.put(vertices)
        vertexBuffer.position(0)

        bb = ByteBuffer.allocateDirect(vertexIndices.size * 2)
        bb.order(ByteOrder.nativeOrder())
        val vertexIndicesBuffer = bb.asShortBuffer()
        vertexIndicesBuffer.put(vertexIndices)
        vertexIndicesBuffer.position(0)

        bb = ByteBuffer.allocateDirect(vertexNormals.size * 4)
        bb.order(ByteOrder.nativeOrder())
        val vertexNormalsBuffer = bb.asFloatBuffer()
        vertexNormalsBuffer.put(vertexNormals)
        vertexNormalsBuffer.position(0)

        return StaticObject(
                vertexBuffer,
                vertexIndices.size,
                vertexIndicesBuffer,
                vertexNormalsBuffer)
    }

    private fun genColoredObject(
            vertices: FloatArray,
            vertexIndices: ShortArray,
            vertexNormals: FloatArray,
            vertexColors: FloatArray): StaticColoredObject {

        var bb = ByteBuffer.allocateDirect(vertices.size * 4)  //  4 vertices * 3 coordinates * 4 bytes
        bb.order(ByteOrder.nativeOrder())
        val vertexBuffer = bb.asFloatBuffer()
        vertexBuffer.put(vertices)
        vertexBuffer.position(0)

        bb = ByteBuffer.allocateDirect(vertexIndices.size * 2)
        bb.order(ByteOrder.nativeOrder())
        val vertexIndicesBuffer = bb.asShortBuffer()
        vertexIndicesBuffer.put(vertexIndices)
        vertexIndicesBuffer.position(0)

        bb = ByteBuffer.allocateDirect(vertexNormals.size * 4)
        bb.order(ByteOrder.nativeOrder())
        val vertexNormalsBuffer = bb.asFloatBuffer()
        vertexNormalsBuffer.put(vertexNormals)
        vertexNormalsBuffer.position(0)

        bb = ByteBuffer.allocateDirect(vertexColors.size * 4)
        bb.order(ByteOrder.nativeOrder())
        val vertexColorsBuffer = bb.asFloatBuffer()
        vertexColorsBuffer.put(vertexColors)
        vertexColorsBuffer.position(0)

        return StaticColoredObject(
                vertexBuffer,
                vertexIndices.size,
                vertexIndicesBuffer,
                vertexNormalsBuffer,
                vertexColorsBuffer)
    }

    fun loadObj(context: Context, path: String): StaticObject {
        val br = BufferedReader(InputStreamReader(context.assets.open(path)))
        var line = br.readLine()

        val verticesData = ArrayList<Float>()
        val texturesData = ArrayList<Float>()
        val normalsData = ArrayList<Float>()

        val vertices = ArrayList<Float>()
        val theirIndices = ArrayList<Short>()
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
                    }
                }

                line.startsWith("usemtl") -> {

                }

                line.startsWith("mtllib") -> {

                }
            }
            line = br.readLine()
        }

        return MeshManager.genObject(
                vertices.toFloatArray(),
                theirIndices.toShortArray(),
                paranormals.toFloatArray())
    }

    @Suppress("unused")
    fun loadObjWithColors(context: Context, path: String): StaticColoredObject {
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

                        val c = pieces[3].toInt() - 1
                        theirColors.add(colorsData[c * 4])
                        theirColors.add(colorsData[c * 4 + 1])
                        theirColors.add(colorsData[c * 4 + 2])
                        theirColors.add(colorsData[c * 4 + 3])
                    }
                }

                line.startsWith("usemtl") -> {

                }

                line.startsWith("mtllib") -> {

                }
            }
            line = br.readLine()
        }

        return MeshManager.genColoredObject(
                vertices.toFloatArray(),
                theirIndices.toShortArray(),
                paranormals.toFloatArray(),
                theirColors.toFloatArray())
    }

    fun loadTexture(context: Context, path: String, framesCount: Int = 1, duration: Long = 0): TextureObject {
        val options = BitmapFactory.Options()
        options.inScaled = false
        val bitmap = BitmapFactory.decodeStream(context.assets.open(path), null, options)
        val width = bitmap.width / framesCount
        val height = bitmap.height

        val texts = IntArray(framesCount)

        for (i in 0 until framesCount) {
            GLES30.glGenTextures(1, texts, i)
//            GLES30.glActiveTexture(GLES30.GL_TEXTURE0)
            GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, texts[i])
            GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_LINEAR)
            GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_LINEAR)
            GLUtils.texImage2D(
                    GLES30.GL_TEXTURE_2D, 0,
                    Bitmap.createBitmap(bitmap, i * width, 0, width, height),
                    0)
            bitmap.recycle()

//            GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, 0)
        }

        return TextureObject(texts, duration, width, height)
    }

    fun genRectangleTextureObject(context: Context, path: String, framesCount: Int = 1, duration: Long = 0): RectangleTextureObject {
        val texture = loadTexture(context, path, framesCount, duration)
        return RectangleTextureObject(texture,
                CoordProperty(1.0f, 1.0f, 0.0f),
                CoordProperty(-1.0f, 1.0f, 0.0f),
                CoordProperty(-1.0f, -1.0f, 0.0f),
                CoordProperty(1.0f, -1.0f, 0.0f)
                )
    }
}