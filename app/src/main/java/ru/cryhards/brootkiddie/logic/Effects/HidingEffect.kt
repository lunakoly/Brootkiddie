package ru.cryhards.brootkiddie.logic.Effects

import ru.cryhards.brootkiddie.logic.Effect
import ru.cryhards.brootkiddie.logic.Malware

/**
 * Created by Dima on 24.12.2017.
 */
class HidingEffect(malware: Malware, power : Float) : Effect(malware, power){
    override fun action() {
        malware.irritation/=power
    }
}