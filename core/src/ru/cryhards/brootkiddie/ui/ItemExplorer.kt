package ru.cryhards.brootkiddie.ui

import com.badlogic.gdx.scenes.scene2d.ui.Table

/**
 * Table that displats item information
 */
class ItemExplorer : Table() {
    private val icon = ImageActor("img/ui/empty.png")
    private val name = UI.StaticLabel("<name>")
    private val info = UI.StaticTextArea("<info>")
    private val data = UI.StaticTextArea("<item dependent data>")

    init {
        add(name)
        row()

        add(icon)
//        icon.height = icon.width
        row()

        add(info)
        row()
        add(data)
        row()

        println(name.width)
        println(name.height)

        // TODO table

//        setFillParent(true)
    }

    fun explore(block: ItemBlock) {
        clear()

        add(name)
        name.setText(block.item.name)
        row()

        add(icon)
        icon.drawable = block.iconDrawable
        row()

        add(info)
        info.text = block.item.name
        row()

        add(data)
        data.text = block.toString()
    }

}