package ru.cryhards.brootkiddie.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import ru.cryhards.brootkiddie.Core
import ru.cryhards.brootkiddie.Player
import ru.cryhards.brootkiddie.events.dialogs.Dialog
import ru.cryhards.brootkiddie.ui.ImageActor
import ru.cryhards.brootkiddie.ui.UI

/**
 * Screen that displays list of available dialogs
 */

class DialogsScreen : ScreenAdapter() {

    private val background = Texture("img/bg/logo.png")
    private val backButton = UI.GlitchImageButton("img/ui/back.png")
    /**val dialogWidget : DialogDisplay by lazy {
        val w = DialogDisplay(Player.dialogs[0])
        uiGroup.addActor(w)
        uiGroup.pack()
        w
    }**/

    private val uiGroup = HorizontalGroup()
    private val sendersGroup = VerticalGroup()
    private val senderViewer = VerticalGroup()

    private val dialogsGroup = HorizontalGroup()

    private val emptyDialogsLabel = UI.StaticLabel("Looks like you have no dialogs at the moment.\n" +
            "Come back later")

    private val stage = Stage()


    private val dialogsBySender = mutableMapOf<String, MutableList<Dialog>>()

    init {
        uiGroup.pad(10f)
        uiGroup.space(10f)
        uiGroup.setFillParent(true)
        uiGroup.align(Align.topLeft)
        stage.addActor(uiGroup)

        sendersGroup.width = 100f

        senderViewer.addActor(dialogsGroup)

        val pane = ScrollPane(sendersGroup)
        pane.width = sendersGroup.width
        pane.height = Gdx.graphics.height * 1f

        //senderViewer.addActor(dialogWidget)

        uiGroup.addActor(pane)
        uiGroup.addActor(senderViewer)

        /**if (Player.dialogs.size > 0) {
            dialogWidget.refreshInfo()
        }**/


        refreshDialogs()

        backButton.setPosition(Gdx.graphics.width.toFloat() - 50f, 50f, Align.bottomRight)
        stage.addActor(backButton)

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

    override fun dispose() {
        background.dispose()
    }

    /**
     * Refresh list of dialogs
     */

    private fun refreshDialogs(){


        dialogsBySender.clear()
        sendersGroup.clear()

        Gdx.app.log("DD", Player.senders.size.toString())

        if (!Player.senders.isEmpty()) {

            emptyDialogsLabel.isVisible = false

            for (d in Player.dialogs) {
                if (!dialogsBySender.contains(d.sender)) {
                    dialogsBySender[d.sender] = mutableListOf(d)
                } else {
                    dialogsBySender[d.sender]!!.add(d)
                }
            }

            for (s in dialogsBySender.keys) {
                val sender = Player.senders[s]
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

                sendersGroup.addActor(group)
            }

            sendersGroup.pack()



            for (dialog in Player.dialogs) {
                val button = UI.StaticTextButton(dialog.id)
                Gdx.app.log("Dialog", dialog.id)
                /**button.addListener(object : ClickListener(){
                override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                dialogWidget.dialog = dialog
                dialogWidget.refreshInfo()
                }
                })**/
                dialogsGroup.addActor(button)
            }

            uiGroup.pack()

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
        dialogs!!
                .map { UI.StaticTextButton(it.id) }
                .forEach { dialogsGroup.addActor(it) }

        dialogsGroup.pack()
    }

    override fun show() {
        Gdx.input.inputProcessor = stage
        refreshDialogs()
        super.show()
    }
}