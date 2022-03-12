package com.project.bankingapp.feature.authentication

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.project.bankingapp.R
import com.project.bankingapp.base.ScreenState
import com.project.bankingapp.common.showToast
import com.project.bankingapp.databinding.ActivityLoginBinding
import com.project.bankingapp.feature.dashboard.DashboardActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val viewModel by viewModels<AuthenticationVM>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUIListener()
        setupDataObserver()
    }

    private fun setupDataObserver() {
        viewModel.loginResult.observe(this) {
            when (it) {
                is ScreenState.Loading -> {
                    toggleScreenState(enable = false)
                }
                is ScreenState.Success -> {
                    startActivity(Intent(this@LoginActivity, DashboardActivity::class.java))
                    finish()
                }
                is ScreenState.Error -> {
                    toggleScreenState(enable = true)
                    showToast(it.exception.message.toString())
                }
            }
        }
    }

    private fun setupUIListener() = with(binding) {
        btnLogin.setOnClickListener {
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()

            val usernameValid = username.isNotEmpty()
            val passwordValid = password.isNotEmpty()

            tilUsername.isErrorEnabled = !usernameValid
            tilPassword.isErrorEnabled = !passwordValid

            if (usernameValid && passwordValid) {
                viewModel.login(username, password)
            } else {
                if (!usernameValid) {
                    tilUsername.error = getString(R.string.form_username_error)
                }
                if (!passwordValid) {
                    tilPassword.error = getString(R.string.form_password_error)
                }
            }
        }

        btnRegister.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }
    }

    private fun toggleScreenState(enable: Boolean) = with(binding) {
        tilUsername.isEnabled = enable
        tilPassword.isEnabled = enable
        btnRegister.isEnabled = enable
        btnLogin.isEnabled = enable
        loading.visibility = if (enable) View.GONE else View.VISIBLE
    }
}