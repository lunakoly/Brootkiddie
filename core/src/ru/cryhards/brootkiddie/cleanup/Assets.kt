package ru.cryhards.brootkiddie.cleanup

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import java.lang.System.currentTimeMillis

/**
 * Created with love by luna_koly on 04.02.2018.
 */
object Assets {

    fun initialize() {
        fonts.initialize()
        shaders.initialize()
    }

    fun update() {
        shaders.update()
    }


    val fonts = Fonts()

    class Fonts {
        lateinit var ROBOTO: BitmapFont
        lateinit var HACK_REGULAR: BitmapFont

        fun initialize() {
            var generator = FreeTypeFontGenerator(Gdx.files.internal("fonts/roboto.ttf"))
            var params = FreeTypeFontGenerator.FreeTypeFontParameter()
            params.color = Color.WHITE
            params.size = 100

            ROBOTO = generator.generateFont(params)

            generator = FreeTypeFontGenerator(Gdx.files.internal("fonts/Hack-Regular.ttf"))
            params = FreeTypeFontGenerator.FreeTypeFontParameter()
            params.color = Color.WHITE
            params.size = 100

            HACK_REGULAR = generator.generateFont(params)
        }
    }


    val shaders = Shaders()

    class Shaders {
        lateinit var ABERRATION: ShaderProgram
        lateinit var GLITCH: ShaderProgram

        fun initialize() {
            ShaderProgram.pedantic = false

            ABERRATION = ShaderProgram(Gdx.files.internal("shaders/aberration.vsh"), Gdx.files.internal("shaders/aberration.fsh"))
            if (!ABERRATION.isCompiled)
                println(ABERRATION.log)

            GLITCH = ShaderProgram(Gdx.files.internal("shaders/glitch.vsh"), Gdx.files.internal("shaders/glitch.fsh"))
            if (!GLITCH.isCompiled)
                println(GLITCH.log)
        }

        fun update() {
            val time = (currentTimeMillis() % 10000).toFloat()
            ABERRATION.begin()
            ABERRATION.setUniformf("u_time", time)
            ABERRATION.end()

            GLITCH.begin()
            GLITCH.setUniformf("u_time", time)
            GLITCH.end()
        }
    }

}