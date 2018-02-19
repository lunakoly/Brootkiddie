package ru.cryhards.brootkiddie.screens.inventory

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.utils.DragListener
import ru.cryhards.brootkiddie.items.Item

/**
 * Inventory field
 */
class InventoryBlockSpace(explorer: ItemExplorer) : BlockSpace(explorer) {
    /**
     * Used to disable dragging
     */
//    private var captureListener: EventListener = listeners[0]

    /**
     * Fills field with items
     */

    lateinit var pane: ScrollPane

    fun fill(items: ArrayList<Item>) {
        val colCount = width.toInt() / prefBlockSize.toInt()
        val blockSize = width / colCount
        val rowCount = max(
                items.size / colCount + 1,
                truncate(height / blockSize).toInt() + 1)
        buildBlockSpace(rowCount)


        for (k in 0 until items.size) {

            val item = items[k]
            item.setSize(blockSize, blockSize)

            cells[k].setActor<Actor>(item)

//            __  __                                        __                __              __  __
//            /  |/  |                                      /  |              /  |            /  |/  |
//            $$ |$$ |        _______   ______   __    __  _$$ |_     _______ $$ |____        $$ |$$ |
//            $$ |$$ |       /       | /      \ /  |  /  |/ $$   |   /       |$$      \       $$ |$$ |
//            $$ |$$ |      /$$$$$$$/ /$$$$$$  |$$ |  $$ |$$$$$$/   /$$$$$$$/ $$$$$$$  |      $$ |$$ |
//            $$/ $$/       $$ |      $$ |  $$/ $$ |  $$ |  $$ | __ $$ |      $$ |  $$ |      $$/ $$/
//            __  __       $$ \_____ $$ |      $$ \__$$ |  $$ |/  |$$ \_____ $$ |  $$ |       __  __
//            /  |/  |      $$       |$$ |      $$    $$/   $$  $$/ $$       |$$ |  $$ |      /  |/  |
//            $$/ $$/        $$$$$$$/ $$/        $$$$$$/     $$$$/   $$$$$$$/ $$/   $$/       $$/ $$/



            item.addListener(object : ClickListener() {
                override fun clicked(event: InputEvent?, x: Float, y: Float) {
                    explorer.explore(item)
                }
            })

            item.addListener(object : DragListener() {
                override fun drag(event: InputEvent?, x: Float, y: Float, pointer: Int) {
                    var subX = item.x + x - item.width / 2
                    var subY = item.y + y - item.height / 2

                    if (subX < 0) subX = 0f
                    if (subY < 0) subY = 0f

                    if (subX > width - item.width)
                        subX = width - item.width
                    if (subY > height - item.height)
                        subY = height - item.height

                    item.moveBy(subX - item.x, subY - item.y)
                }

                private var oldX = 0f
                private var oldY = 0f

                override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {

                    oldX = item.x
                    oldY = item.y
                    pane.setScrollingDisabled(true, true)
                    super.touchDown(event, x, y, pointer, button)
                    return true
                }

                override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {

                    pane.setScrollingDisabled(false, false)

                    val cellX = (item.x + item.width / 2) / blockSize
                    val cellY = (height - item.y - item.height / 2) / blockSize
//                    item.setPosition(
//                            cellX * blockSize,
//                            table.height - cellY * blockSize,
//                            Align.topLeft)
                    //Gdx.app.log("ASDSADSADSADSADSA", getCell(stage.hit(cellX, cellY, true)))
                    /*
                    if (item is Script && drop != item) {
                        /*
                        when (drop) {
                            is Malware -> {
                                drop.combine(item)
                                Player.Inventory.items.remove(item)
                                this@InventoryBlockSpace.fill(Player.Inventory.items)
                            }
                            is Script -> {
                                val mal = drop + (item)
                                Player.Inventory.items.remove(item)
                                Player.Inventory.items.remove(drop)
                                Player.Inventory.items.add(mal)
                                this@InventoryBlockSpace.fill(Player.Inventory.items)
                            }
                            else -> item.setPosition(oldX, oldY, Align.bottomLeft)

                        }*/
                    } else {
                        item.setPosition(oldX, oldY, Align.bottomLeft)
                    }*/
                }
            })
        }
    }

}