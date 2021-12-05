package com.example.fortuna

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    override fun onStart() {
        super.onStart()
        if (FirebaseAuth.getInstance().currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val emailInput = findViewById<EditText>(R.id.emailField)
        val passwordInput = findViewById<EditText>(R.id.passwordField)

        findViewById<Button>(R.id.directToRegisterBtn).setOnClickListener {
            startActivity(Intent(this, Register::class.java))
        }

        findViewById<Button>(R.id.loginBtn).setOnClickListener {
            if (emailInput.text.isEmpty() || passwordInput.text.isEmpty()) {

                Toast.makeText(
                    this, "Please fill all the fields!",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val email = emailInput.text.toString()
                val password = passwordInput.text.toString()

                loginUser(email, password)
            }
        }


    }

    private fun loginUser(email: String, password: String) {
        FirebaseAuth
            .getInstance()
            .signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        this, "Login success",
                        Toast.LENGTH_SHORT
                    ).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finishAffinity()
                } else {
                    Toast.makeText(
                        this, "Login failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}