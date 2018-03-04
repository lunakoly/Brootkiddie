package ru.cryhards.brootkiddie.items

import com.badlogic.gdx.graphics.Texture

/**
 * Represents a script that can be combined into malware
 */
class Script(title: String, info: String, iconTexture: Texture, var size: Int) : Item(title, info, iconTexture, Item.Type.SCRIPT), Combinable {

    /**
     * The script level affets effects stats
     * Initial value = 1
     */
    var level = 1

    /**
     * Used by BlockSpace's to determin saved position
     */
    var gridX: Int = 0

    /**
     * Used by BlockSpace's to determin saved position
     */
    var gridY: Int = 0


    /**
     * Returns the result of affecting the empty
     * malware stats. Use it to modulate
     * the target malware by
     */
    fun affection(): Malware.Stats {
        // script effects are applied to virtual stats first

        val affected = Malware.Stats()
        effects.forEach { it.affect(affected, level) }
        return affected
    }


    /**
     * Returns malware that is a combination of the 2 scripts
     */
    fun combine(script: Script) = Malware(
            this.title.substring(0, 3) + " + " + script.title.substring(0, 3),
            "Combo of " + this.title + " and " + script.title, Texture("img/items/malware.png"), this, script)

    /**
     * Adds this to malware and returns it
     */
    fun combine(malware: Malware): Malware {
        malware.scripts.add(this)
        return malware
    }

    operator fun plus(script: Script) = combine(script)

    operator fun plus(malware: Malware) = combine(malware)


    /**
     * Combines the given item with itself
     */
    override fun combine(item: Item): Item {
        if (item is Script)
            return combine(item)
        if (item is Malware)
            return combine(item)
        return this
    }

    operator fun plus(item: Item) = combine(item)

}