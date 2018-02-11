package ru.cryhards.brootkiddie.screens.bench

import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import ru.cryhards.brootkiddie.ui.ImageActor
import ru.cryhards.brootkiddie.ui.items.ItemBlock
import ru.cryhards.brootkiddie.ui.items.ItemExplorer
import kotlin.math.max
import kotlin.math.truncate

/**
 * Square field of blocks
 */
class BlockSpace(private val explorer: ItemExplorer) : ScrollPane(Group()) {
    /**
     * The preferred size of a single square block
     */
    private val prefBlockSize = 200

    /**
     * Holds links to ItemBlocks in the cells
     */
    private lateinit var field: Array<Array<ItemBlock?>>

    /**
     * The actual content
     */
    private val content = actor as Group

    /**
     * The field
     */
    private val table = Table()


    init {
        content.addActor(table)
        table.width = width
        table.setPosition(0f, 0f)

        setOverscroll(false, false)
        layout()
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
    fun buildBlockSpace(items: ArrayList<ItemBlock>) {
        table.clear()

        val colCount = width.toInt() / prefBlockSize
        val blockSize = width / colCount
        val rowCount = max(
                items.size / colCount + 1,
                truncate(height / blockSize).toInt() + 1)


        table.height = rowCount * blockSize
        content.height = table.height


        field = Array(rowCount) { arrayOfNulls<ItemBlock?>(colCount) }


        for (j in 0 until rowCount) {
            for (i in 0 until colCount) {
                // create block bg
                val block = ImageActor("img/ui/inventory_block.png")
                table.add(block).size(blockSize)

                // put item in it if possible
                if (j * rowCount + i < items.size) {
                    val item = items[j * rowCount + i]
                    item.setSize(blockSize, blockSize)
                    item.setPosition(
                            i * blockSize,
                            table.height - j * blockSize,
                            Align.topLeft)

                    content.addActor(item)
                    field[j][i] = item


                    item.addListener(object : ClickListener() {
                        override fun clicked(event: InputEvent?, x: Float, y: Float) {
                            explorer.explore(item)
                        }
                    })

                    // TODO: dragging
                }
            }

            table.row()
        }

    }

}