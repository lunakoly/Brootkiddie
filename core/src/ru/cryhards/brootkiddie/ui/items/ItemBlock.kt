package ru.cryhards.brootkiddie.ui.items

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Actor
import ru.cryhards.brootkiddie.items.Item
import ru.cryhards.brootkiddie.ui.ImageActor

/**
 * UI wrapper for item model
 */
class ItemBlock(var item: Item, var iconTexture: Texture) : ImageActor(iconTexture) {
    /**
     * List of UI components to be added
     * to Explorer tab
     */
    val actions = ArrayList<Actor>()


    override fun toString(): String {
        return item.toString()
    }
}