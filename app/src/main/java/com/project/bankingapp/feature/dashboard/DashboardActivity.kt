package com.project.bankingapp.feature.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.project.bankingapp.R
import com.project.bankingapp.base.ScreenState
import com.project.bankingapp.databinding.ActivityDashboardBinding
import com.project.bankingapp.feature.authentication.LoginActivity
import com.project.bankingapp.feature.dashboard.adapter.TransactionHistoryAdapter
import com.project.bankingapp.feature.dashboard.dto.AccountSummary
import com.project.bankingapp.feature.transfer.TransferActivity
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

        populateData()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        populateData()
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

        viewModel.logoutResult.observe(this) {
            startActivity(Intent(this@DashboardActivity, LoginActivity::class.java))
            finishAffinity()
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
        rvTrxHistory.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val topRowVerticalPosition =
                    if (recyclerView.childCount == 0) 0 else recyclerView.getChildAt(0).top
                binding.swipeRefresh.isEnabled = topRowVerticalPosition >= 0
            }
        })
    }

    private fun setupUIListener() = with(binding) {
        tvLogout.setOnClickListener {
            viewModel.logout()
        }

        btnTransfer.setOnClickListener {
            startActivity(Intent(this@DashboardActivity, TransferActivity::class.java))
        }

        swipeRefresh.setOnRefreshListener {
//            swipeRefresh.isRefreshing = true
            populateData()
            swipeRefresh.isRefreshing = false
        }
    }

    private fun populateData() {
        viewModel.getAccountSummary()
        viewModel.getTransactionHistoryList()
    }
}