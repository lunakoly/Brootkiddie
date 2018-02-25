package ru.cryhards.brootkiddie.screens.inventory

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.Container
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop
import com.badlogic.gdx.scenes.scene2d.utils.DragListener
import ru.cryhards.brootkiddie.Player
import ru.cryhards.brootkiddie.items.Item
import ru.cryhards.brootkiddie.items.Malware
import ru.cryhards.brootkiddie.items.Script
import ru.cryhards.brootkiddie.ui.ImageActor
import kotlin.math.max
import kotlin.math.truncate

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

            val cont : Container<*>? = cells[k].actor as? Container<*>
            cont!!.actor = item

            dragAndDrop.addSource(object : DragAndDrop.Source(item){
                override fun dragStart(event: InputEvent?, x: Float, y: Float, pointer: Int): DragAndDrop.Payload? {
                    if (!item.isDragged) return null

                    val payload = DragAndDrop.Payload()

                    payload.`object` = item

                    payload.dragActor = ImageActor(item.iconTexture)

                    val validActor = ImageActor(item.iconTexture)
                    validActor.color = Color(0f, 1f, 0f, 0.3f)
                    payload.validDragActor = validActor

                    return payload
                }

                override fun dragStop(event: InputEvent?, x: Float, y: Float, pointer: Int, payload: DragAndDrop.Payload?, target: DragAndDrop.Target?) {
                    item.isDragged = false
                    item.color = Color(1f, 1f, 1f, 1f)
                }


            })

            dragAndDrop.addTarget(object : DragAndDrop.Target(item){
                override fun drop(source: DragAndDrop.Source?, payload: DragAndDrop.Payload?, x: Float, y: Float, pointer: Int) {

                    when (source!!.actor) {
                        is Malware -> {
                            (source.actor as Malware).combine(item as Script)
                            Player.Inventory.items.remove(item)
                            this@InventoryBlockSpace.fill(Player.Inventory.items)
                        }
                        is Script -> {
                            val mal = (source.actor as Script) + (item as Script)
                            Player.Inventory.items.remove(item)
                            Player.Inventory.items.remove(source.actor as Script)
                            Player.Inventory.items.add(mal)
                            this@InventoryBlockSpace.fill(Player.Inventory.items)
                        }
                    }

                    item.color = Color(1f, 1f, 1f, 1f)
                }

                override fun drag(source: DragAndDrop.Source?, payload: DragAndDrop.Payload?, x: Float, y: Float, pointer: Int): Boolean {
                    if (source!!.actor is Item && (source.actor != item)) {
                        item.color = Color(0f, 1f, 0f, 0.3f)
                        return true
                    }
                    return false
                }

                override fun reset(source: DragAndDrop.Source?, payload: DragAndDrop.Payload?) {

                    if (source!!.actor != item) item.color = Color(1f, 1f, 1f, 1f)
                }

            })

//            __  __                                        __                __              __  __
//            /  |/  |                                      /  |              /  |            /  |/  |
//            $$ |$$ |        _______   ______   __    __  _$$ |_     _______ $$ |____        $$ |$$ |
//            $$ |$$ |       /       | /      \ /  |  /  |/ $$   |   /       |$$      \       $$ |$$ |
//            $$ |$$ |      /$$$$$$$/ /$$$$$$  |$$ |  $$ |$$$$$$/   /$$$$$$$/ $$$$$$$  |      $$ |$$ |
//            $$/ $$/       $$ |      $$ |  $$/ $$ |  $$ |  $$ | __ $$ |      $$ |  $$ |      $$/ $$/
//            __  __       $$ \_____ $$ |      $$ \__$$ |  $$ |/  |$$ \_____ $$ |  $$ |       __  __
//            /  |/  |      $$       |$$ |      $$    $$/   $$  $$/ $$       |$$ |  $$ |      /  |/  |
//            $$/ $$/        $$$$$$$/ $$/        $$$$$$/     $$$$/   $$$$$$$/ $$/   $$/       $$/ $$/



            item.addListener(object : InputListener() {
                override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                    explorer.explore(item)
                    return super.touchDown(event, x, y, pointer, button)
                }
            })
            /**
            item.addListener(object : ClickListener()
            {
                override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                    pane.setScrollingDisabled(true, true)
                    Gdx.app.log("SADA", pane.isScrollingDisabledY.toString())
                    return super.touchDown(event, x, y, pointer, button)
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
                    //pane.setScrollingDisabled(true, true)
                }

                private var oldX = 0f
                private var oldY = 0f

                override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {

                    oldX = item.x
                    oldY = item.y

                    super.touchDown(event, x, y, pointer, button)
                    return true
                }

                override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {

                    //pane.setScrollingDisabled(false, false)

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
            **/
        }
    }

}