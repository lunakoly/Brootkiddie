package ru.cryhards.brootkiddie.screens.inventory

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.Container
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop
import ru.cryhards.brootkiddie.Player
import ru.cryhards.brootkiddie.items.Combinable
import ru.cryhards.brootkiddie.items.Item
import ru.cryhards.brootkiddie.ui.ItemActor
import kotlin.math.max
import kotlin.math.truncate

/**
 * Inventory field
 */
class InventoryBlockSpace(explorer: ItemExplorer) : BlockSpace(explorer) {

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

        dragAndDrop.clear()


        for (k in 0 until items.size) {
            val item = ItemActor(items[k])
            item.setSize(blockSize, blockSize)

            val cont = cells[k].actor as Container<*>
            cont.actor = item


            /**
             * Listeners for Drag and Drop
             */
            val gestureListener = object : ActorGestureListener() {
                override fun longPress(actor: Actor?, x: Float, y: Float): Boolean {
                    item.isDragged = true
                    item.color = Color(1f, 0f, 0f, 0.3f)
                    return super.longPress(actor, x, y)
                }

                override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                    item.isDragged = false
                    item.color = Color(1f, 1f, 1f, 1f)
                    super.touchUp(event, x, y, pointer, button)
                }
            }

            gestureListener.gestureDetector.setLongPressSeconds(0.6f)
            item.addListener(gestureListener)

            /**
             * Adding actor that can be dragged
             */
            dragAndDrop.addSource(object : DragAndDrop.Source(item) {
                override fun dragStart(event: InputEvent?, x: Float, y: Float, pointer: Int): DragAndDrop.Payload? {

                    // Do not start drag if there was no longpress event
                    if (!item.isDragged) return null

                    val payload = DragAndDrop.Payload()

                    payload.`object` = item.item
                    payload.dragActor = ItemActor(item.item)
                    payload.dragActor.setSize(blockSize, blockSize)

                    val validActor = ItemActor(item.item)
                    validActor.color = Color(.5f, 1f, .5f, 0.3f)
                    validActor.setSize(blockSize, blockSize)
                    payload.validDragActor = validActor

                    return payload
                }

                override fun dragStop(event: InputEvent?, x: Float, y: Float, pointer: Int, payload: DragAndDrop.Payload?, target: DragAndDrop.Target?) {
                    item.isDragged = false
                    item.color = Color(1f, 1f, 1f, 1f)
                }
            })


            /**
             * Adding actor that can be dragged on
             */
            dragAndDrop.addTarget(object : DragAndDrop.Target(item){
                override fun drop(source: DragAndDrop.Source?, payload: DragAndDrop.Payload?, x: Float, y: Float, pointer: Int) {
                    val sourceItem = (source!!.actor as ItemActor).item

                    if (item.item is Combinable) {
                        val res = item.item.combine(sourceItem)
                        Player.Inventory.items.remove(sourceItem)
                        Player.Inventory.items.remove(item.item)
                        Player.Inventory.items.add(res)
                        this@InventoryBlockSpace.fill(Player.Inventory.items)
                        explorer.explore(res)
                    }

                    item.color = Color(1f, 1f, 1f, 1f)
                }

                override fun drag(source: DragAndDrop.Source?, payload: DragAndDrop.Payload?, x: Float, y: Float, pointer: Int): Boolean {
                    if (source!!.actor is ItemActor && (source.actor != item)) {
                        item.color = Color(.5f, 1f, .5f, 0.3f)
                        return true
                    }
                    return false
                }

                override fun reset(source: DragAndDrop.Source?, payload: DragAndDrop.Payload?) {

                    if (source!!.actor != item) item.color = Color(1f, 1f, 1f, 1f)
                }

            })

            item.addListener(object : InputListener() {
                override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                    explorer.explore(item.item)
                    return super.touchDown(event, x, y, pointer, button)
                }
            })
        }
    }

}