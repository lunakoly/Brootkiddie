package ru.cryhards.brootkiddie.logic.codeblocks

import ru.cryhards.brootkiddie.logic.CodeBlock
import ru.cryhards.brootkiddie.logic.CodeBlockTypes
import ru.cryhards.brootkiddie.logic.Malware
import ru.cryhards.brootkiddie.logic.Player


class MiningBlock (size : Int, weight : Float, power : Float, player : Player, malware: Malware) : CodeBlock(CodeBlockTypes.Mining, size, weight, power, player, malware){
    override fun action(count : Int) {
        player.balance+=(count*power).toInt()
    }
}