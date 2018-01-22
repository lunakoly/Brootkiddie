package ru.cryhards.brootkiddie.utils

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator

object AssetManager {
    private val files: MutableMap<String, FileHandle> = mutableMapOf()
    private val fonts: MutableMap<String, FreeTypeFontGenerator> = mutableMapOf()

    fun loadFileInternal(path: String): FileHandle {
        var file = files[path]

        if (file == null) {
            file = Gdx.files.internal(path)
            files[path] = file
        }
        return file!!
    }

    fun loadFont(path: String, name: String): FreeTypeFontGenerator {
        var font = fonts[name]

        if (font == null) {
            val file = loadFileInternal(path)
            font = FreeTypeFontGenerator(file)
            fonts[name] = font
        }

        return font
    }

    fun makeFont(name: String, param: FreeTypeFontGenerator.FreeTypeFontParameter): BitmapFont {
        val font = fonts[name]!!
        return font.generateFont(param)
    }
}