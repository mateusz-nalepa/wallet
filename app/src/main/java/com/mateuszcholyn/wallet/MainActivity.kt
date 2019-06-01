package com.mateuszcholyn.wallet


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import com.mateuszcholyn.wallet.activity.*
import com.mateuszcholyn.wallet.category.activity.CategoryActivity
import com.mateuszcholyn.wallet.expense.activity.AddExpenseActivity
import com.mateuszcholyn.wallet.expense.activity.AverageExpenseActivity
import com.mateuszcholyn.wallet.expense.activity.ExpenseHistoryActivity
import kotlinx.android.synthetic.main.activity_main.*

const val EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE"

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initMainActivity()
    }

    @SuppressLint("RestrictedApi")
    private fun initMainActivity() {
        drawerLayout = findViewById(R.id.drawerLayout)
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDefaultDisplayHomeAsUpEnabled(true)
        setSupportActionBar(toolbar)
        makeSnackBar()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    private fun makeSnackBar() {
        fab.setOnClickListener {
            Snackbar.make(it, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null)
                    .show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.x ml.
        if (item.itemId == R.id.action_settings) {
            return true
//            else -> super.onOptionsItemSelected(item)
        }

        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun sendMessage(view: View) {
        val editText = findViewById<EditText>(R.id.editText)
        val message = editText.text.toString()
        val intent = Intent(this, DisplayMessageActivity::class.java).apply {
            putExtra(EXTRA_MESSAGE, message)
        }
        startActivity(intent)
    }

    fun showAddExpenseActivity(item: MenuItem) {
        val intent = Intent(this, AddExpenseActivity::class.java)
        startActivity(intent)
    }

    fun showCategoryActivity(item: MenuItem) {
        val intent = Intent(this, CategoryActivity::class.java)
        startActivity(intent)
    }

    fun showHistoryActivity(item: MenuItem) {
        val intent = Intent(this, ExpenseHistoryActivity::class.java)
        startActivity(intent)
    }

    fun showAverageExpenseActivity(item: MenuItem) {
        val intent = Intent(this, AverageExpenseActivity::class.java)
        startActivity(intent)
    }

}
