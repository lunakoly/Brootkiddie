package ru.cryhards.brootkiddie.cleanup.items.effects

import ru.cryhards.brootkiddie.cleanup.items.Item
import ru.cryhards.brootkiddie.cleanup.items.Malware

/**
 * Mining Effect
 * -------------
 * Increases malware mining speed
 */
class MiningEffect(var miningSpeed: Float = 0.5f) : Item.Effect(
        "Mining Effect",
        "Increases malware mining speed") {

    override fun affect(malware: Malware): Item.Effect {
        malware.stats.miningSpeed += miningSpeed
        return super.affect(malware)
    }

    override fun toString(): String {
        return "Mining Speed: $miningSpeed"
    }
}