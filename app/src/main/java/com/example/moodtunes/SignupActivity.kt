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

class SignupActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirm: EditText
    private lateinit var btnSignup: Button
    private lateinit var tvLogin: TextView
    private lateinit var tvError: TextView
    private lateinit var progress: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        auth = FirebaseAuth.getInstance()

        etEmail = findViewById(R.id.et_email)
        etPassword = findViewById(R.id.et_password)
        etConfirm = findViewById(R.id.et_confirm_password)
        btnSignup = findViewById(R.id.btn_signup)
        tvLogin = findViewById(R.id.tv_login_link)
        tvError = findViewById(R.id.tv_error)
        progress = findViewById(R.id.progress)

        btnSignup.setOnClickListener { attemptSignup() }
        tvLogin.setOnClickListener { finish() }
    }

    private fun attemptSignup() {
        tvError.visibility = View.GONE
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString()
        val confirm = etConfirm.text.toString()

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            tvError.text = getString(R.string.invalid_email)
            tvError.visibility = View.VISIBLE
            return
        }

        if (password.length < 6) {
            tvError.text = getString(R.string.password_length_error)
            tvError.visibility = View.VISIBLE
            return
        }

        if (password != confirm) {
            tvError.text = getString(R.string.password_mismatch)
            tvError.visibility = View.VISIBLE
            return
        }

        progress.visibility = View.VISIBLE
        btnSignup.isEnabled = false

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                progress.visibility = View.GONE
                btnSignup.isEnabled = true
                if (task.isSuccessful) {
                    // Account created — redirect to login so user can sign in
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                } else {
                    val ex = task.exception
                    val message = when {
                        ex?.message?.contains("email address is already in use", true) == true || ex?.message?.contains("already in use", true) == true -> "An account with that email already exists."
                        ex?.message?.contains("A network error", true) == true -> getString(R.string.network_error)
                        else -> ex?.localizedMessage ?: "Signup failed."
                    }
                    tvError.text = message
                    tvError.visibility = View.VISIBLE
                }
            }
    }
}

