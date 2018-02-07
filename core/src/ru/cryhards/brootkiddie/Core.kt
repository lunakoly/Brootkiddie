package ru.cryhards.brootkiddie

import com.badlogic.gdx.Game
import com.badlogic.gdx.Screen
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import ru.cryhards.brootkiddie.screens.BenchScreen
import ru.cryhards.brootkiddie.screens.globalmap.GlobalMapScreen
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
     * Reference to currently plaing bg music
     */
    private var currentBackgroundMusic: Music? = null


    override fun create() {
        batch = SpriteBatch()
        instance = this

        setScreen(SplashScreen())

        // start loading resources
        Assets.initialize()

        setScreen(MainMenuScreen())
        switchBackgroundMusic(Assets.sounds.AUTUMNS_DREAM_LULLABY)
    }


    /**
     * Starts plaing music
     */
    fun switchBackgroundMusic(music: Music) {
        currentBackgroundMusic?.stop()
        currentBackgroundMusic = music
        music.play()
    }


    override fun render() {
        Assets.update()
        invokeTasks()
        super.render()
    }


    override fun dispose() {
        batch.dispose()
        super.dispose()
    }


    /**
     * Disposes old screen and opens the target one
     */
    fun switchScreen(target: Screen): Core {
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
     * Holds tasks to be invoked
     */
    private val tasks = ArrayList<Task>()

    /**
     * Requests task execution
     */
    fun addTask(task: Task) {
        tasks.add(task)
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
