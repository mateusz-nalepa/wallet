package com.mateuszcholyn.wallet.view.moneysaver

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.domain.moneysaver.MoneySaverService
import org.kodein.di.DIAware
import org.kodein.di.android.closestDI
import org.kodein.di.instance
import java.time.LocalDateTime


class MoneySaverActivity : AppCompatActivity(), DIAware {

    override val di by closestDI()

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
        setContentView(R.layout.activity_money_saver)
        monthlyBudget = findViewById(R.id.monthlyBudget)
        spentMoney = findViewById(R.id.spentMoney)
        savedMoney = findViewById(R.id.savedMoney)
    }

    @SuppressLint("SetTextI18n")
    private fun initFieldsValues() {
        val now = LocalDateTime.now()
        moneySaverService.monthlyBudgetSummaryFor(now.year, now.monthValue).also {
            monthlyBudget.text = it.monthlyBudget.toString() + " zł"
            spentMoney.text = it.spentMoney.toString() + " zł"
            savedMoney.text = it.savedMoney.toString() + " zł"
        }
    }

}
