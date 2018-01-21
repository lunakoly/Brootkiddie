package ru.cryhards.brootkiddie

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import ru.cryhards.brootkiddie.screens.BaseScreen
import ru.cryhards.brootkiddie.screens.GlobalMapScreen
import java.util.*

class Game : ApplicationAdapter() {
    lateinit var batch: SpriteBatch
    var screenStack: Stack<BaseScreen> = Stack()
    var player = Player()

    val FPS = 45f
    private val SPF = 1f / FPS
    private var prevRenderTime = 0f
    private var curTime = 0f

    override fun create() {
        batch = SpriteBatch()
        screenStack.push(GlobalMapScreen(this, batch))
        player.loadProfile()
    }

    override fun render() {
        Gdx.gl.glClearColor(173f / 256f, 216f / 256f, 230f / 256f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        if (screenStack.empty()) {
            this.dispose()
            return
        }

        screenStack.peek().act(Gdx.graphics.deltaTime)

        curTime += Gdx.graphics.deltaTime
        if (curTime - prevRenderTime > SPF) {
            screenStack.peek().draw()
            prevRenderTime = curTime
        }
    }

    override fun dispose() {
        screenStack.clear()
        batch.dispose()
    }
}
