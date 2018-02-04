package ru.cryhards.brootkiddie.ui

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup
import ru.cryhards.brootkiddie.Event
import ru.cryhards.brootkiddie.utils.AssetManager

/**
 * Created by Dima on 04.02.2018.
 */
class EventWidget() : VerticalGroup(){
    lateinit var event : Event

    companion object {
        fun createWidget(event: Event) : EventWidget
        {
            val widget = EventWidget()
            AssetManager.loadFont("fonts/roboto.ttf", "roboto")
            val fontParameter = FreeTypeFontGenerator.FreeTypeFontParameter()
            fontParameter.size = 70
            var font = AssetManager.makeFont("roboto", fontParameter)
            var style = Label.LabelStyle(font, Color(224 / 255f, 35f / 255f, 45f / 255f, 1f))

            val nameLabel = Label(event.name, style) // update UI will be called later to set up right label texts
            widget.addActor(nameLabel)

            fontParameter.size = 20
            font = AssetManager.makeFont("roboto", fontParameter)
            style = Label.LabelStyle(font, Color(224 / 255f, 35f / 255f, 45f / 255f, 1f))

            val descriptionLabel = Label(event.description, style) // update UI will be called later to set up right label texts

            widget.addActor(descriptionLabel)

            widget.pack()


            return widget
        }
    }
}