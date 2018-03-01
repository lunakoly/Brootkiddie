package ru.cryhards.brootkiddie.items

import com.badlogic.gdx.graphics.Texture
import ru.cryhards.brootkiddie.items.effects.Converter.humanReadable

/**
 * Represents a script that can be combined into malware
 */
open class Script(title: String, info: String, iconTexture: Texture, var size: Float, var level: Int = 1) : Item(title, info, iconTexture, Item.Type.SCRIPT) {
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
    fun combine(script: Script, title: String) = Malware(
            title,
            "", Texture("img/items/malware.png"), this, script)

    /**
     * Adds this to malware and returns it
     */
    fun combine(malware: Malware): Malware {
        malware.combine(this)
        return malware
    }

    override fun toString(): String {
        val stats = affection()
        return "Level: $level\n" +
                "Infectiousness: ${humanReadable(stats.infectiousness)}\n" +
                "Spreading Speed: ${humanReadable(stats.spreadingSpeed)}\n" +
                "Suspiciousness: ${humanReadable(stats.suspiciousness)}\n" +
                "Mining Speed: ${humanReadable(stats.miningSpeed)}"
    }

}