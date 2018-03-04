package ru.cryhards.brootkiddie.screens.bench

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import ru.cryhards.brootkiddie.Assets
import ru.cryhards.brootkiddie.Core
import ru.cryhards.brootkiddie.Player
import ru.cryhards.brootkiddie.items.Malware
import ru.cryhards.brootkiddie.items.effects.DisguiseEffect
import ru.cryhards.brootkiddie.items.effects.MiningEffect
import ru.cryhards.brootkiddie.items.effects.SpreadingEffect
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


        // explorer
        explorer.setSize(stage.width / 3.5f, stage.height)
        explorer.setPosition(stage.width, stage.height, Align.topRight)
        stage.addActor(explorer)

        // back
        applyButton.width = explorer.width
        applyButton.setPosition(stage.width - 50, stage.height - 50, Align.topRight)
        stage.addActor(applyButton)

        applyButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                Core.instance.toInventory()
            }
        })

        // blockSpace
        blockSpace.setSize(stage.width - explorer.width, stage.height)
        blockSpace.setPosition(0f, stage.height, Align.topLeft)
        blockSpace.shader = Assets.Shaders.WAVE
        stage.addActor(blockSpace)
    }


    fun inspect(malware: Malware) {
        blockSpace.fill(malware.scripts)
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