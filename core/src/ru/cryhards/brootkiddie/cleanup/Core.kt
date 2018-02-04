package ru.cryhards.brootkiddie.cleanup

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import ru.cryhards.brootkiddie.cleanup.screens.EditorScreen
import ru.cryhards.brootkiddie.cleanup.screens.GlobalMapScreen
import ru.cryhards.brootkiddie.cleanup.screens.MainMenuScreen
import ru.cryhards.brootkiddie.cleanup.screens.SplashScreen
import ru.cryhards.brootkiddie.malware.MalwareExample
import ru.cryhards.brootkiddie.malware.scripts.ExampleBaseScript
import ru.cryhards.brootkiddie.malware.scripts.Script
import ru.cryhards.brootkiddie.malware.scripts.SpreadingScript


/**
 * Main entry point
 */
class Core : Game() {

    lateinit var batch: SpriteBatch
    private lateinit var currentMusic: Music
    lateinit var noize: Sound


    override fun create() {
        batch = SpriteBatch()
        Core.instance = this

        setScreen(SplashScreen())

        // start loading resources
        Assets.initialize()

        setScreen(MainMenuScreen())
        currentMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/gurdonark_autumn_s_dream_lullaby_1.mp3"))
        currentMusic.isLooping = true
        currentMusic.play()

        noize = Gdx.audio.newSound(Gdx.files.internal("sounds/noise.mp3"))
    }

    override fun render() {
        Assets.update()
        super.render()
    }

    override fun dispose() {
        batch.dispose()
        super.dispose()
    }


    private fun switchScreen(target: Screen): Core {
        getScreen().dispose()
        setScreen(target)
        return this
    }

    fun openMap() {
        switchScreen(GlobalMapScreen())
    }

    fun openEditor() {
        switchScreen(EditorScreen())
    }


    companion object {
        lateinit var instance: Core
    }
}
