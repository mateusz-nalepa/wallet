package com.mateuszcholyn.wallet.domain.moneysaver.activity

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment.*
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import android.widget.Toast
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.android.AppCompatActivityInjector
import com.github.salomonbrys.kodein.instance
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.config.ApplicationContext
import com.mateuszcholyn.wallet.domain.moneysaver.service.MoneySaverService
import org.joda.time.LocalDateTime
import java.io.File


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

        saveToFile()
    }


    //storage/emulated/0/download
    private fun saveToFile() {
        if (MEDIA_MOUNTED != getExternalStorageState()) {
            return
        }
        val file = File(getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS), "dane.txt")

        verifyStoragePermissions(this)
        kotlin.runCatching {
            file.createNewFile()
            file.writeText("asd")
        }.onFailure { nieDziala(it) }
                .onSuccess { dziala() }

    }

    fun verifyStoragePermissions(activity: Activity) {
        // Check if we have write permission
        val permission = ActivityCompat.checkSelfPermission(activity, WRITE_EXTERNAL_STORAGE)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(WRITE_EXTERNAL_STORAGE),
                    1
            )
        }
    }

    private fun nieDziala(ex: Throwable) {
        Toast.makeText(ApplicationContext.appContext, "Nie dziala: ${ex.message}", Toast.LENGTH_LONG).show()
    }

    private fun dziala() {
        Toast.makeText(ApplicationContext.appContext, "Dziala", Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        destroyInjector()
        super.onDestroy()
    }
}
