package ru.cryhards.brootkiddie

import android.os.Bundle
import android.view.View
import ru.cryhards.brootkiddie.engine.android.EngineActivity
import android.widget.ArrayAdapter
import android.support.v4.widget.DrawerLayout
import android.R.array
import android.util.Log
import android.widget.ListView
import kotlinx.android.synthetic.main.layout_main.*


class MainActivity : EngineActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_main)

        initSurface(findViewById(R.id.main_surface))
//        registry.setTask(MyTask1())
        registry.setTask(MyTask2())
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

    fun showMenu(v : View){
        navigation_menu.visibility = View.VISIBLE
    }

    fun hideMenu(v: View) {
        navigation_menu.visibility = View.GONE
    }
}
