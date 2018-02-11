package ru.cryhards.brootkiddie.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import ru.cryhards.brootkiddie.Core
import ru.cryhards.brootkiddie.Player
import ru.cryhards.brootkiddie.events.dialogs.Dialog
import ru.cryhards.brootkiddie.ui.Cropper
import ru.cryhards.brootkiddie.ui.DialogDisplay
import ru.cryhards.brootkiddie.ui.UI

/**
 * Screen that displays list of available dialogs
 */

class DialogsScreen : ScreenAdapter() {

    private val background = Texture("img/bg/logo.png")
    private val backButton = UI.GlitchImageButton("img/ui/back.png")
    val dialogWidget : DialogDisplay by lazy {
        val w = DialogDisplay(Player.dialogs[0])
        uiGroup.addActor(w)
        uiGroup.pack()
        w
    }
    private val dialogsGroup = HorizontalGroup()
    private val uiGroup : VerticalGroup = VerticalGroup()
    private val stage = Stage()

    init {
        uiGroup.columnLeft()
        uiGroup.align(Align.topLeft)
        uiGroup.pad(10f)
        uiGroup.space(10f)
        uiGroup.setFillParent(true)
        stage.addActor(uiGroup)

        uiGroup.addActor(dialogsGroup)

        if (Player.dialogs.size > 0) {
            dialogWidget.refreshInfo()
        }


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
        dialogsGroup.clear()

        for (dialog in Player.dialogs)
        {
            val button = UI.StaticTextButton(dialog.id)
            Gdx.app.log("Dialog", dialog.id)
            button.addListener(object : ClickListener(){
                override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                    dialogWidget.dialog = dialog
                    dialogWidget.refreshInfo()
                }
            })
            dialogsGroup.addActor(button)
        }

        dialogsGroup.pack()
        uiGroup.pack()
    }

    override fun show() {
        Gdx.input.inputProcessor = stage
        refreshDialogs()
        super.show()
    }
}