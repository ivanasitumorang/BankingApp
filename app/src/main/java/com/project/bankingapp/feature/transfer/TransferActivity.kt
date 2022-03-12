package com.project.bankingapp.feature.transfer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.project.bankingapp.R
import com.project.bankingapp.databinding.ActivityTransferBinding

class TransferActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTransferBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransferBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUIListener()
    }

    private fun setupUIListener() = with(binding) {
        ivBack.setOnClickListener {
            finish()
        }

        btnTransfer.setOnClickListener {
            val payee = tvPayeeName.text.toString()
            val amount = etAmount.text.toString()
            val description = etDescription.text.toString()

            val payeeValid = payee.isNotEmpty()
            val amountValid = amount.isNotEmpty()

            tilPayee.isErrorEnabled = !payeeValid
            tilAmount.isErrorEnabled = !amountValid

            if (payeeValid && amountValid) {

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
}