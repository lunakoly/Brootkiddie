package ru.cryhards.brootkiddie.ui

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import ru.cryhards.brootkiddie.events.dialogs.Dialog

/**
 * Widget that displays dialog
 */
class DialogDisplay(var dialog: Dialog) : VerticalGroup(){

    private val senderLabel : ShaderableLabel
    private val infoLabel : ShaderableLabel
    private val transitionsTable : Table


    init {
        val currentState = dialog.getCurrentState()
        columnLeft()
        pad(5f)
        space(10f)

        senderLabel = UI.StaticLabel(dialog.sender)
        addActor(senderLabel)

        val info = currentState!!.getInfo()
        infoLabel = UI.StaticLabel(info)
        infoLabel.width = 100f
        addActor(infoLabel)

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
        addActor(transitionsTable)
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
        infoLabel.width = 100f

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