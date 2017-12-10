package ru.cryhards.brootkiddie

import android.os.Bundle
import android.view.View
import ru.cryhards.brootkiddie.engine.android.EngineActivity
import android.widget.ArrayAdapter
import android.support.v4.widget.DrawerLayout
import android.R.array
import android.animation.ObjectAnimator
import android.util.Log
import android.widget.ListView
import kotlinx.android.synthetic.main.layout_main.*


class MainActivity : EngineActivity() {

    var menuVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_main)

        initSurface(findViewById(R.id.main_surface))
//        registry.setTask(MyTask1())
        registry.setTask(MyTask2())
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

    }

    fun launchBrowserActivity(v : View) {
        Log.i("Click", "Browser")
    }

    fun launchTingActivity(v : View) {
        Log.i("Click", "Ting")

    }
    fun launchCodeActivity(v : View) {
        Log.i("Click", "Code")

    }

    fun launchDarkNetActivity(v : View) {
        Log.i("Click", "DarkNet")

    }

    fun toggleMenu(v : View){
        if (!menuVisible) {
            navigation_menu.visibility = View.VISIBLE
            navigation_menu.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
            val w = navigation_menu.measuredWidth
            Log.d("animation", "" + w)
            val animation = ObjectAnimator.ofFloat(navigation_menu, "translationX", -w.toFloat(), 0f)
            animation.duration = 250
            animation.start()
            menuVisible = true
        }

        else {
            val w = navigation_menu.width
            Log.d("animation", "" + w)
            val animation = ObjectAnimator.ofFloat(navigation_menu, "translationX", -w.toFloat())
            animation.duration = 250
            animation.start()
            menuVisible = false
        }
    }
}
