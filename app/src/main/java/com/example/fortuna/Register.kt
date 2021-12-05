package com.example.fortuna

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.database.FirebaseDatabase

class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val registBtn = findViewById<Button>(R.id.registerBtn)
        val nameInput = findViewById<EditText>(R.id.inputName)
        val emailInput = findViewById<EditText>(R.id.inputEmail)
        val passwordInput = findViewById<EditText>(R.id.inputPassword)

        registBtn.setOnClickListener {
            if (nameInput.text.isEmpty() || emailInput.text.isEmpty() || passwordInput.text.isEmpty()) {
                Toast.makeText(this, "Please fill all fields!", Toast.LENGTH_SHORT).show()
            } else {
                val name = nameInput.text.toString()
                val email = emailInput.text.toString()
                val password = passwordInput.text.toString()

                registerUser(email, password, name)
            }
        }
    }

    private fun registerUser(email: String, password: String, name: String) {
        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val firebaseUser: FirebaseUser = task.result!!.user!!
                    val profileUpdates = userProfileChangeRequest {
                        displayName = name
                    }

                    firebaseUser.updateProfile(profileUpdates)


                    FirebaseAuth.getInstance().signOut()

                    startActivity(Intent(this, Login::class.java))
                    finishAffinity()
                } else {
                    Toast.makeText(this, "Register failed", Toast.LENGTH_SHORT).show()
                }
            }
    }
}