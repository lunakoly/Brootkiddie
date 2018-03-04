package ru.cryhards.brootkiddie.screens.inventory

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.badlogic.gdx.scenes.scene2d.ui.Container
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable
import ru.cryhards.brootkiddie.ui.ItemActor

/**
 * Square field of blocks
 */

abstract class BlockSpace(protected val explorer: ItemExplorer) : Table() {
    /**
     * The preferred size of a single square block
     */
    protected val prefBlockSize = 76 * Gdx.graphics.density

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
     * Builds 2d field of blocks and puts
     * items in its cells
     */
    fun buildBlockSpace(rowCount: Int): BlockSpace {
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
}