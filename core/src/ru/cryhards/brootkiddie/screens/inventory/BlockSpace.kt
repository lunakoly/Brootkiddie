package ru.cryhards.brootkiddie.screens.inventory

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.scenes.scene2d.ui.Table
import ru.cryhards.brootkiddie.items.Item
import ru.cryhards.brootkiddie.ui.ImageActor

/**
 * Square field of blocks
 */
abstract class BlockSpace(protected val explorer: ItemExplorer) : ScrollPane(Group()) {
    /**
     * The preferred size of a single square block
     */
    protected val prefBlockSize = 76 * Gdx.graphics.density

    /**
     * Holds links to ItemBlocks in the cells
     */
    protected lateinit var field: Array<Array<Item?>>

    /**
     * The actual content
     */
    protected val content = actor as Group

    /**
     * The field
     */
    protected val table = Table()

    /**
     * Use for adding shader effects
     */
    var shader: ShaderProgram? = null


    init {
        content.addActor(table)
        table.width = width
        table.setPosition(0f, 0f)

        this.setOverscroll(false, true)
        this.layout()
    }


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
    fun squeezeUI() {
        table.width = width
    }


    /**
     * BUilds 2d field of blocks and puts
     * items in its cells
     */
    fun buildBlockSpace(rowCount: Int): BlockSpace {
        table.clear()

        val colCount = width.toInt() / prefBlockSize.toInt()
        val blockSize = width / colCount

        table.height = rowCount * blockSize
        content.height = table.height


        field = Array(rowCount) { arrayOfNulls<Item?>(colCount) }


        for (j in 0 until rowCount) {
            for (i in 0 until colCount) {
                // create block bg
                val block = ImageActor("img/ui/inventory_block.png")
                table.add(block).size(blockSize)
            }

            table.row()
        }

        return this
    }

}