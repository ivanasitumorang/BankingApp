package com.project.bankingapp.feature.authentication

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.project.bankingapp.R
import com.project.bankingapp.common.showToast
import com.project.bankingapp.databinding.ActivityRegisterBinding
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
}