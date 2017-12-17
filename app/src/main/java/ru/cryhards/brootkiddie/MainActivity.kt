package ru.cryhards.brootkiddie

import android.os.Bundle
import android.view.View
import ru.cryhards.brootkiddie.engine.android.EngineActivity
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.provider.Browser
import android.util.Log
import android.view.MotionEvent
import kotlinx.android.synthetic.main.activity_main.*

import ru.cryhards.brootkiddie.engine.util.components.prop.Vec3
import java.lang.Math.random

class MainActivity : EngineActivity() {

    var menuVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initSurface(findViewById(R.id.main_surface))
        addScene(MyScene1("intro"))
        addScene(MyScene2("test"))
        startScene("intro")

        val btn = button_darknet
        btn.setOnClickListener {
            if (registry.activeScene?.sceneName == "intro")
                registry.switchScene("test")
            else {
                registry.switchScene("intro")
                registry.activeScene?.environment?.sunlight = Vec3(random().toFloat(), random().toFloat(), random().toFloat())
            }
        }

        main_surface.setOnTouchListener{v, event ->  if (event.action == MotionEvent.ACTION_DOWN && menuVisible) toggleMenu(v); false}
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

    }

    fun launchBrowserActivity(v : View) {
        Log.i("Click", "Browser")
        startActivity(Intent(this, BrowserActivity::class.java))
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
            val a1 = ObjectAnimator.ofFloat(navigation_menu, "translationX", -w.toFloat(), 0f)
            val a2 = ObjectAnimator.ofFloat(menu_button, "translationX", -menu_button.width*1f)
            val set = AnimatorSet()
            set.playTogether(a1, a2)
            set.duration = 250
            set.start()
            menuVisible = true
        }

        else {
            val w = navigation_menu.width
            val a1 = ObjectAnimator.ofFloat(navigation_menu, "translationX", -w.toFloat())
            val a2 = ObjectAnimator.ofFloat(menu_button, "translationX", -menu_button.width*1f, 0f)
            val set = AnimatorSet()
            set.playTogether(a1, a2)
            set.duration = 250
            set.start()
            menuVisible = false
        }
    }
}
