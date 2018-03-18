package ru.cryhards.brootkiddie.screens.inventory

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.Container
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable
import ru.cryhards.brootkiddie.Assets
import ru.cryhards.brootkiddie.Environment
import ru.cryhards.brootkiddie.items.Combinable
import ru.cryhards.brootkiddie.items.Item
import ru.cryhards.brootkiddie.ui.ItemActor
import kotlin.math.max
import kotlin.math.truncate

/**
 * Inventory field
 */
@Suppress("PrivatePropertyName")
class InventoryBlockSpace(val explorer: ItemExplorer) : Table() {
    /**
     * The preferred size of a single square block
     */
    private val prefBlockSize = 76 * Gdx.graphics.density

    /**
     * Use for adding shader effects
     */
    var shader: ShaderProgram? = null

    /**
     * For DragAndDrop
     */
    private val dragAndDrop = DragAndDrop()

    /**
     * Item color mask when combination must succeed
     */
    private val COLOR_ACCEPT = Color(.5f, .8f, 1f, 1f)

    /**
     * Item color mask for selected item
     */
    private val COLOR_SELECT = Color(.3f, .3f, .3f, 1f)

    /**
     * Item color mask for selected item
     */
    private val COLOR_NORMAL = Color(1f, 1f, 1f, 1f)


    override fun draw(batch: Batch?, parentAlpha: Float) {
        if (shader != null) {
            val old = batch?.shader
            batch?.shader = shader
            super.draw(batch, parentAlpha)
            batch?.shader = old

        } else
            super.draw(batch, parentAlpha)
    }


    /**
     * Builds 2d field of blocks and puts
     * items in its cells
     */
    private fun buildBlockSpace(rowCount: Int): InventoryBlockSpace {
        clear()

        val colCount = width.toInt() / prefBlockSize.toInt()
        val blockSize = width / colCount


        for (j in 0 until rowCount) {
            for (i in 0 until colCount) {
                // container for items
                val block = Container<ItemActor>()
                block.background = SpriteDrawable(Sprite(Texture("img/ui/inventory_block.png")))
                add(block).size(blockSize)
            }

            row()
        }

        return this
    }


    /**
     * Fills field with items
     */
    fun fill(items: ArrayList<Item>) {
        val colCount = width.toInt() / prefBlockSize.toInt()
        val blockSize = width / colCount
        val rowCount = max(
                items.size / colCount + 1,
                truncate(parent.height / blockSize).toInt() + 1)
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
                    item.color = COLOR_SELECT
                    item.shader = Assets.Shaders.GLITCH
                    return super.longPress(actor, x, y)
                }

                override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                    item.isDragged = false
                    item.color = COLOR_NORMAL
                    item.shader = null
                    super.touchUp(event, x, y, pointer, button)
                }
            }

            gestureListener.gestureDetector.setLongPressSeconds(0.1f)
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
                    validActor.color = COLOR_ACCEPT
                    validActor.setSize(blockSize, blockSize)
                    payload.validDragActor = validActor

                    return payload
                }

                override fun dragStop(event: InputEvent?, x: Float, y: Float, pointer: Int, payload: DragAndDrop.Payload?, target: DragAndDrop.Target?) {
                    item.isDragged = false
                    item.color = COLOR_NORMAL
                    item.shader = null
                }
            })


            /**
             * Adding actor that can be dragged on
             */
            dragAndDrop.addTarget(object : DragAndDrop.Target(item){
                override fun drop(source: DragAndDrop.Source?, payload: DragAndDrop.Payload?, x: Float, y: Float, pointer: Int) {
                    val sourceItem = (source!!.actor as ItemActor).item

                    if (item.item is Combinable) {

                        val temp = item.item.clone()

                        val res = item.item.combine(sourceItem)
                        Environment.instance.player.inventory.items.remove(item.item)
                        Environment.instance.player.inventory.items.add(res)
                        Environment.instance.player.inventory.items.add(temp as Item)
                        this@InventoryBlockSpace.fill(Environment.instance.player.inventory.items)
                        explorer.explore(res)
                    }

                    item.color = COLOR_NORMAL
                }

                override fun drag(source: DragAndDrop.Source?, payload: DragAndDrop.Payload?, x: Float, y: Float, pointer: Int): Boolean {
                    if (source!!.actor is ItemActor && (source.actor != item)) {
                        item.color = COLOR_ACCEPT
                        return true
                    }
                    return false
                }

                override fun reset(source: DragAndDrop.Source?, payload: DragAndDrop.Payload?) {

                    if (source!!.actor != item) item.color = COLOR_NORMAL
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