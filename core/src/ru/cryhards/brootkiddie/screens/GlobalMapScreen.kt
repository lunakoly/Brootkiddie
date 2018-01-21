package ru.cryhards.brootkiddie.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import ru.cryhards.brootkiddie.Game
import ru.cryhards.brootkiddie.malware.MalwareExample
import ru.cryhards.brootkiddie.screens.actors.GlobalMapActor

open class GlobalMapScreen(game: Game, batch: SpriteBatch) : BaseScreen(game, batch) {
    val countryColor = Color(138f / 255f, 172f / 255f, 184f / 255f, 1f)
    val infectedColor = Color(1f, 64f / 255f, 64f / 255f, 1f)

    val totalNodes = Int.MAX_VALUE
    var infectedNodes = 1
    var secondsElapsed = 0f

    var malware = MalwareExample()

    var stage = Stage()

    init {
        stage.addActor(GlobalMapActor())


        var ftfg = FreeTypeFontGenerator(Gdx.files.internal("fonts/roboto.ttf"))
        var par = FreeTypeFontGenerator.FreeTypeFontParameter()
        par.size = 100

        var infectedLabel = Label("INFECTED", Label.LabelStyle(ftfg.generateFont(par), Color(0f, 0f, 0f, 1f)))
        infectedLabel.x = 0f
        infectedLabel.y = 0f


        stage.addActor(infectedLabel)
    }

    override fun draw() {
        stage.draw()
    }

    override fun act(deltaT: Float) {
        secondsElapsed += deltaT
        malware.recalcStats()
    }

    fun calcInfectionSpeed(): Int {
        return 1
    }

}