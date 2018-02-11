package ru.cryhards.brootkiddie

import com.badlogic.gdx.Game
import com.badlogic.gdx.Screen
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import ru.cryhards.brootkiddie.events.dialogs.Dialog
import ru.cryhards.brootkiddie.screens.DialogsScreen
import ru.cryhards.brootkiddie.screens.MainMenuScreen
import ru.cryhards.brootkiddie.screens.SplashScreen
import ru.cryhards.brootkiddie.screens.bench.BenchScreen
import ru.cryhards.brootkiddie.screens.browser.BrowserScreen
import ru.cryhards.brootkiddie.screens.globalmap.GlobalMapScreen
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

    private lateinit var globalMapScreen: GlobalMapScreen
    private lateinit var benchScreen: BenchScreen
    private lateinit var browserScreen : BrowserScreen
    private lateinit var dialogsScreen: DialogsScreen


    /**
     * Initializes map screen and switches to it
     */
    fun openMap() {
        globalMapScreen = GlobalMapScreen()
        Player.dialogs.add(Dialog.readFromFile("dialogs/example.json"))
        switchScreen(globalMapScreen)
        benchScreen = BenchScreen()
        browserScreen = BrowserScreen()
        dialogsScreen = DialogsScreen()
    }

    /**
     * Shows map screen
     */
    fun toGlobalMap() {
        setScreen(globalMapScreen)
    }

    /**
     * Shows bench screen
     */
    fun toBench() {
        setScreen(benchScreen)
    }

    /**
     * Shows dialogs screen
     */

    fun toDialogs(){
        setScreen(dialogsScreen)
    }

    /**
     * Shows browser screen
     */

    fun toBrowser(){
        setScreen(browserScreen)
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

    fun removeTask(task: Task){
        tasks.remove(task)
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
     * Piece of code to be invoked later
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

        /*
            Constant for period for daily tasks
         */

        companion object {
            val DayTaskPeriod = 3000L
        }
    }
}
