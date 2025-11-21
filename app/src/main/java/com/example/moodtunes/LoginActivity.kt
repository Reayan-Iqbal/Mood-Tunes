package com.example.moodtunes

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var tvSignup: TextView
    private lateinit var tvError: TextView
    private lateinit var progress: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        etEmail = findViewById(R.id.et_email)
        etPassword = findViewById(R.id.et_password)
        btnLogin = findViewById(R.id.btn_login)
        tvSignup = findViewById(R.id.tv_signup)
        tvError = findViewById(R.id.tv_error)
        progress = findViewById(R.id.progress)

        btnLogin.setOnClickListener { attemptLogin() }
        tvSignup.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        // If user already logged in, go to MainActivity
        val currentUser = auth.currentUser
        if (currentUser != null) {
            navigateToMain()
        }
    }

    private fun attemptLogin() {
        tvError.visibility = View.GONE
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString()

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            tvError.text = getString(R.string.invalid_email)
            tvError.visibility = View.VISIBLE
            return
        }

        if (password.isEmpty()) {
            tvError.text = getString(R.string.enter_password)
            tvError.visibility = View.VISIBLE
            return
        }

        progress.visibility = View.VISIBLE
        btnLogin.isEnabled = false

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                progress.visibility = View.GONE
                btnLogin.isEnabled = true
                if (task.isSuccessful) {
                    navigateToMain()
                } else {
                    val ex = task.exception
                    val message = when {
                        ex?.message?.contains("password is invalid", true) == true -> "Incorrect password."
                        ex?.message?.contains("No user record", true) == true -> "No account found for that email."
                        ex?.message?.contains("A network error", true) == true -> getString(R.string.network_error)
                        else -> ex?.localizedMessage ?: "Authentication failed."
                    }
                    tvError.text = message
                    tvError.visibility = View.VISIBLE
                }
            }
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
