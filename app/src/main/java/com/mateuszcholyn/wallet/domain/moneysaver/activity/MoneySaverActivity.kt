package com.mateuszcholyn.wallet.domain.moneysaver.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.android.AppCompatActivityInjector
import com.github.salomonbrys.kodein.instance
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.domain.moneysaver.service.MoneySaverService

class MoneySaverActivity : AppCompatActivity(), AppCompatActivityInjector {

    override val injector: KodeinInjector = KodeinInjector()
    private val moneySaverService: MoneySaverService by instance()
    private lateinit var monthlyBudget: TextView
    private lateinit var spentMoney: TextView
    private lateinit var savedMoney: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initActivity()
        initFieldsValues()
    }

    private fun initActivity() {
        initializeInjector()
        setContentView(R.layout.activity_money_saver)
        monthlyBudget = findViewById(R.id.monthlyBudget)
        spentMoney = findViewById(R.id.spentMoney)
        savedMoney = findViewById(R.id.savedMoney)
    }

    private fun initFieldsValues() {
        var asd = moneySaverService.monthlyBudgetSummary()

    }

    override fun onDestroy() {
        destroyInjector()
        super.onDestroy()
    }
}
