package ru.cryhards.brootkiddie.logic.codeblocks

import ru.cryhards.brootkiddie.logic.CodeBlock
import ru.cryhards.brootkiddie.logic.CodeBlockTypes
import ru.cryhards.brootkiddie.logic.Effects.HidingEffect
import ru.cryhards.brootkiddie.logic.Malware
import ru.cryhards.brootkiddie.logic.Player

/**
 * Created by Dima on 24.12.2017.
 */
class HidingBlock (size : Int, weight : Float, power : Float, player : Player, malware: Malware) : CodeBlock(CodeBlockTypes.Hiding, size, weight, power, player, malware){
    override fun action(count: Int) {
        malware.effects.add(HidingEffect(malware, power))
    }
}