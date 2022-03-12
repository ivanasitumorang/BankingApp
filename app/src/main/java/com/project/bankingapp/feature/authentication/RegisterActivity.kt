package com.project.bankingapp.feature.authentication

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.project.bankingapp.R
import com.project.bankingapp.base.ScreenState
import com.project.bankingapp.common.showToast
import com.project.bankingapp.databinding.ActivityRegisterBinding
import com.project.bankingapp.feature.dashboard.DashboardActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    private val viewModel by viewModels<AuthenticationVM>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUIListener()
        setupDataObserver()
    }

    private fun setupDataObserver() {
        viewModel.registerResult.observe(this) {
            when (it) {
                is ScreenState.Loading -> {
                    toggleScreenState(enable = false)
                }
                is ScreenState.Success -> {
                    startActivity(Intent(this, DashboardActivity::class.java))
                    finishAffinity()
                }
                is ScreenState.Error -> {
                    toggleScreenState(enable = true)
                    showToast(it.exception.message.toString())
                }
            }
        }
    }

    private fun setupUIListener() = with(binding) {
        ivBack.setOnClickListener {
            finish()
        }

        btnRegister.setOnClickListener {
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()
            val confirmPassword = etConfirmPassword.text.toString()

            val usernameValid = username.isNotEmpty()
            val passwordValid = password.isNotEmpty()
            val confirmPasswordValid = confirmPassword.isNotEmpty()

            tilUsername.isErrorEnabled = !usernameValid
            tilPassword.isErrorEnabled = !passwordValid
            tilConfirmPassword.isErrorEnabled = !confirmPasswordValid

            if (usernameValid && passwordValid && password == confirmPassword) {
                showToast("register berhasil")
                viewModel.register(username, password)
            } else {
                if (!usernameValid) {
                    tilUsername.error = getString(R.string.form_username_error)
                }
                if (!passwordValid && !confirmPasswordValid) {
                    tilPassword.error = getString(R.string.form_password_error)
                    tilConfirmPassword.error = getString(R.string.form_confirm_password_error)
                } else if (!passwordValid) {
                    tilPassword.error = getString(R.string.form_password_error)
                }

                if (password != confirmPassword) {
                    tilConfirmPassword.error =
                        getString(R.string.form_confirm_password_not_match_error)
                }
            }
        }
    }

    private fun toggleScreenState(enable: Boolean) = with(binding) {
        tilUsername.isEnabled = enable
        tilPassword.isEnabled = enable
        tilConfirmPassword.isEnabled = enable
        btnRegister.isEnabled = enable
        loading.visibility = if (enable) View.GONE else View.VISIBLE
    }
}