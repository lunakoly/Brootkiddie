package ru.cryhards.brootkiddie.ui

import ru.cryhards.brootkiddie.items.Item

/**
 * Wraper for representing items
 */
class ItemActor(val item: Item) : ImageActor(item.iconTexture), Draggable {
    /**
     * Boolean for DragAndDrop
     */
    override var isDragged = false
}