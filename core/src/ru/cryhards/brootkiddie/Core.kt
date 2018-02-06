package ru.cryhards.brootkiddie

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import ru.cryhards.brootkiddie.screens.BenchScreen
import ru.cryhards.brootkiddie.screens.GlobalMapScreen
import ru.cryhards.brootkiddie.screens.MainMenuScreen
import ru.cryhards.brootkiddie.screens.SplashScreen
import java.lang.System.currentTimeMillis


/**
 * Main entry point
 */
class Core : Game() {

    /**
     * Use this batch to draw something from different screens
     */
    lateinit var batch: SpriteBatch

    /**
     * Holds tasks to be invoked
     */
    val tasks = ArrayList<Task>()

    /**
     * Sound of noize used when user clicks anything
     */
    lateinit var noize: Sound


    private lateinit var currentMusic: Music


    override fun create() {
        batch = SpriteBatch()
        instance = this

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
        invokeTasks()
        super.render()
    }


    /**
     * Executes tasks and removes redutant if needed
     */
    private fun invokeTasks() {
        val newTime = currentTimeMillis()
        var i = 0

        while (i < tasks.size) {
            if (newTime - tasks[i].lastStartTime >= tasks[i].period) {
                tasks[i].invoke()

                if (!tasks[i].mustRepeat()) {
                    tasks.removeAt(i)
                    i--
                }
            }

            i++
        }
    }


    override fun dispose() {
        batch.dispose()
        super.dispose()
    }


    /**
     * Disposes old screen and opens the target one
     */
    private fun switchScreen(target: Screen): Core {
        getScreen().dispose()
        setScreen(target)
        return this
    }

    /**
     * Switches to map screen
     */
    fun openMap() {
        switchScreen(GlobalMapScreen())
    }

    /**
     * Switches to bench screen
     */
    fun openBench() {
        switchScreen(BenchScreen())
    }


    companion object {
        /**
         * Holds last created instance of Core
         */
        lateinit var instance: Core
    }


    /**
     * Piece of code to be inveoked later
     */
    class Task(private var repeatCount: Int, var period: Long, private val task: () -> Unit) {
        /**
         * Saves time when the code got invoked the last time
         * or holds initial time
         */
        var lastStartTime = currentTimeMillis()

        /**
         * Returns true if this task has not finished
         * it's mission
         */
        fun mustRepeat(): Boolean = repeatCount != 0

        /**
         * Runs task
         */
        fun invoke() {
            task()
            lastStartTime = currentTimeMillis()
            if (repeatCount > 0)
                repeatCount--
        }
    }
}
