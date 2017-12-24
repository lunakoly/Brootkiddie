package ru.cryhards.brootkiddie

import android.content.Context
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.item_codeblock.view.*
import ru.cryhards.brootkiddie.logic.CodeBlock

/**
 * Created by Dima on 24.12.2017.
 */
class CodeBlockLayout(context : Context, val codeBlock: CodeBlock) : RelativeLayout(context){
    init {
        findViewById<TextView>(R.id.textView).setText("" + codeBlock.type)
        findViewById<TextView>(R.id.textView2).setText("size : " + codeBlock.size + " | weight : " + codeBlock.weight)
        findViewById<TextView>(R.id.textView3).setText("power : " + codeBlock.power)
    }
}