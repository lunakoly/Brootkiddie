package ru.cryhards.brootkiddie.items.effects

import ru.cryhards.brootkiddie.items.Item
import ru.cryhards.brootkiddie.items.Malware

/**
 * Mining Effect
 * -------------
 * Increases malware mining speed
 */
class MiningEffect(var miningSpeed: Float = 0.5f) : Item.Effect(
        "Mining Effect",
        "Increases malware mining speed") {

    override fun affect(target: Any?, vararg dependencies: Any?): Item.Effect {
        // TODO: make dependencies[0] (script level) affect the affection)))
        (target as Malware.Stats).miningSpeed += miningSpeed
        return super.affect(target)
    }

    override fun toString(): String {
        return "Mining Speed: $miningSpeed"
    }
}