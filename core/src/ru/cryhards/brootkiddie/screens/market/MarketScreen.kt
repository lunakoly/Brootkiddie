package ru.cryhards.brootkiddie.screens.market

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
import ru.cryhards.brootkiddie.Player
import ru.cryhards.brootkiddie.screens.inventory.InventoryBlockSpace
import ru.cryhards.brootkiddie.screens.inventory.ItemExplorer
import ru.cryhards.brootkiddie.ui.UI

/**
 * One of the screens that guys needed
 */
class MarketScreen : ScreenAdapter() {
    private val stage = Stage()

    private val background = Image(UI.colorToDrawable(Color(.07f, .07f, .07f, 1f)))
//    private val background = Image(UI.colorToDrawable(Color(.0f, .0f, .0f, 1f)))


    private val crypto = UI.GlitchLabel("  $100  ")
    private val backButton = UI.GlitchImageButton("img/ui/back.png")

    private val explorer = ItemExplorer()
    private val blockSpace = InventoryBlockSpace(explorer)

    init {
        // background
        background.setPosition(0f, 0f)
        background.setSize(stage.width, stage.height)
        stage.addActor(background)


        // back
        backButton.setPosition(50f, 50f, Align.bottomLeft)
        stage.addActor(backButton)

        backButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                Core.instance.toGlobalMap()
            }
        })

        // crypto
        crypto.setPosition(50f, stage.height, Align.topLeft)
        crypto.setSize(backButton.width, crypto.height)
        crypto.style.background = null
        stage.addActor(crypto)

        // explorer
        explorer.setSize(stage.width / 3.5f, stage.height)
        explorer.setPosition(stage.width, stage.height, Align.topRight)
        stage.addActor(explorer)

        // blockSpace
        val pane = ScrollPane(blockSpace)
        blockSpace.setSize(stage.width - explorer.width - backButton.width - 100f, stage.height)
        pane.debug = true
        pane.setSize(stage.width - explorer.width - backButton.width - 100f, stage.height)
        //pane.squeezeUI()
        pane.setPosition(stage.width - explorer.width, stage.height, Align.topRight)

        blockSpace.shader = Assets.Shaders.WAVE
        blockSpace.pane = pane
        stage.addActor(pane)

        // test bench
        Player.Inventory.items.add(UI.emptyItem())
        Player.Inventory.items.add(UI.loremItem())
    }


    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        stage.act(delta)
        stage.draw()

        super.render(delta)
    }

    override fun show() {
        blockSpace.fill(MarketItems.generateItems())
        Gdx.input.inputProcessor = stage
        super.show()
    }

}