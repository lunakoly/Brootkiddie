package ru.cryhards.brootkiddie.items.effects

import ru.cryhards.brootkiddie.items.Item
import ru.cryhards.brootkiddie.items.Malware

/**
 * Spreading Effect
 * ----------------
 * Increases malware spreading speed
 */
class SpreadingEffect(var infectiousness: Float = 0.5f,
                      var spreadingSpeed: Float = 0.27f,
                      var suspiciousness: Float = 0.7f) : Item.Effect(
        "Spreading Effect",
        "Increases malware spreading speed") {

    override fun affect(target: Any?, vararg dependencies: Any?): Item.Effect {
        // TODO: make dependencies[0] (script level) affect the affection)))
        (target as Malware.Stats).infectiousness += Converter.pnsqrt(infectiousness) * if (dependencies.isNotEmpty()) Converter.pnsqrt((dependencies[0] as Int).toFloat()) else 1f
        (target as Malware.Stats).spreadingSpeed += Converter.pnsqrt(spreadingSpeed) * if (dependencies.isNotEmpty()) Converter.pnsqrt((dependencies[0] as Int).toFloat()) else 1f
        (target as Malware.Stats).suspiciousness += Converter.pnsqrt(suspiciousness) * if (dependencies.isNotEmpty()) Converter.pnsqrt((dependencies[0] as Int).toFloat()) else 1f
        return super.affect(target)
    }

    override fun toString(): String {
        return "Infectiousness: $infectiousness" +
                "Spreading Speed: $spreadingSpeed" +
                "Suspiciousness: $suspiciousness"
    }
}