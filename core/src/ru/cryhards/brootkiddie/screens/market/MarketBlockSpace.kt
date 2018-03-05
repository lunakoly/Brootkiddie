package ru.cryhards.brootkiddie.screens.market

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
import ru.cryhards.brootkiddie.Player
import ru.cryhards.brootkiddie.items.Combinable
import ru.cryhards.brootkiddie.items.Item
import ru.cryhards.brootkiddie.screens.inventory.InventoryBlockSpace
import ru.cryhards.brootkiddie.screens.inventory.ItemExplorer
import ru.cryhards.brootkiddie.ui.ItemActor
import kotlin.math.max
import kotlin.math.truncate


class MarketBlockSpace(val explorer: ItemExplorer) : Table() {
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
    private fun buildBlockSpace(rowCount: Int): MarketBlockSpace {
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


            item.addListener(object : InputListener() {
                override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                    explorer.explore(item.item)
                    return super.touchDown(event, x, y, pointer, button)
                }
            })
        }
    }
}