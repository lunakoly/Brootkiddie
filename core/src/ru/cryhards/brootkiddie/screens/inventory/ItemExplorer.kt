package ru.cryhards.brootkiddie.screens.inventory

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.Value
import com.badlogic.gdx.utils.Align
import ru.cryhards.brootkiddie.Assets
import ru.cryhards.brootkiddie.items.Item
import ru.cryhards.brootkiddie.items.Script
import ru.cryhards.brootkiddie.ui.ImageActor
import ru.cryhards.brootkiddie.ui.UI

/**
 * Table that displats item information
 */
class ItemExplorer : ScrollPane(Table()) {
    private val icon = ImageActor("img/ui/empty.png")
    private val name = UI.StaticLabel("<title>")
    private val info = UI.StaticLabel("<info>")
    private val data = UI.StaticLabel("<item dependent data>")
    private val actions = Group()


    init {
        val table = actor as Table

        // title
        table.add(name).width(Value.percentWidth(1f, this)).row()
        name.style.fontColor = Color.LIGHT_GRAY
        name.setAlignment(Align.center)
        name.style.background = null

        // icon
        val iconCell = table.add(icon).width(Value.percentWidth(1f, this))
        iconCell.height(iconCell.prefWidthValue).row()
        icon.shader = Assets.Shaders.ABERRATION


        // info & data
        val textTable = Table().padLeft(50f)
        table.add(textTable).width(Value.percentWidth(1f, this)).row()

        // info
        info.setWrap(true)
        info.style.background = null
        info.style.fontColor = Color.LIGHT_GRAY
        textTable.add(info).width(Value.percentWidth(1f, this)).row()

        // data
        data.setWrap(true)
        data.style.background = null
        data.style.fontColor = Color.LIGHT_GRAY
        textTable.add(data).width(Value.percentWidth(1f, this)).row()

        // actions
        table.add(actions).width(Value.percentWidth(1f, this)).height(Value.percentHeight(1f, actions)).row()


        setOverscroll(false, true)
        layout()
        explore(UI.emptyItem())
    }


    /**
     * Displays information about item
     */
    fun explore(block: Item) {
        name.setText(block.title)
        info.setText(block.info + if (block is Script) {
            "\n" + "Level: ${block.level}"
        } else "")
        icon.drawable = Image(block.iconTexture).drawable
        data.setText(block.toString())

        actions.clear()
        actions.height = 20f

        block.actions.forEach {
            it.setPosition(actions.width / 2, actions.height, Align.bottom)
            actions.height += it.height
            actions.addActor(it)
        }

        actions.height += 20f
    }


    /**
     * Use for adding shader effects
     */
    var shader: ShaderProgram? = null

    override fun draw(batch: Batch?, parentAlpha: Float) {
        if (shader != null) {
            val old = batch?.shader
            batch?.shader = shader
            super.draw(batch, parentAlpha)
            batch?.shader = old

        } else
            super.draw(batch, parentAlpha)
    }
}