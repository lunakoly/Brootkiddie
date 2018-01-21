package ru.cryhards.brootkiddie

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import ru.cryhards.brootkiddie.screens.BaseScreen
import ru.cryhards.brootkiddie.screens.GlobalMapScreen
import java.util.*

class Game : ApplicationAdapter() {
    lateinit internal var batch: SpriteBatch
    internal var screenStack: Stack<BaseScreen> = Stack()

    override fun create() {
        batch = SpriteBatch()
        screenStack.push(GlobalMapScreen(this, batch))
    }

    override fun render() {
        Gdx.gl.glClearColor(173f / 256f, 216f / 256f, 230f / 256f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        if (screenStack.empty()) {
            this.dispose()
            return
        }

        screenStack.peek().update(Gdx.graphics.deltaTime)
        screenStack.peek().render()
    }

    override fun dispose() {
        screenStack.clear()
        batch.dispose()
    }
}
