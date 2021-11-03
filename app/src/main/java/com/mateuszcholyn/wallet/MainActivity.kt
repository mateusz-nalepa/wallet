package com.mateuszcholyn.wallet


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.android.AppCompatActivityInjector
import com.mateuszcholyn.wallet.domain.category.activity.CategoryActivity
import com.mateuszcholyn.wallet.domain.expense.activity.AddExpenseActivityBinding
import com.mateuszcholyn.wallet.domain.expense.activity.AverageExpenseActivity
import com.mateuszcholyn.wallet.domain.expense.activity.ExpenseHistoryActivity
import com.mateuszcholyn.wallet.domain.moneysaver.activity.MoneySaverActivity
import kotlinx.android.synthetic.main.activity_main.*

const val EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE"

class MainActivity : AppCompatActivity(), AppCompatActivityInjector {

    override val injector: KodeinInjector = KodeinInjector()

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initMainActivity()
        initializeInjector()
    }

    @SuppressLint("RestrictedApi")
    private fun initMainActivity() {
        drawerLayout = findViewById(R.id.drawerLayout)
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDefaultDisplayHomeAsUpEnabled(true)
        setSupportActionBar(toolbar)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.x ml.
        if (item.itemId == R.id.action_settings) {
            return true
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

    fun showAddExpenseActivity(view: View) {
        val intent = Intent(this, AddExpenseActivityBinding::class.java)
        startActivity(intent)
    }

    fun showCategoryActivity(view: View) {
        val intent = Intent(this, CategoryActivity::class.java)
        startActivity(intent)
    }

    fun showHistoryActivity(view: View) {
        val intent = Intent(this, ExpenseHistoryActivity::class.java)
        startActivity(intent)
    }

    fun showAverageExpenseActivity(view: View) {
        val intent = Intent(this, AverageExpenseActivity::class.java)
        startActivity(intent)
    }

    fun showMoneySaverActivity(view: View) {
        val intent = Intent(this, MoneySaverActivity::class.java)
        startActivity(intent)
    }

    override fun onDestroy() {
        destroyInjector()
        super.onDestroy()
    }

}
