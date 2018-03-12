package ru.cryhards.brootkiddie.screens.bench

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import ru.cryhards.brootkiddie.Assets
import ru.cryhards.brootkiddie.Core
import ru.cryhards.brootkiddie.items.Malware
import ru.cryhards.brootkiddie.items.Scripts
import ru.cryhards.brootkiddie.screens.inventory.ItemExplorer
import ru.cryhards.brootkiddie.ui.UI

/**
 * Malware crafting
 */
class BenchScreen : ScreenAdapter() {
    private val stage = Stage()

    private val background = Image(UI.colorToDrawable(Color(.07f, .07f, .07f, 1f)))
    private val applyButton = UI.GlitchTextButton("APPLY")
    private val explorer = ItemExplorer()
    private val blockSpace = BenchBlockSpace(explorer)


    init {
        // background
        background.setPosition(0f, 0f)
        background.setSize(stage.width, stage.height)
        stage.addActor(background)


        // back
        applyButton.width = stage.width / 3.5f - 50
        applyButton.setPosition(stage.width - 50, stage.height - 50, Align.topRight)
        stage.addActor(applyButton)

        applyButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                Core.instance.toInventory()
            }
        })

        // explorer
        explorer.setSize(applyButton.width + 100, stage.height - applyButton.height - 100)
        explorer.setPosition(stage.width, stage.height - applyButton.height - 100, Align.topRight)
        stage.addActor(explorer)

        // blockSpace
        val pane = ScrollPane(blockSpace)
        pane.setSize(stage.width - explorer.width, stage.height)
        pane.setPosition(0f, stage.height, Align.topLeft)
        blockSpace.setSize(pane.width, pane.height)
        blockSpace.shader = Assets.Shaders.WAVE
        stage.addActor(pane)
    }


    fun inspect(malware: Malware) {
        explorer.explore(malware.scripts.firstOrNull() ?: Scripts.emptyItem())
        blockSpace.inspect(malware)
    }


    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        stage.act(delta)
        stage.draw()

        super.render(delta)
    }

    override fun show() {
        Gdx.input.inputProcessor = stage
        super.show()
    }

}