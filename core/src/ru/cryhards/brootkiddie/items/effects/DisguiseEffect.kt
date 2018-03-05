package ru.cryhards.brootkiddie.items.effects

import ru.cryhards.brootkiddie.Environment
import ru.cryhards.brootkiddie.items.Item
import ru.cryhards.brootkiddie.items.Malware

/**
 * Disguise Effect
 * ---------------
 * Decreases malware total suspiciousness
 */
class DisguiseEffect(var suspiciousness: Float = 0.5f) : Item.Effect(
        "Disguise Effect",
        "Decreases malware total suspiciousness") {

    override fun affect(target: Any?, vararg dependencies: Any?): Item.Effect {
        // TODO: make dependencies[0] (script level) affect the affection)))
        (target as Malware.Stats).suspiciousness -= Converter.pnsqrt(Environment.player.level * suspiciousness)
        return super.affect(target)
    }

    override fun toString(): String {
        return "Suspiciousness: $suspiciousness"
    }
}