package ru.cryhards.brootkiddie.screens.inventory

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Cell
import com.badlogic.gdx.scenes.scene2d.ui.Container
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop
import com.badlogic.gdx.scenes.scene2d.utils.Drawable
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable
import ru.cryhards.brootkiddie.items.Item
import ru.cryhards.brootkiddie.ui.ImageActor

/**
 * Square field of blocks
 */
/**
 * CHTO ZA GOVNO?!!?!?!?! KOSTYA, BLYAT!
 */
abstract class BlockSpace(protected val explorer: ItemExplorer) : Table() {
    /**
     * The preferred size of a single square block
     */
    protected val prefBlockSize = 76 * Gdx.graphics.density

    /**
     * Holds links to ItemBlocks in the cells
     */
    protected lateinit var field: Array<Array<Item?>>

    /**
     * Use for adding shader effects
     */
    var shader: ShaderProgram? = null

    /**
     * For DragAndDrop
     */
    val dragAndDrop = DragAndDrop()


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
     * Updates width and height of inner
     * UI components according to parent size
     */



    /**
     * Builds 2d field of blocks and puts
     * items in its cells
     */
    fun buildBlockSpace(rowCount: Int): BlockSpace {
        clear()

        val colCount = width.toInt() / prefBlockSize.toInt()
        val blockSize = width / colCount
        dragAndDrop.clear()

        field = Array(rowCount) { arrayOfNulls<Item?>(colCount) }


        for (j in 0 until rowCount) {
            for (i in 0 until colCount) {
                // create block bg
            val block = Container<Item>()
            block.background = SpriteDrawable(Sprite(Texture("img/ui/inventory_block.png")))
            val cell = add(block).size(blockSize)
            }

            row()
        }

        return this
    }

}