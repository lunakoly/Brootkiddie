package ru.cryhards.brootkiddie.ui

import com.badlogic.gdx.scenes.scene2d.ui.Table
import ru.cryhards.brootkiddie.events.NewsEvent

/**
 * Created by user on 2/26/18.
 */
class NewsDisplay(val news: NewsEvent) : Table(){
    private val infoLabel : ShaderableLabel

    init {
        infoLabel = UI.StaticLabel(news.description)

    }
}