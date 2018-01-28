package ru.cryhards.brootkiddie

import com.badlogic.gdx.Game
import ru.cryhards.brootkiddie.malware.MalwareExample
import ru.cryhards.brootkiddie.malware.scripts.ExampleBaseScript
import ru.cryhards.brootkiddie.malware.scripts.Script
import ru.cryhards.brootkiddie.malware.scripts.SpreadingScript


class ReallyGame : Game() {

    lateinit var player : Player
    lateinit var globalMapScreen : GlobalMapScreen


    override fun create() {
        player = Player()
        globalMapScreen = GlobalMapScreen(player, this)

        setScreen(globalMapScreen)
    }

    override fun dispose() {
        getScreen().dispose()
    }

    fun openEditor(){
        setScreen(MalwareEditorScreen(player, this))
    }

    fun closeEditor(){
        getScreen().dispose()
        setScreen(globalMapScreen)
    }
}
