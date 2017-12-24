package ru.cryhards.brootkiddie.logic

/**
 * Created by Dima on 24.12.2017.
 */
open class CodeBlock (val type : CodeBlockTypes, val size : Int, val weight : Float, val power : Float, val player : Player, val malware: Malware) {

    open fun action (count : Int) {
    }
}