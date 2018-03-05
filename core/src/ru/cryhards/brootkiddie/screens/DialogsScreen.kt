package ru.cryhards.brootkiddie.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import ru.cryhards.brootkiddie.Core
import ru.cryhards.brootkiddie.Environment
import ru.cryhards.brootkiddie.events.dialogs.Dialog
import ru.cryhards.brootkiddie.ui.DialogDisplay
import ru.cryhards.brootkiddie.ui.ImageActor
import ru.cryhards.brootkiddie.ui.UI

/**
 * Screen that displays list of available dialogs
 */

class DialogsScreen : ScreenAdapter() {

    private val backButton = UI.GlitchImageButton("img/ui/back.png")
    private val dialogDisplay = DialogDisplay()

    private val uiGroup = Table()
    private val sendersGroup = Table()
    private val senderViewer = Table()

    private val dialogsGroup = Table()

    private val emptyDialogsLabel = UI.StaticLabel("Looks like you have no dialogs at the moment.\n" +
            "Come back later")

    private val stage = Stage()


    private val dialogsBySender = mutableMapOf<String, MutableList<Dialog>>()

    init {
        //uiGroup.pad(10f)
        //uiGroup.space(10f)
        uiGroup.setFillParent(true)
        uiGroup.align(Align.topLeft)
        stage.addActor(uiGroup)

        sendersGroup.width = 100f
        sendersGroup.align(Align.topLeft)


        senderViewer.align(Align.topLeft)
        //dialogsGroup.debug = true

        dialogsGroup.align(Align.topLeft)


        senderViewer.add(dialogsGroup).align(Align.topLeft).row()

        val pane = ScrollPane(sendersGroup)
        pane.width = sendersGroup.width
        pane.height = Gdx.graphics.height * 1f


        //senderViewer.grow()
        senderViewer.add(dialogDisplay).grow()

        val leftTable = Table()

        Gdx.app.log("DD", "${backButton.prefWidth}")

        leftTable.add(pane).align(Align.topLeft).growY().row()
        leftTable.add(backButton).align(Align.topLeft).pad(5f, 50f, 50f, 0f).prefSize(backButton.width, backButton.height)

        uiGroup.add(leftTable).align(Align.topLeft).growY()
        uiGroup.add(senderViewer).grow()
        //uiGroup.debug = true




        refreshDialogs()

        backButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                Core.instance.toGlobalMap()
            }
        })


    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(45/255f, 45/255f, 45/255f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        stage.act()
        stage.draw()
    }

    /**
     * Refresh list of dialogs
     */

    private fun refreshDialogs(){


        dialogsBySender.clear()
        sendersGroup.clear()

        if (!Environment.player.senders.isEmpty()) {

            emptyDialogsLabel.remove()

            for (d in Environment.player.dialogs) {
                if (!dialogsBySender.contains(d.sender)) {
                    dialogsBySender[d.sender] = mutableListOf(d)
                } else {
                    dialogsBySender[d.sender]!!.add(d)
                }
            }

            for (s in dialogsBySender.keys) {
                val sender = Environment.player.senders[s]
                val image = ImageActor(sender!!)
                image.setSize(50f, 50f)

                val nameLabel = UI.StaticLabel(s)

                val group = HorizontalGroup()
                group.addActor(image)
                group.addActor(nameLabel)

                group.rowAlign(Align.center)

                group.pack()

                group.addListener(object : InputListener() {
                    override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                        showSender(sender)
                    }
                })

                sendersGroup.add(group).align(Align.topLeft).row()
            }

            sendersGroup.pack()

            //uiGroup.pack()

            showSender(dialogsBySender.keys.elementAt(0))
        } else {
            emptyDialogsLabel.isVisible = true
            emptyDialogsLabel.setPosition(Gdx.graphics.width / 2f, Gdx.graphics.height / 2f, Align.center)
            stage.addActor(emptyDialogsLabel)
        }
    }

    fun showSender(sender: String) {
        dialogsGroup.clear()
        val dialogs = dialogsBySender[sender]
        for (d in dialogs!!) {
            val label = UI.StaticTextButton(d.topic)
            label.addListener(object : ClickListener(){

                override fun clicked(event: InputEvent?, x: Float, y: Float) {
                    dialogDisplay.setDialog(d)
                    Gdx.app.log("Dialog", "Up")
                }
            })
            dialogsGroup.add(label).align(Align.topLeft).pad(0f,0f,0f,10f)
        }

        dialogDisplay.setDialog(dialogs[0])

        //dialogsGroup.pack()
    }

    override fun show() {
        Gdx.input.inputProcessor = stage
        refreshDialogs()
        super.show()
    }
}