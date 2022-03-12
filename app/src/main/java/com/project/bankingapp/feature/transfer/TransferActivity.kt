package com.project.bankingapp.feature.transfer

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.project.bankingapp.R
import com.project.bankingapp.base.ScreenState
import com.project.bankingapp.common.showToast
import com.project.bankingapp.databinding.ActivityTransferBinding
import com.project.bankingapp.feature.dashboard.DashboardActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransferActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTransferBinding

    private val viewModel by viewModels<TransferVM>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransferBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUIListener()
        setupDataObserver()
    }

    private fun setupDataObserver() {
        viewModel.transferResult.observe(this) {
            when (it) {
                is ScreenState.Loading -> toggleScreenState(enable = false)
                is ScreenState.Success -> {
                    startActivity(Intent(this, DashboardActivity::class.java))
                    finish()
                }
                is ScreenState.Error -> {
                    toggleScreenState(enable = true)
                    showToast(it.exception.message.toString())
                }
            }
        }

        viewModel.selectedPayee.observe(this) {
            binding.tvPayeeName.setText(it.name)
            binding.tvPayeeNo.text = it.no
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupUIListener() = with(binding) {
        ivBack.setOnClickListener {
            finish()
        }

        tvPayeeName.setOnClickListener {
            PayeeListFragment().show(supportFragmentManager, PayeeListFragment.TAG)
        }

        btnTransfer.setOnClickListener {
            val payee = tvPayeeName.text.toString()
            val payeeNo = tvPayeeNo.text.toString()
            val amount = etAmount.text.toString()
            val description = etDescription.text.toString()

            val payeeValid = payee.isNotEmpty()
            val payeeNoValid = payeeNo.isNotEmpty()
            val amountValid = amount.isNotEmpty()

            tilPayee.isErrorEnabled = !payeeValid
            tilAmount.isErrorEnabled = !amountValid

            if (payeeValid && amountValid && payeeNoValid) {
                viewModel.transfer(payeeNo, amount, description)
            } else {
                if (!payeeValid) {
                    tilPayee.error = getString(R.string.form_payee_error)
                }
                if (!amountValid) {
                    tilAmount.error = getString(R.string.form_amount_error)
                }
            }
        }
    }

    private fun toggleScreenState(enable: Boolean) = with(binding) {
        tilPayee.isEnabled = enable
        tilAmount.isEnabled = enable
        tilDescription.isEnabled = enable
        btnTransfer.isEnabled = enable
        loading.visibility = if (enable) View.GONE else View.VISIBLE
    }
}