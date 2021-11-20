package com.mateuszcholyn.wallet

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener
import com.mateuszcholyn.wallet.view.showShortText
import org.kodein.di.DIAware
import org.kodein.di.android.closestDI

class SimpleNavigation : AppCompatActivity(), DIAware, OnNavigationItemSelectedListener {

    lateinit var drawer: DrawerLayout

    override val di by closestDI()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_navigation)
        toggle()
        switchToMessageFragment()
        navigationState(savedInstanceState)
    }

    private fun toggle() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawer = findViewById(R.id.drawer_layout)

        val toggle =
            ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close
            )

        drawer.addDrawerListener(toggle)

        toggle.syncState()
    }

    private fun navigationState(savedInstanceState: Bundle?) {
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        if (savedInstanceState == null) {
            navigationView.setCheckedItem(R.id.nav_message)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_message -> switchToMessageFragment()
            R.id.nav_chat -> switchToChatFragment()
            R.id.nav_share -> showShortText("Share")
            R.id.nav_send -> showShortText("Send")
        }

        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}