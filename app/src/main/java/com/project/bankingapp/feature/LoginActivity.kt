package com.project.bankingapp.feature

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.project.bankingapp.common.showToast
import com.project.bankingapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUIListener()
    }

    private fun setupUIListener() = with(binding) {
        btnLogin.setOnClickListener {
            // todo: verify if form empty
            val usernameValid = etUsername.text.isNullOrEmpty()
            val passwordValid = etPassword.text.isNullOrEmpty()

            tilUsername.isErrorEnabled = usernameValid
            tilPassword.isErrorEnabled = passwordValid

            if (usernameValid && passwordValid) {
                showToast("click button login -> verify data")
            } else {
                if (!usernameValid) {
                    tilUsername.error = "Username is required"
                }
                if (!passwordValid) {
                    tilPassword.error = "Password is required"
                }
            }
        }

        btnRegister.setOnClickListener {
            showToast("click button register -> navigate")
        }
    }
}