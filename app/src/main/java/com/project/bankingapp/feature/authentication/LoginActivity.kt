package com.project.bankingapp.feature.authentication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.project.bankingapp.common.showToast
import com.project.bankingapp.databinding.ActivityLoginBinding
import com.project.bankingapp.feature.dashboard.DashboardActivity

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
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()

            val usernameValid = username.isNotEmpty()
            val passwordValid = password.isNotEmpty()

            tilUsername.isErrorEnabled = !usernameValid
            tilPassword.isErrorEnabled = !passwordValid

            if (username == "test" && password == "asdasd") {
                // todo : show loading
                startActivity(Intent(this@LoginActivity, DashboardActivity::class.java))
                finish()
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