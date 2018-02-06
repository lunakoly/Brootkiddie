package ru.cryhards.brootkiddie

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import java.lang.System.currentTimeMillis


@Suppress("PropertyName")
/**
 * Caches frequently used resources
 */
object Assets {

    /**
     * Loads resources. Call on startup
     */
    fun initialize() {
        fonts.initialize()
        shaders.initialize()
    }

    /**
     * Updates resources state. Call periodically
     */
    fun update() {
        shaders.update()
    }


    /**
     * Contains fonts resources
     */
    val fonts = Fonts()

    /**
     * Contains fonts resources
     */
    class Fonts {
        lateinit var ROBOTO: BitmapFont
        lateinit var ROBOTOx2: BitmapFont
        lateinit var HACK_REGULAR: BitmapFont

        /**
         * Loads fonts. Call on startup
         */
        fun initialize() {
            var generator = FreeTypeFontGenerator(Gdx.files.internal("fonts/roboto.ttf"))
            var params = FreeTypeFontGenerator.FreeTypeFontParameter()
            params.color = Color.WHITE
            params.size = 35

            ROBOTO = generator.generateFont(params)

            params.size = 50
            ROBOTOx2 = generator.generateFont(params)

            generator = FreeTypeFontGenerator(Gdx.files.internal("fonts/Hack-Regular.ttf"))
            params = FreeTypeFontGenerator.FreeTypeFontParameter()
            params.color = Color.WHITE
            params.size = 100

            HACK_REGULAR = generator.generateFont(params)
        }
    }


    /**
     * Contains shaders resources
     */
    val shaders = Shaders()

    /**
     * Contains shaders resources
     */
    class Shaders {
        lateinit var ABERRATION: ShaderProgram
        lateinit var GLITCH: ShaderProgram
        lateinit var WAVE: ShaderProgram

        /**
         * Loads shaders. Call on startup
         */
        fun initialize() {
            ShaderProgram.pedantic = false

            ABERRATION = ShaderProgram(Gdx.files.internal("shaders/aberration.vsh"), Gdx.files.internal("shaders/aberration.fsh"))
            if (!ABERRATION.isCompiled)
                println(ABERRATION.log)

            GLITCH = ShaderProgram(Gdx.files.internal("shaders/glitch.vsh"), Gdx.files.internal("shaders/glitch.fsh"))
            if (!GLITCH.isCompiled)
                println(GLITCH.log)

            WAVE = ShaderProgram(Gdx.files.internal("shaders/wave.vsh"), Gdx.files.internal("shaders/wave.fsh"))
            if (!WAVE.isCompiled)
                println(WAVE.log)
        }

        /**
         * Updates shaders uniforms. Call frequently
         */
        fun update() {
            val time = (currentTimeMillis() % 10000).toFloat()
            ABERRATION.begin()
            ABERRATION.setUniformf("u_time", time)
            ABERRATION.end()

            GLITCH.begin()
            GLITCH.setUniformf("u_time", time)
            GLITCH.end()

            WAVE.begin()
            WAVE.setUniformf("u_time", time)
            WAVE.end()
        }
    }

}