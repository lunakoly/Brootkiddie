package ru.cryhards.brootkiddie

import android.os.Bundle
import android.view.View
import ru.cryhards.brootkiddie.engine.android.EngineActivity
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Handler
import android.provider.Browser
import android.util.Log
import android.view.MotionEvent
import kotlinx.android.synthetic.main.activity_main.*

import ru.cryhards.brootkiddie.engine.util.components.prop.Vec3
import ru.cryhards.brootkiddie.logic.Malware
import ru.cryhards.brootkiddie.logic.MalwareTypes
import ru.cryhards.brootkiddie.logic.Player
import ru.cryhards.brootkiddie.logic.codeblocks.HidingBlock
import ru.cryhards.brootkiddie.logic.codeblocks.MiningBlock
import ru.cryhards.brootkiddie.logic.codeblocks.SpreadBlock
import java.lang.Math.random

class MainActivity : EngineActivity() {

    var menuVisible = false
    val player = Player()
    val handler = Handler()
    var day = 0

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
        val m1 = Malware(MalwareTypes.Virus, 3, "Petya", player, 0.3f)
        m1.codeblocks.add(HidingBlock(1, 0.2f, 1.1f, player, m1))
        m1.codeblocks.add(MiningBlock(1, 0.8f, 0.001f, player, m1))
        m1.codeblocks.add(SpreadBlock(1, 0.5f, 0.001f, player, m1))
        val m2 = Malware(MalwareTypes.Rootkit, 6, "Kolya", player, 0.9f)
        m2.codeblocks.add(HidingBlock(1, 0.2f, 1.05f, player, m1))
        m2.codeblocks.add(MiningBlock(2, 1.2f, 0.003f, player, m1))
        m2.codeblocks.add(SpreadBlock(3, 0.5f, 0.001f, player, m1))
        val m3 = Malware(MalwareTypes.Worm, 2, "Vasya", player, 0.4f)
        m3.codeblocks.add(MiningBlock(1, 0.5f, 0.007f, player, m1))
        m3.codeblocks.add(SpreadBlock(1, 0.5f, 0.01f, player, m1))
        m1.count = 1000
        m2.count = 1000
        m3.count = 1000
        player.malwares.add(m1)
        player.malwares.add(m2)
        player.malwares.add(m3)

        val runnable = object : Runnable  {

            override fun run() {

                var count = 0
                day++
                for (m in player.malwares) {m.action(); count += m.count}
                malwareCountView.text = "" + count
                moneyView.text = "" + player.balance
                timeView.text = "Day " + day

                handler.postDelayed(this, 40)
            }
        }

        handler.post(runnable)
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
