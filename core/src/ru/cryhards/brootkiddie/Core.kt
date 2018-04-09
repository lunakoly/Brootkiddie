package ru.cryhards.brootkiddie

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import ru.cryhards.brootkiddie.items.Malware
import ru.cryhards.brootkiddie.screens.DialogsScreen
import ru.cryhards.brootkiddie.screens.MainMenuScreen
import ru.cryhards.brootkiddie.screens.SplashScreen
import ru.cryhards.brootkiddie.screens.bench.BenchScreen
import ru.cryhards.brootkiddie.screens.browser.BrowserScreen
import ru.cryhards.brootkiddie.screens.clicker.ClickerScreen
import ru.cryhards.brootkiddie.screens.globalmap.GlobalMapScreen
import ru.cryhards.brootkiddie.screens.inventory.InventoryScreen
import ru.cryhards.brootkiddie.screens.market.MarketScreen
import java.io.*
import java.lang.System.currentTimeMillis


/**
 * Main entry point
 */
class Core : Game(), Serializable {

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

        switchBackgroundMusic(Assets.Sounds.AUTUMNS_DREAM_LULLABY)
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
    private lateinit var inventoryScreen: InventoryScreen
    private lateinit var browserScreen : BrowserScreen
    private lateinit var dialogsScreen: DialogsScreen
    private lateinit var benchScreen: BenchScreen
    private lateinit var marketScreen : MarketScreen
    private lateinit var clickerScreen : ClickerScreen

    private var prevScreen: Screen? = null
    private var mustDispose = false

    fun newGame() {
        val env = Environment()
        openMap()
    }

    /**
     * Initializes map screen and switches to it
     */
    fun openMap() {
        globalMapScreen = GlobalMapScreen()
        prevScreen = globalMapScreen

        inventoryScreen = InventoryScreen()
        browserScreen = BrowserScreen()
        dialogsScreen = DialogsScreen()
        benchScreen = BenchScreen()
        marketScreen = MarketScreen()
        clickerScreen = ClickerScreen()

        switchScreen(globalMapScreen)
    }

    /**
     * Shows map screen
     */
    fun toGlobalMap() {
        mustDispose = false
        prevScreen = getScreen()
        setScreen(globalMapScreen)
    }

    /**
     * Shows inventory screen
     */
    fun toInventory() {
        mustDispose = false
        prevScreen = getScreen()
        setScreen(inventoryScreen)
    }

    /**
     * Shows bench screen
     */
    fun toBench(malware: Malware) {
        benchScreen.inspect(malware)
        mustDispose = true
        prevScreen = getScreen()
        setScreen(benchScreen)
    }

    /**
     * Shows dialogs screen
     */
    fun toDialogs(){
        mustDispose = false
        prevScreen = getScreen()
        setScreen(dialogsScreen)
    }

    /**
     * Shows dialogs screen
     */
    fun toMarket(){
        mustDispose = false
        prevScreen = getScreen()
        setScreen(marketScreen)
    }

    /**
     * Shows browser screen
     */
    fun toBrowser(){
        mustDispose = false
        prevScreen = getScreen()
        setScreen(browserScreen)
    }

    /**
     * Shows clicker screen
     */
    fun toClicker(){
        mustDispose = false
        prevScreen = getScreen()
        setScreen(clickerScreen)
    }

    /**
     * Shows previous screen
     */
    fun toBack(){
        if (prevScreen != null) {
            val sub = getScreen()
            setScreen(prevScreen)

            if (mustDispose)
                sub.dispose()
            else
                prevScreen = sub
        }
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
     * Executes tasks and removes redundant if needed
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
    class Task(private var repeatCount: Int, var period: Long, private val task: () -> Boolean) : Serializable {
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
            if (task())
                repeatCount = 0

            lastStartTime = currentTimeMillis()
            if (repeatCount > 0)
                repeatCount--
        }
    }

    fun saveGame() {
        Gdx.app.log("Saving", savePath)
        val f = File(savePath)
        if (!f.exists()) f.createNewFile()

        val oos = ObjectOutputStream(FileOutputStream(f))

        val save = GameSave(Environment.instance, Core.instance.tasks)
        oos.writeObject(save)
        oos.flush()
        oos.close()
    }

    fun loadGame() : Boolean{
        try {
            val ois = ObjectInputStream(FileInputStream(savePath))
            val save = ois.readObject() as GameSave
            Environment.instance = save.env
            Core.instance.tasks.clear()
            Core.instance.tasks += save.tasks
            ois.close()
            return true
        } catch (e: IOException) {
            Gdx.app.log("Loading", "No save was found")
            return false
        }
    }

    var savePath: String = ""

    data class GameSave(val env: Environment, val tasks: ArrayList<Task>) : Serializable {}

}
