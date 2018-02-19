package ru.cryhards.brootkiddie.screens.bench

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import ru.cryhards.brootkiddie.items.Script
import ru.cryhards.brootkiddie.screens.inventory.BlockSpace
import ru.cryhards.brootkiddie.screens.inventory.ItemExplorer

/**
 * Inventory field
 */
class BenchBlockSpace(explorer: ItemExplorer) : BlockSpace(explorer) {

    /**
     * Fills field with items
     */
    fun fill(items: ArrayList<Script>) {
        val colCount = width.toInt() / prefBlockSize.toInt()
        val blockSize = width / colCount
        val rowCount = max(
                items.size / colCount + 1,
                truncate(height / blockSize).toInt() + 1)
        buildBlockSpace(rowCount)


        for (k in 0 until items.size) {
            val i = k % rowCount
            val j = k / rowCount

            val item = items[k / rowCount + k]
            item.setSize(blockSize, blockSize)
            item.setPosition(
                    i * blockSize,
                    height - j * blockSize,
                    Align.topLeft)

            addActor(item)
            field[j][i] = item


            item.addListener(object : ClickListener() {
                override fun clicked(event: InputEvent?, x: Float, y: Float) {
                    explorer.explore(item)
                }
            })

            // TODO: dragging
        }
    }

}