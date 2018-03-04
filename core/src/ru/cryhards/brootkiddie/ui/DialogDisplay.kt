package ru.cryhards.brootkiddie.ui

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import ru.cryhards.brootkiddie.events.dialogs.Dialog

/**
 * Widget that displays dialog
 */
class DialogDisplay : Table(){

    private val topicLabel = UI.StaticLabel("")
    private val infoLabel = UI.StaticLabel("")
    private val transitionsTable = Table()

    private lateinit var dialog : Dialog

    init {

        align(Align.topLeft)

        add(topicLabel)
        row()

        infoLabel.setWrap(true)
        infoLabel.setText("")
        infoLabel.setAlignment(Align.topLeft, Align.topLeft)

        val pane = ScrollPane(infoLabel)
        add(pane).height(Gdx.graphics.height*0.67f).growX()
        row()

        transitionsTable.pack()
        add(transitionsTable).align(Align.topLeft).growX()
        row()
    }

    /**
    Refreshes info when state changes
     */

    fun setDialog(dialog: Dialog) {
        this.dialog = dialog
        refreshInfo()
    }

    fun refreshInfo(){
        val currentState = dialog.getCurrentState()

        topicLabel.setText(dialog.topic)

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
            transitionsTable.add(transLabel).grow()


            counter++

            if (counter % 2 == 0) {
                transitionsTable.row()
            }
        }

    }
}