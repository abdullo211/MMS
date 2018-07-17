package com.wd.mms.ui.main

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.widget.SlidingPaneLayout
import android.view.MenuItem
import android.view.View
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.wd.mms.R
import com.wd.mms.presentation.main.MainPresenter
import com.wd.mms.presentation.main.MainView
import com.wd.mms.toothpick.DI
import com.wd.mms.ui.auth.LoginActivity
import com.wd.mms.ui.messages.list.MessagesListFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.menu_main.*
import toothpick.Toothpick

class MainActivity : MvpAppCompatActivity(), MainView, SlidingPaneLayout.PanelSlideListener,
        NavigationView.OnNavigationItemSelectedListener {


    companion object {
        const val LOGIN_RESULT = 1
    }

    @InjectPresenter
    lateinit var presenter: MainPresenter

    @ProvidePresenter
    fun provide(): MainPresenter {
        return Toothpick.openScopes(DI.SERVER_SCOPE)
                .getInstance(MainPresenter::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Toothpick.inject(this, Toothpick.openScopes(DI.APP_SCOPE, DI.SERVER_SCOPE))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        panelLayout.sliderFadeColor = Color.TRANSPARENT
        panelLayout.setPanelSlideListener(this)
        nav_view.setNavigationItemSelectedListener(this)

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
                    presenter.onUserClicked()
                true
            }
        }
    }

    override fun onBackPressed() {
        if (panelLayout.isOpen)
            panelLayout.closePane()
        else super.onBackPressed()
    }

    override fun showMessagesPage() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, MessagesListFragment())
                .commit()
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        panelLayout.closePane()
        when (menuItem.itemId) {
            R.id.nav_log_out -> presenter.onLogOutMenuClicked()

        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        presenter.onActivityResult()
    }

    override fun showLoginPage() {
        startActivityForResult(Intent(this, LoginActivity::class.java), LOGIN_RESULT)
    }

    override fun showLogOutDialog() {
        AlertDialog.Builder(this)
                .setTitle(R.string.title_log_out)
                .setMessage(R.string.message_log_out)
                .setPositiveButton(R.string.title_yes) { _, _ -> presenter.logOutClicked() }
                .setNegativeButton(R.string.title_cancel) { _, _ -> }
                .create()
                .show()
    }

    override fun close() {
        finish()
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
