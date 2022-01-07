package com.mateuszcholyn.wallet

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.mateuszcholyn.wallet.scaffold.MainScreen


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                ProvideWindowInsets {
                    val systemUiController = rememberSystemUiController()
                    val darkIcons = MaterialTheme.colors.isLight
                    SideEffect {
                        systemUiController.setSystemBarsColor(Color.Transparent, darkIcons = darkIcons)
                    }
                    MainScreen()
                }
            }

        }
    }
}


//class MainActivity : AppCompatActivity(), DIAware, NavigationView.OnNavigationItemSelectedListener {
//
//    lateinit var drawer: DrawerLayout
//
//    override val di by closestDI()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        toggle()
//        switchToSummaryFragment()
//        navigationState(savedInstanceState)
//    }
//
//    private fun toggle() {
//        val toolbar = findViewById<Toolbar>(R.id.toolbar)
//        setSupportActionBar(toolbar)
//
//        drawer = findViewById(R.id.drawer_layout)
//
//        val toggle =
//                ActionBarDrawerToggle(
//                        this, drawer, toolbar,
//                        R.string.navigation_drawer_open, R.string.navigation_drawer_close
//                )
//
//        drawer.addDrawerListener(toggle)
//
//        toggle.syncState()
//    }
//
//    private fun navigationState(savedInstanceState: Bundle?) {
//        val navigationView = findViewById<NavigationView>(R.id.nav_view)
//        navigationView.setNavigationItemSelectedListener(this)
//
//        if (savedInstanceState == null) {
//            navigationView.setCheckedItem(R.id.nav_message)
//        }
//    }
//
//    override fun onNavigationItemSelected(item: MenuItem): Boolean {
//        handleNavigation(item)
//
//        drawer.closeDrawer(GravityCompat.START)
//        return true
//    }
//
//    override fun onBackPressed() {
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START)
//        } else {
//            super.onBackPressed()
//        }
//    }
//}
