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

            val item = items[k]
            item.setSize(blockSize, blockSize)

            val cont : Container<*>? = cells[k].actor as? Container<*>
            cont!!.actor = item


            if (item !is Malware) {

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
            }

            /**
             * Adding actor that can be dragged on
             */
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

            item.addListener(object : InputListener() {
                override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                    explorer.explore(item)
                    return super.touchDown(event, x, y, pointer, button)
                }
            })
        }
    }

}