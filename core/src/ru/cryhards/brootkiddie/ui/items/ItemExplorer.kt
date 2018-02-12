package ru.cryhards.brootkiddie.ui.items

import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.Value
import com.badlogic.gdx.utils.Align
import ru.cryhards.brootkiddie.ui.ImageActor
import ru.cryhards.brootkiddie.ui.UI

/**
 * Table that displats item information
 */
class ItemExplorer : ScrollPane(Table()) {
    private val icon = ImageActor("img/ui/empty.png")
    private val name = UI.StaticLabel("<name>")
    private val info = UI.StaticLabel("<info>")
    private val data = UI.StaticLabel("<item dependent data>")
    private val actions = Group()


    init {
        val table = actor as Table

        // name
        table.add(name).width(Value.percentWidth(1f, this)).row()
        name.setAlignment(Align.center)
        name.style.background = null

        // icon
        val iconCell = table.add(icon).width(Value.percentWidth(1f, this))
        iconCell.height(iconCell.prefWidthValue).row()

        // info
        info.setWrap(true)
        table.add(info).width(Value.percentWidth(1f, this))
        table.row()
        info.style.background = null

        // data
        data.setWrap(true)
        table.add(data).width(Value.percentWidth(1f, this))
        table.row()
        data.style.background = null

        // actions
        table.add(actions).width(Value.percentWidth(1f, this)).height(Value.percentHeight(1f, actions)).row()


        setOverscroll(false, false)
        layout()
        explore(UI.emptyItem())
    }


    /**
     * Displays information about item
     */
    fun explore(block: ItemBlock) {
        name.setText(block.item.name)
        info.setText(block.item.info)
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
     * Updates width and height of inner
     * UI components according to parent size
     */
//    fun squeezeUI() {
//        info.squeeze(width)
//        data.squeeze(width)
//    }
}