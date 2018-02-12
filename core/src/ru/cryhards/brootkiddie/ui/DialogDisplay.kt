package ru.cryhards.brootkiddie.ui

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import ru.cryhards.brootkiddie.events.dialogs.Dialog

/**
 * Widget that displays dialog
 */
class DialogDisplay(var dialog: Dialog) : Table(){

    private val senderLabel : ShaderableLabel
    private val infoLabel : ShaderableLabel
    private val transitionsTable : Table


    init {
        val currentState = dialog.getCurrentState()

        senderLabel = UI.StaticLabel(dialog.sender)
        add(senderLabel).align(Align.topLeft)
        row()

        val info = currentState!!.getInfo()
        infoLabel = UI.StaticLabel(info)
        infoLabel.setWrap(true)
        val pane = ScrollPane(infoLabel)
        add(pane).width(Gdx.graphics.width/2f).align(Align.topLeft).height(Gdx.graphics.height/2f)
        row()

        transitionsTable = Table()
        var counter = 0


        for (transition in dialog.getTransitions(currentState.id))
        {
            val transLabel = UI.StaticTextButton(transition.getInfo())
            transLabel.addListener(object : ClickListener(){
                override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                    dialog.go(transition)
                    refreshInfo()
                }
            })
            transitionsTable.add(transLabel).space(10f).uniform()


            counter++

            if (counter % 2 == 0) {
                transitionsTable.row()
            }
        }

        transitionsTable.pack()
        add(transitionsTable).align(Align.topLeft)
        row()
        pack()
    }

    /**
    Refreshes info when state changes
     */

    fun refreshInfo(){
        val currentState = dialog.getCurrentState()

        senderLabel.setText(dialog.sender)

        val info = currentState!!.getInfo()
        infoLabel.setText(info)

        transitionsTable.clear()
        var counter = 0

        for (transition in dialog.getTransitions(currentState.id))
        {
            val transLabel = UI.StaticTextButton(transition.getInfo())
            transLabel.addListener(object : ClickListener(){
                override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                    dialog.go(transition)
                    refreshInfo()
                }
            })
            transitionsTable.add(transLabel)


            counter++

            if (counter % 2 == 0) {
                transitionsTable.row()
            }
        }

        transitionsTable.pack()
        pack()
    }
}