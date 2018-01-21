package ru.cryhards.brootkiddie

import com.badlogic.gdx.Game


class ReallyGame : Game() {

    override fun create() {
        setScreen(GlobalMapScreen())
    }

    override fun dispose() {
        getScreen().dispose()
    }
}
