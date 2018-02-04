package ru.cryhards.brootkiddie.cleanup.items.effects

import ru.cryhards.brootkiddie.cleanup.items.Item
import ru.cryhards.brootkiddie.cleanup.items.Malware

/**
 * Spreading Effect
 * ----------------
 * Increases malware spreading speed
 */
class SpreadingEffect(var infectiousness: Float = 0.5f,
                      var spreadingSpeed: Float = 0.5f,
                      var suspiciousness: Float = 0.5f) : Item.Effect(
        "Spreading Effect",
        "Increases malware spreading speed") {

    override fun affect(malware: Malware): Item.Effect {
        malware.stats.infectiousness += Converter.pnsqrt(infectiousness)
        malware.stats.spreadingSpeed += Converter.pnsqrt(spreadingSpeed)
        malware.stats.suspiciousness += Converter.pnsqrt(suspiciousness)
        return super.affect(malware)
    }

    override fun toString(): String {
        return "Infectiousness: $infectiousness" +
                "Spreading Speed: $spreadingSpeed" +
                "Suspiciousness: $suspiciousness"
    }
}