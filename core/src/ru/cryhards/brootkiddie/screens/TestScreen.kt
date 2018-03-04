package ru.cryhards.brootkiddie.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop
import com.badlogic.gdx.utils.Align
import ru.cryhards.brootkiddie.ui.ImageActor
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload
import ru.cryhards.brootkiddie.ui.UI


/**
 * Created by Dima on 21.02.2018.
 */
class TestScreen : ScreenAdapter(){

    private val stage = Stage()

    private val table = Table()
    private val image = Draggable("img/ui/bench.png")
    private val pane = ScrollPane(table)
    private val dragAndDrop = DragAndDrop()


    class Draggable(path : String) : ImageActor(path){
        var isDragged = false
        init {
            addListener(object : ActorGestureListener(){
                override fun longPress(actor: Actor?, x: Float, y: Float): Boolean {
                    isDragged = true
                    Gdx.app.log("DD", "KEEEK")
                    return super.longPress(actor, x, y)
                }
            })
            addListener(object : InputListener(){
                override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                    isDragged = false
                    super.touchUp(event, x, y, pointer, button)
                }
            })
        }


    }

    init {

        dragAndDrop.addSource(object : DragAndDrop.Source(image){
            override fun dragStart(event: InputEvent?, x: Float, y: Float, pointer: Int): Payload? {

                if (!image.isDragged) return null

                val payload = Payload()
                payload.`object` = "AA"

                payload.dragActor = UI.StaticLabel("DA")

                val validLabel = UI.StaticLabel("VDA")
                validLabel.setColor(0f, 1f, 0f, 1f)
                payload.validDragActor = validLabel

                val invalidLabel = UI.StaticLabel("IVDA")
                invalidLabel.setColor(1f, 0f, 0f, 1f)
                payload.invalidDragActor = invalidLabel

                return payload
            }

            override fun dragStop(event: InputEvent?, x: Float, y: Float, pointer: Int, payload: Payload?, target: DragAndDrop.Target?) {
                image.isDragged = false
                super.dragStop(event, x, y, pointer, payload, target)
            }
        })


        for (i in 1..5) {
            for (j in 1..5) {
                val actor = Draggable("img/ui/inventory_block.png")
                table.add(actor).size(100f)

                dragAndDrop.addSource(object : DragAndDrop.Source(actor){
                    override fun dragStart(event: InputEvent?, x: Float, y: Float, pointer: Int): Payload? {

                        if (!actor.isDragged) return null

                        val payload = Payload()
                        payload.`object` = "AA"

                        payload.dragActor = UI.StaticLabel("DA")

                        val validLabel = UI.StaticLabel("VDA")
                        validLabel.setColor(0f, 1f, 0f, 1f)
                        payload.validDragActor = validLabel

                        val invalidLabel = UI.StaticLabel("IVDA")
                        invalidLabel.setColor(1f, 0f, 0f, 1f)
                        payload.invalidDragActor = invalidLabel

                        return payload
                    }

                    override fun dragStop(event: InputEvent?, x: Float, y: Float, pointer: Int, payload: Payload?, target: DragAndDrop.Target?) {
                        actor.isDragged = false
                        super.dragStop(event, x, y, pointer, payload, target)
                    }
                })

            }
            table.row()
        }

        table.pack()

        image.width = 100f
        image.height = 100f
        stage.addActor(image)



        dragAndDrop.addTarget(object : DragAndDrop.Target(table){
            override fun drag(source: DragAndDrop.Source, payload: Payload, x: Float, y: Float, pointer: Int): Boolean {
                actor.color = Color.GREEN
                return true
            }

            override fun reset(source: DragAndDrop.Source, payload: Payload) {
                actor.color = Color.WHITE
            }

            override fun drop(source: DragAndDrop.Source, payload: Payload, x: Float, y: Float, pointer: Int) {
                println("Accepted: " + payload.`object` + " " + x + ", " + y)
            }

        })

        //dragAndDrop.setCancelTouchFocus(false)

        pane.height = 300f
        pane.setPosition(Gdx.graphics.width/2f, Gdx.graphics.height/2f, Align.center)
        pane.width = table.width


        stage.addActor(pane)
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        stage.act(delta)
        stage.draw()

        super.render(delta)
    }

    override fun show() {
        Gdx.input.inputProcessor = stage
        super.show()
    }
}