package com.wd.mms.ui.main

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.widget.SlidingPaneLayout
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.wd.mms.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), SlidingPaneLayout.PanelSlideListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        panelLayout.sliderFadeColor = Color.TRANSPARENT
        panelLayout.setPanelSlideListener(this)
        fab.setOnClickListener {
            Snackbar.make(it, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        initToolbar()
    }

    private fun initToolbar() {
        toolbar.inflateMenu(R.menu.menu_user)

        toolbar.setNavigationOnClickListener {
            if (panelLayout.isOpen)
                panelLayout.closePane()
            else
                panelLayout.openPane()

            toolbar.setOnMenuItemClickListener {
                if (it.itemId== R.id.action_user)


                    return true
            }
        }
    }

    override fun onBackPressed() {
        if (panelLayout.isOpen)
            panelLayout.closePane()
        else super.onBackPressed()
    }



    override fun onPanelClosed(panel: View) {}

    override fun onPanelSlide(panel: View, slideOffset: Float) {
        mainView.scaleY = (1 - slideOffset * .07f)
        menuView.alpha = slideOffset
        menuView.translationY = slideOffset
        menuView.translationX = -(slideOffset) * 30
    }

    override fun onPanelOpened(panel: View) { }

}
