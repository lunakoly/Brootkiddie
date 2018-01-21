package ru.cryhards.brootkiddie.screens.actors

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.scenes.scene2d.Actor

open class GlobalMapActor : Actor() {

    override fun draw(batch: Batch?, parentAlpha: Float) {
        val shaper = ShapeRenderer()
        shaper.projectionMatrix = batch?.projectionMatrix

    }
}