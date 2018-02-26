package ru.cryhards.brootkiddie.items

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Actor
import ru.cryhards.brootkiddie.ui.Draggable
import ru.cryhards.brootkiddie.ui.ImageActor

/**
 * Represents an item that the Player may own
 */
open class Item(var title: String, var info: String, var iconTexture: Texture, val type: Type) : ImageActor(iconTexture), Draggable{
    /*
        Boolean for DragAndDrop
     */
    override var isDragged = false

    /**
     * Represents an item type
     */
    enum class Type(val info: String) {
        SCRIPT("Code written by the community that can do something. Combining the right code pieces will increment the common malware effect. Some scripts may have dependencies to another ones (frameworks)"),
        MALWARE("A multifunctional set of cooperating scripts (source codes)")
    }


    /**
     * Holds all effects applied to the item
     */
    val effects = ArrayList<Effect>()

    open fun combine(effect: Effect): Item {
        title += effect.title.subSequence(0, 3) // TODO: wtf is dis
        info += " and " + effect.title
        effects.add(effect)
        return this
    }

    open operator fun plus(effect: Effect) = combine(effect)

    /**
     * Returns effect with matching title
     */
    fun findEffect(name: String) = effects.find { it.title == name }


    /**
     * List of UI components to be added
     * to Explorer tab
     */
    val actions = ArrayList<Actor>()


    /**
     * Represents an effect that can be applied to an item
     */
    open class Effect(val title: String, val info: String) {
        /**
         * Affects he given target according to the given dependencies
         */
        open fun affect(target: Any?, vararg dependencies: Any?): Effect {
            return this
        }
    }


    override fun toString(): String {
        return "Some unknown item"
    }

}