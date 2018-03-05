package ru.cryhards.brootkiddie.screens.browser

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import ru.cryhards.brootkiddie.Core
import ru.cryhards.brootkiddie.ui.ShaderableButton
import ru.cryhards.brootkiddie.ui.UI

/**
 * Screen, where news will be displayed
 */
class BrowserScreen : ScreenAdapter() {
    private val stage = Stage()
    private val backButton = UI.GlitchImageButton("img/ui/back.png")
    private val pageGroup = VerticalGroup()
    private val dialogsButton : ShaderableButton = UI.StaticTextButton("dialogs")
    private val newsButton : ShaderableButton = UI.StaticTextButton("news")
    private val marketButton: ShaderableButton = UI.StaticTextButton("black market")

    init {

        dialogsButton.addListener(object : ClickListener(){
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                Core.instance.toDialogs()
            }
        })

        pageGroup.addActor(dialogsButton)
        //pageGroup.addActor(newsButton)
        pageGroup.addActor(marketButton)
        pageGroup.pack()

        pageGroup.setPosition(Gdx.graphics.width/2f, Gdx.graphics.height/2f, Align.center)
        stage.addActor(pageGroup)

        backButton.setPosition(50f, 50f, Align.bottomLeft)
        stage.addActor(backButton)

        backButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                Core.instance.toGlobalMap()
            }
        })

        marketButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                Core.instance.toMarket()
            }
        })
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(45/255f, 45/255f, 45/255f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        stage.act(delta)
        stage.draw()
    }

    override fun show() {
        Gdx.input.inputProcessor = stage
        super.show()
    }
}