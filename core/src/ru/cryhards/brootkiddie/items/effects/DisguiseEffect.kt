package ru.cryhards.brootkiddie.items.effects

import ru.cryhards.brootkiddie.Player
import ru.cryhards.brootkiddie.items.Item
import ru.cryhards.brootkiddie.items.Malware
import ru.cryhards.brootkiddie.items.effects.Converter

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