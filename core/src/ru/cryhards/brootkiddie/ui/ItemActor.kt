package ru.cryhards.brootkiddie.ui

import com.badlogic.gdx.graphics.Texture
import ru.cryhards.brootkiddie.items.Item

/**
 * Wraper for representing items
 */
class ItemActor(val item: Item) : ImageActor(Texture(item.iconTexturePath)), Draggable {
    /**
     * Boolean for DragAndDrop
     */
    override var isDragged = false
}