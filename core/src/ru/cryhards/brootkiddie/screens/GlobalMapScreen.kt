package ru.cryhards.brootkiddie.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import ru.cryhards.brootkiddie.Game
import ru.cryhards.brootkiddie.Player
import ru.cryhards.brootkiddie.malware.Distribution
import ru.cryhards.brootkiddie.malware.MalwareExample
import ru.cryhards.brootkiddie.screens.actors.GlobalMapActor

open class GlobalMapScreen(game: Game, batch: SpriteBatch) : BaseScreen(game, batch) {
    val countryColor = Color(138f / 255f, 172f / 255f, 184f / 255f, 1f)
    val infectedColor = Color(1f, 64f / 255f, 64f / 255f, 1f)

    val player = Player()

    val ticksInDay = 20

    var ticksToNextDay = ticksInDay

    val infectedLabel : Label


    var stage = Stage()

    init {
        stage.addActor(GlobalMapActor())


        val ftfg = FreeTypeFontGenerator(Gdx.files.internal("fonts/roboto.ttf"))
        val par = FreeTypeFontGenerator.FreeTypeFontParameter()
        par.size = 100

        infectedLabel = Label("INFECTED : ${player.infectedNodes} DAY : ${player.days}", Label.LabelStyle(ftfg.generateFont(par), Color(0f, 0f, 0f, 1f)))
        infectedLabel.x = 0f
        infectedLabel.y = 0f


        stage.addActor(infectedLabel)

        player.distribution = Distribution(player)
        val malware = MalwareExample(player)
        player.malwareList+=malware

    }

    override fun draw() {
        stage.draw()
    }

    override fun act(deltaT: Float) {
        ticksToNextDay-=1
        if (ticksToNextDay == 0) {
            player.days += 1
            player.doDay()
            updateUi()
            ticksToNextDay = ticksInDay
        }
    }

    fun calcInfectionSpeed(): Int {
        return 1
    }

    private fun updateUi(){
        infectedLabel.setText("INFECTED : ${player.infectedNodes} DAY : ${player.days}")
    }

}