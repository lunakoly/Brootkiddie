package ru.cryhards.brootkiddie.ui

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.TextArea
import kotlin.math.truncate

/**
 * TextArea that increasess its height if needed
 */
class ExtendingTextArea(text: String, style: TextFieldStyle) : TextArea(text, style) {

    init {
        val pix = Pixmap(50, 50, Pixmap.Format.RGB888)
        pix.setColor(Color.BLACK)
        pix.fill()

        style.background = Image(Texture(pix)).drawable

        // ! IMPORTANT
        style.fontColor = Color.WHITE
        isDisabled = true
    }


    override fun setText(str: String?) {
        super.setText(str)
        height = calculateSuitableHeight()
    }


    /**
     * Sets width and updates height
     * for it to fit text according to wrapping
     */
    fun squeeze(width: Float) {
        this.width = width
        height = calculateSuitableHeight()
    }


    /**
     * UNSTABLE
     * ========
     * Returns the height that should the area be of
     * in case overflow is allowed
     */
    private fun calculateSuitableHeight(): Float {
        // if width has not been initialized yet
        if (width == 0f) return 0f

        val words = text.replace("-".toRegex(), "- ").split("[\\s+\n\t-]".toRegex())
        val lineWidth = truncate(width / 22).toInt()

        // TODO: i dont know why 22!

//        println("area width: " + width)
//        println("line height: " + style.font.lineHeight)
//        println("space width: " + style.font.spaceWidth)
//
//        println("words: " + words.size)
//        println("line capacity: " + lineWidth)


        var spaceLeft = lineWidth
        var linesCount = 1

        for (i in 0 until words.size) {
            // goes to new line
            if (words[i].length + 1 > spaceLeft) {
                linesCount += 1 + words[i].length / lineWidth
//                println("----- newline at " + words[i])
                spaceLeft = lineWidth - words[i].length % lineWidth
            } else {
                spaceLeft -= words[i].length + 1
            }
        }

//        println("count: " + linesCount)

        return linesCount * style.font.lineHeight
    }
}
