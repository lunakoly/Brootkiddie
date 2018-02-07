package ru.cryhards.brootkiddie.items.effects

import ru.cryhards.brootkiddie.items.Item
import ru.cryhards.brootkiddie.items.Malware

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

    override fun affect(target: Any?, vararg dependencies: Any?): Item.Effect {
        // TODO: make dependencies[0] (script level) affect the affection)))
        (target as Malware.Stats).infectiousness += Converter.pnsqrt(infectiousness)
        (target as Malware.Stats).spreadingSpeed += Converter.pnsqrt(spreadingSpeed)
        (target as Malware.Stats).suspiciousness += Converter.pnsqrt(suspiciousness)
        return super.affect(target)
    }

    override fun toString(): String {
        return "Infectiousness: $infectiousness" +
                "Spreading Speed: $spreadingSpeed" +
                "Suspiciousness: $suspiciousness"
    }
}