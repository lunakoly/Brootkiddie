package ru.cryhards.brootkiddie.events

/**
 * Created by user on 2/19/18.
 */
abstract  class NewsEvent(name : String, description : String) :GameEvent(name, description) {
    abstract val pathToImage:String
}