package ru.cryhards.brootkiddie.events

import com.badlogic.gdx.scenes.scene2d.utils.Drawable

/**
 * Created by Dima on 11.02.2018.
 */
abstract class GameEvent(val name : String, val description : String, val pathToImage : String = "") {
    abstract val id: Int
    abstract fun act()
}