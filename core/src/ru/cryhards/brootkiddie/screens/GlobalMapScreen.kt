package ru.cryhards.brootkiddie.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import ru.cryhards.brootkiddie.Game

open class GlobalMapScreen(game: Game, batch: SpriteBatch) : BaseScreen(game, batch) {
    val shapeRenderer = ShapeRenderer()
    val countryColor = Color(138f / 255f, 172f / 255f, 184f / 255f, 1f)
    val infectedColor = Color(1f, 64f / 255f, 64f / 255f, 1f)

    init {
        shapeRenderer.projectionMatrix = batch.projectionMatrix

    }

    override fun render() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        shapeRenderer.color = countryColor
        shapeRenderer.rect(width / 4, height / 4, width / 2, height / 2)
        shapeRenderer.color = infectedColor
        shapeRenderer.circle(width / 2, height / 2, height / 100)
        shapeRenderer.end()
    }

    override fun update(deltaT: Float) {

    }

}