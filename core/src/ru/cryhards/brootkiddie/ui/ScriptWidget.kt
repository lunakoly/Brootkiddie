package ru.cryhards.brootkiddie.ui

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup
import ru.cryhards.brootkiddie.malware.scripts.BaseScript
import ru.cryhards.brootkiddie.malware.scripts.Script
import ru.cryhards.brootkiddie.utils.AssetManager

/**
 * Created by Dima on 28.01.2018.
 */
class ScriptWidget() :  VerticalGroup(){

    lateinit var script : Script

    companion object {
        fun createWidget(script: Script) : ScriptWidget
        {
            val widget = ScriptWidget()
            AssetManager.loadFont("fonts/roboto.ttf", "roboto")
            val fontParameter = FreeTypeFontGenerator.FreeTypeFontParameter()

            fontParameter.size = 70
            var font = AssetManager.makeFont("roboto", fontParameter)
            var style = Label.LabelStyle(font, Color(224 / 255f, 35f / 255f, 45f / 255f, 1f))

            val nameLabel = Label(script.name, style) // update UI will be called later to set up right label texts
            nameLabel.x = 10f


            fontParameter.size = 40
            font = AssetManager.makeFont("roboto", fontParameter)
            style = Label.LabelStyle(font, Color(232 / 255f, 232 / 255f, 232 / 255f, 1f))

            val statsLabel = Label(script.getStats(), style)
            statsLabel.x = 10f

            widget.addActor(nameLabel)
            widget.addActor(statsLabel)

            widget.script = script

            return widget
        }
    }

}