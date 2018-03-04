package ru.cryhards.brootkiddie.screens.bench

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop
import com.badlogic.gdx.utils.Align
import ru.cryhards.brootkiddie.Assets
import ru.cryhards.brootkiddie.items.Script
import ru.cryhards.brootkiddie.screens.inventory.ItemExplorer
import ru.cryhards.brootkiddie.ui.ImageActor
import ru.cryhards.brootkiddie.ui.ItemActor
import kotlin.math.truncate

/**
 * Inventory field
 */
@Suppress("PrivatePropertyName")
class BenchBlockSpace(val explorer: ItemExplorer) : Group() {
    /**
     * The preferred size of a single square block
     */
    private val prefBlockSize = 76 * Gdx.graphics.density

    /**
     * Used to determine free slots
     */
    private var field = ArrayList<Array<ItemActor?>>()

    /**
     * Use for adding shader effects
     */
    var shader: ShaderProgram? = null

    /**
     * For DragAndDrop
     */
    private val dragAndDrop = DragAndDrop()

    /**
     * Last inspected scripts
     */
    private lateinit var lastInspectedItems: ArrayList<Script>

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
    private fun buildBlockSpace(rowCount: Int): BenchBlockSpace {
        clear()

        val colCount = width.toInt() / prefBlockSize.toInt()
        val blockSize = width / colCount

        height = rowCount * blockSize

        for (j in 0 until rowCount) {
            for (i in 0 until colCount) {
                // container for items
                val slot = ImageActor("img/ui/inventory_block.png")
                slot.setSize(blockSize, blockSize)
                slot.setPosition(i * blockSize, height - j * blockSize, Align.topLeft)
                addActor(slot)


                // SETTING UP DROPPING
                dragAndDrop.addTarget(object : DragAndDrop.Target(slot){
                    override fun drop(source: DragAndDrop.Source?, payload: DragAndDrop.Payload?, x: Float, y: Float, pointer: Int) {
                        val sourceItem = (source!!.actor as ItemActor)
                        val scriptItem = sourceItem.item as Script


                        // unfill old grid
                        for (ii in scriptItem.gridX until scriptItem.gridX + scriptItem.size)
                            for (jj in scriptItem.gridY until scriptItem.gridY + scriptItem.size)
                                field[jj][ii] = null

                        scriptItem.gridY = j
                        scriptItem.gridX = i

                        // fill new grid
                        for (ii in scriptItem.gridX until scriptItem.gridX + scriptItem.size)
                            for (jj in scriptItem.gridY until scriptItem.gridY + scriptItem.size)
                                field[jj][ii] = sourceItem


                        sourceItem.color = COLOR_NORMAL
                        fill(lastInspectedItems)
                    }

                    override fun drag(source: DragAndDrop.Source?, payload: DragAndDrop.Payload?, x: Float, y: Float, pointer: Int): Boolean {
                        val item = (source!!.actor as ItemActor)

                        if (canPlace(item.item as Script, i, j)) {
                            item.color = COLOR_SELECT
                            return true
                        }
                        return false
                    }

                    override fun reset(source: DragAndDrop.Source?, payload: DragAndDrop.Payload?) {
                        source!!.actor.color = COLOR_SELECT
                    }

                })
            }
        }

        return this
    }


    /**
     * Returns true if there is much enough space
     * to place that script
     */
    private fun canPlace(script: Script, i: Int, j: Int): Boolean {
        if (j > field.size)
            return true

        for (ii in i until i + script.size) {
            for (jj in j until j + script.size) {
                if (field[jj][ii] != null)
                    return false
            }
        }

        return true
    }


    /**
     * Returns suitable empty position
     */
    private fun findSuitable(scriptItem: Script, w: Int, h: Int): Pair<Int, Int> {
        for (j in 0 until h)
            for (i in 0 until w)
                if (canPlace(scriptItem, i, j))
                    return Pair(i, j)
        return Pair(-1, -1)
    }


    /**
     * Prints field to stdout
     */
    private fun printField() {
        for (i in 0 until field.size) {
            print("$i: ")
            for (j in 0 until field[i].size)
                print(if (field[i][j] == null) "0" else "1")
            println()
        }
    }


    /**
     * Fills field with items
     */
    fun fill(items: ArrayList<Script>) {
        lastInspectedItems = items

        val colCount = width.toInt() / prefBlockSize.toInt()
        val blockSize = width / colCount
        val minRowCount = truncate(parent.height / blockSize).toInt() + 1

        field.clear()
        for (i in 0 until minRowCount)
            field.add( arrayOfNulls<ItemActor?>(colCount) )


        // these items with valid [x y] will be added after
        // building the field
        val cachedItems = ArrayList<ItemActor>()


        for (k in 0 until items.size) {
            if (items[k].size > colCount) {
                Gdx.app.log("BenchBlockSpace", "Size of ${items[k].title} is too big for that screen ($colCount)")
                continue
            }

            val scriptItem = items[k]
            val itemActor = ItemActor(items[k])
            itemActor.setSize(
                    blockSize * scriptItem.size,
                    blockSize * scriptItem.size)
            cachedItems.add(itemActor)


            // try restore saves
            if (canPlace(scriptItem, scriptItem.gridX, scriptItem.gridY)) {

                // enlarge field in needed
                for (l in 0 until scriptItem.gridY + 1 - field.size)
                    field.add(arrayOfNulls<ItemActor?>(colCount))

                // fill grid
                for (ii in scriptItem.gridX until scriptItem.gridX + scriptItem.size)
                    for (jj in scriptItem.gridY until scriptItem.gridY + scriptItem.size)
                        field[jj][ii] = itemActor

                continue
            }

            val xy = findSuitable(
                    scriptItem,
                    colCount - scriptItem.size + 1,
                    field.size + scriptItem.size)

            // place
            scriptItem.gridX = xy.first
            scriptItem.gridY = xy.second

            // enlarge field in needed
            for (l in 0 until xy.second + 1 - field.size)
                field.add(arrayOfNulls<ItemActor?>(colCount))

            // fill grid
            for (jj in xy.first until xy.first + scriptItem.size)
                for (ii in xy.second until xy.second + scriptItem.size)
                    field[ii][jj] = itemActor

        }

        printField()
        buildBlockSpace(field.size)

        // place cached items where they should be
        for (item in cachedItems) {
            val scriptItem = item.item as Script
            item.setPosition(
                    scriptItem.gridX * blockSize,
                    height - scriptItem.gridY * blockSize,
                    Align.topLeft)
            addActor(item)


            // CLICK
            item.addListener(object : InputListener() {
                override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                    explorer.explore(item.item)
                    return super.touchDown(event, x, y, pointer, button)
                }
            })


            // DRAG AND DROP
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


            // REGISTERING DRAGGABLE
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
        }
    }

}