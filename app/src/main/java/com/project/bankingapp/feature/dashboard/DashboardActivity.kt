package com.project.bankingapp.feature.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.project.bankingapp.common.showToast
import com.project.bankingapp.databinding.ActivityDashboardBinding
import com.project.bankingapp.feature.authentication.LoginActivity

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding

    private lateinit var trxHistoryAdapter: TransactionHistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        setupUIListener()
        trxHistoryAdapter.setTrxHistoryList(trxHistoryList)
    }

    private fun setupUI() = with(binding) {
        trxHistoryAdapter = TransactionHistoryAdapter()
        rvTrxHistory.adapter = trxHistoryAdapter
    }

    private fun setupUIListener() = with(binding) {
        tvLogout.setOnClickListener {
            showToast("logout")
            startActivity(Intent(this@DashboardActivity, LoginActivity::class.java))
            finishAffinity()
        }

        btnTransfer.setOnClickListener {
            showToast("navigate to page transfer")
        }
    }
}