package ru.cryhards.brootkiddie.logic

/**
 * Created by Dima on 24.12.2017.
 */
open class Effect(val malware: Malware, val power : Float) {
    open fun action() {}
}