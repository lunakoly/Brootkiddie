package ru.cryhards.brootkiddie.items

import com.badlogic.gdx.graphics.Texture

/**
 * Represents a script that can be combined into malware
 */
class Script(title: String, info: String, iconTexture: Texture, var size: Float) : Item(title, info, iconTexture, Item.Type.SCRIPT) {

    /**
     * The script level affets effects stats
     * Initial value = 1
     */
    var level = 1


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

}