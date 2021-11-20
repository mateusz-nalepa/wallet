package com.mateuszcholyn.wallet

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.mateuszcholyn.wallet.ui.chat.ChatFragment
import com.mateuszcholyn.wallet.ui.message.MessageFragment
import com.mateuszcholyn.wallet.view.showShortText
import org.kodein.di.DIAware
import org.kodein.di.android.closestDI

class SimpleNavigation : AppCompatActivity(), DIAware,
    NavigationView.OnNavigationItemSelectedListener {


    lateinit var drawer: DrawerLayout

    override val di by closestDI()

//    private lateinit var appBarConfiguration: AppBarConfiguration
//    private lateinit var binding: ActivitySimpleNavigationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_navigation)


        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawer = findViewById(R.id.drawer_layout)


        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)


        val toggle =
            ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close
            )

        drawer.addDrawerListener(toggle)

        toggle.syncState()

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, MessageFragment())
            .commit()

        if (savedInstanceState == null) {
            navigationView.setCheckedItem(R.id.nav_message)
        }
//
//        binding = try {
//            ActivitySimpleNavigationBinding.inflate(layoutInflater)
//        } catch (ex: Exception) {
//            ex.printStackTrace()
//            throw RuntimeException("wywalilo sie", ex)
//        }
//        setContentView(binding.root)

//        setSupportActionBar(binding.appBarSimpleNavigation.toolbar)

//        binding.appBarSimpleNavigation.fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }
//        val drawerLayout: DrawerLayout = binding.drawerLayout
//        val navView: NavigationView = binding.navView
//        val navController = findNavController(R.id.nav_host_fragment_content_simple_navigation)
//         Passing each menu ID as a set of Ids because each
//         menu should be considered as top level destinations.
//        appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.nav_home,
//                R.id.nav_gallery,
//                R.id.nav_slideshow,
//                R.id.nav_add_expense,
//            ), drawerLayout
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
////        menuInflater.inflate(R.menu.simple_navigation, menu)
//        return true
//    }

//    override fun onSupportNavigateUp(): Boolean {
////        val navController = findNavController(R.id.nav_host_fragment_content_simple_navigation)
////        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
//    }

//    fun showAddExpenseActivity(view: View) {
//        val intent = Intent(this, AddExpenseActivityBinding::class.java)
//        startActivity(intent)
//    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_message -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, MessageFragment())
                    .commit()
            }
            R.id.nav_chat -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, ChatFragment())
                    .commit()
            }
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