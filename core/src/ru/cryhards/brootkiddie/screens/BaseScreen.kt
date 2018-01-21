package ru.cryhards.brootkiddie.screens

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import ru.cryhards.brootkiddie.Game

abstract class BaseScreen(val game: Game, val batch: SpriteBatch) : Stage() {
    abstract fun render()
    abstract fun update(deltaT: Float)
}