package com.project.bankingapp.feature.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.project.bankingapp.R
import com.project.bankingapp.base.ScreenState
import com.project.bankingapp.common.showToast
import com.project.bankingapp.databinding.ActivityDashboardBinding
import com.project.bankingapp.feature.authentication.LoginActivity
import com.project.bankingapp.feature.dashboard.adapter.TransactionHistoryAdapter
import com.project.bankingapp.feature.dashboard.dto.AccountSummary
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding

    private lateinit var trxHistoryAdapter: TransactionHistoryAdapter

    private val viewModel by viewModels<DashboardVM>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        setupUIListener()
        setupDataObserver()

        viewModel.getAccountSummary()
        viewModel.getTransactionHistoryList()
    }

    private fun setupDataObserver() {
        viewModel.accountSummary.observe(this) {
            when (it) {
                is ScreenState.Loading -> {
                    binding.loadingSummary.visibility = View.VISIBLE
                }
                is ScreenState.Success -> {
                    binding.loadingSummary.visibility = View.GONE
                    populateAccountSummary(it.data)
                }
                is ScreenState.Error -> {
                    binding.loadingSummary.visibility = View.GONE
                }
            }
        }

        viewModel.trxHistoryList.observe(this) {
            when (it) {
                is ScreenState.Loading -> {
                    binding.loadingList.visibility = View.VISIBLE
                }
                is ScreenState.Success -> {
                    binding.loadingList.visibility = View.GONE
                    trxHistoryAdapter.setTrxHistoryList(it.data)
                }
                is ScreenState.Error -> {
                    binding.loadingList.visibility = View.GONE
                }
            }
        }
    }

    private fun populateAccountSummary(data: AccountSummary) = with(binding) {
        tvAccountBalance.text = getString(R.string.dashboard_account_balance, data.balance)
        tvAccountNo.text = data.accountNo
        tvAccounName.text = data.name
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
            viewModel.getTransactionHistoryList()
            showToast("navigate to page transfer")
        }
    }
}