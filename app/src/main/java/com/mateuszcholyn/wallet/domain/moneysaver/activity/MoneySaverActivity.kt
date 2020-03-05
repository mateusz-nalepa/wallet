package com.mateuszcholyn.wallet.domain.moneysaver.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.android.AppCompatActivityInjector
import com.github.salomonbrys.kodein.instance
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.domain.moneysaver.service.MoneySaverService
import com.mateuszcholyn.wallet.util.saveToFile
import org.joda.time.LocalDateTime


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

    @SuppressLint("SetTextI18n")
    private fun initFieldsValues() {
        val now = LocalDateTime.now()
        moneySaverService.monthlyBudgetSummaryFor(now.year, now.monthOfYear).also {
            monthlyBudget.text = it.monthlyBudget.toString() + " zł"
            spentMoney.text = it.spentMoney.toString() + " zł"
            savedMoney.text = it.savedMoney.toString() + " zł"
        }

        saveToFile(this);
    }


    override fun onDestroy() {
        destroyInjector()
        super.onDestroy()
    }
}