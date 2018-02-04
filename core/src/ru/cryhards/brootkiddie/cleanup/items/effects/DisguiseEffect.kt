package ru.cryhards.brootkiddie.cleanup.items.effects

import ru.cryhards.brootkiddie.cleanup.Player
import ru.cryhards.brootkiddie.cleanup.items.Item
import ru.cryhards.brootkiddie.cleanup.items.Malware

/**
 * Disguise Effect
 * ---------------
 * Decreases malware total suspiciousness
 */
class DisguiseEffect(var suspiciousness: Float = 0.5f) : Item.Effect(
        "Disguise Effect",
        "Decreases malware total suspiciousness") {

    override fun affect(malware: Malware): Item.Effect {
        malware.stats.suspiciousness -= Converter.pnsqrt(Player.level * suspiciousness)
        return super.affect(malware)
    }

    override fun toString(): String {
        return "Suspiciousness: $suspiciousness"
    }
}