package ru.cryhards.brootkiddie.logic.codeblocks

import ru.cryhards.brootkiddie.logic.CodeBlock
import ru.cryhards.brootkiddie.logic.CodeBlockTypes
import ru.cryhards.brootkiddie.logic.Player
import ru.cryhards.brootkiddie.logic.Malware
/**
 * Created by Dima on 24.12.2017.
 */
class SpreadBlock(size : Int, weight : Float, power : Float, player : Player, malware: Malware) : CodeBlock(CodeBlockTypes.Spread, size, weight, power, player, malware){
    override fun action(count: Int) {
        malware.count+=(count*power).toInt()
    }
}