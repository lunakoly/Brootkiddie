package ru.cryhards.brootkiddie.engine.environment

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.opengl.GLES30
import android.opengl.GLUtils
import ru.cryhards.brootkiddie.engine.environment.meshes.StaticObject
import ru.cryhards.brootkiddie.engine.environment.util.Material
import ru.cryhards.brootkiddie.engine.environment.util.Shaders
import ru.cryhards.brootkiddie.engine.environment.util.Texture
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.ByteBuffer
import java.nio.ByteOrder

/**
 * Created with love by luna_koly on 11.11.2017.
 */
object MeshFactory {
    private fun genObject(
            vertices: FloatArray,
            vertexIndices: ShortArray,
            vertexNormals: FloatArray,
            textureUVs: FloatArray,
            material: Material): StaticObject {

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

        bb = ByteBuffer.allocateDirect(textureUVs.size * 4)
        bb.order(ByteOrder.nativeOrder())
        val textureUVsBuffer = bb.asFloatBuffer()
        textureUVsBuffer.put(textureUVs)
        textureUVsBuffer.position(0)

        return StaticObject(
                vertexBuffer,
                vertexIndices.size,
                vertexIndicesBuffer,
                vertexNormalsBuffer,
                textureUVsBuffer,
                material)
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
        val textureUVs = ArrayList<Float>()
        var material: Material? = null
        var texture: Texture? = null

        while (line != null) {
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
                    // smooth shading
                    // always off now
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

                        if (pieces[1].isNotEmpty()) {
                            val t = pieces[1].toInt() - 1
                            textureUVs.add(texturesData[t * 2])
                            textureUVs.add(texturesData[t * 2 + 1])
                        }
                    }
                }

                line.startsWith("usemtl") -> {
                    val texpath = line.split(" ")
                    texture = loadTexture(context, texpath.drop(1).joinToString(""))
                }

                line.startsWith("mtllib") -> {
                    val matpath = line.split(" ")
                    material = loadMaterial(context, matpath.drop(1).joinToString(""))
                }
            }
            line = br.readLine()
        }

        if (material == null)
            material = Material(Shaders.OBJ)

        material.texture = texture


        return MeshFactory.genObject(
                vertices.toFloatArray(),
                theirIndices.toShortArray(),
                paranormals.toFloatArray(),
                textureUVs.toFloatArray(),
                material)
    }

    @Suppress("MemberVisibilityCanPrivate")
    fun loadTexture(context: Context, path: String, framesCount: Int = 1, duration: Long = 0): Texture {
        val options = BitmapFactory.Options()
        options.inScaled = false
        val bitmap = BitmapFactory.decodeStream(context.assets.open(path), null, options)
        val width = bitmap.width / framesCount
        val height = bitmap.height

        val texts = IntArray(framesCount)

        for (i in 0 until framesCount) {
            GLES30.glGenTextures(1, texts, i)
            GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, texts[i])
            GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_LINEAR)
            GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_LINEAR)
            GLUtils.texImage2D(
                    GLES30.GL_TEXTURE_2D, 0,
                    Bitmap.createBitmap(bitmap, i * width, 0, width, height),
                    0)
            bitmap.recycle()
        }

        return Texture(texts, duration, width, height)
    }

    @Suppress("MemberVisibilityCanPrivate")
    fun loadMaterial(context: Context, path: String): Material {
        val br = BufferedReader(InputStreamReader(context.assets.open(path)))
        var line = br.readLine()

        val material = Material(Shaders.OBJ)

        while (line != null) {
            when {
                line[0].toString() + line[1] == "Ka" -> {
                    val values = line.split(" ")
                    material.ambientLight.r.value = values[1].toFloat()
                    material.ambientLight.g.value = values[2].toFloat()
                    material.ambientLight.b.value = values[3].toFloat()
                }

                line[0].toString() + line[1] == "Kd" -> {
                    val values = line.split(" ")
                    material.diffuseLight.r.value = values[1].toFloat()
                    material.diffuseLight.g.value = values[2].toFloat()
                    material.diffuseLight.b.value = values[3].toFloat()
                }

                line[0].toString() + line[1] == "Ks" -> {
                    val values = line.split(" ")
                    material.specularLight.r.value = values[1].toFloat()
                    material.specularLight.g.value = values[2].toFloat()
                    material.specularLight.b.value = values[3].toFloat()
                }

                line[0].toString() + line[1] == "Ns" -> {
                    val values = line.split(" ")
                    material.shininess.value = values[1].toFloat()
                }

                line[0].toString() + line[1] == "Tr" -> {
                    val values = line.split(" ")
                    material.opacity.value = 1 - values[1].toFloat()
                }

                line[0] == 'd' -> {
                    val values = line.split(" ")
                    material.opacity.value = values[1].toFloat()
                }

                line.startsWith("illum") -> {
                    // TODO
                }

                // TODO
            }

            line = br.readLine()
        }

        return material
    }
}