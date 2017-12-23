package ru.cryhards.brootkiddie

import android.app.Fragment
import android.app.FragmentManager
import android.app.FragmentTransaction
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View

import kotlinx.android.synthetic.main.activity_browser.*
import ru.cryhards.brootkiddie.engine.android.templates.FullScreenActivity

class BrowserActivity : FullScreenActivity(), News1_WebPageFragment.OnFragmentInteractionListener, Forum1_WebPageFragment.OnFragmentInteractionListener{
    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browser)
        val fragment = News1_WebPageFragment()
        fragmentManager.beginTransaction().add(R.id.fragment_frame, fragment).commit()
    }

    fun closeActivity(v : View) {
        finish()
    }

    fun openNews1Page(v : View) {
        val transaction = fragmentManager.beginTransaction()
        val fragment = News1_WebPageFragment()
        transaction.replace(R.id.fragment_frame, fragment)
        transaction.commit()
    }

    fun openForum1Page(v : View) {
        val transaction = fragmentManager.beginTransaction()
        val fragment = Forum1_WebPageFragment()
        transaction.replace(R.id.fragment_frame, fragment)
        transaction.commit()
    }

}
