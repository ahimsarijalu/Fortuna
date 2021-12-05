package com.example.fortuna

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class InputGuardian : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_guardian)

        supportActionBar?.setTitle("Input Guardian")

        val inputNama = findViewById<EditText>(R.id.inputContactName)
        val inputNomor = findViewById<EditText>(R.id.inputNomorContact)
        val db = FirebaseDatabase.getInstance()
        val databaseReference = db.getReference("Guardian")

        findViewById<Button>(R.id.saveContactBtn).setOnClickListener {
            val nama = inputNama.text.toString()
            val nomor = inputNomor.text.toString()

            if( nama.isEmpty() || nomor.isEmpty()) {
                Toast.makeText(this, "Please fill all field!", Toast.LENGTH_SHORT).show()
            } else {
                val key = databaseReference.push().key
                val keyString = key.toString()
                val uid = Firebase.auth.currentUser!!.uid
                val model = Guardian(nama, nomor, keyString, uid)

                databaseReference.child(key!!).setValue(model).addOnCompleteListener{ task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Guardian Berhasil Ditambahkan", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, GuardianList::class.java))
                        finish()
                    }
                }
            }
        }
    }



}