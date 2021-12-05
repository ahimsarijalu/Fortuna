package com.example.fortuna

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class EditGuardian : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actiivity_detail_guardian)
        supportActionBar?.title = "Edit Guardian"

        val editNama = findViewById<EditText>(R.id.editNama)
        val editPhone = findViewById<EditText>(R.id.editNomor)

        val key = intent.getStringExtra("key")
        val dbref = FirebaseDatabase.getInstance().getReference("Guardian").child(key!!)
        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val guardian = snapshot.getValue(Guardian::class.java)
                    editNama.setText(guardian!!.name)
                    editPhone.setText(guardian.phone)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        findViewById<Button>(R.id.editButton).setOnClickListener {
            dbref.child("name").setValue(editNama.text.toString())
            dbref.child("phone").setValue(editPhone.text.toString())
            Toast.makeText(
                this, "Data guardian berhasil diupdate",
                Toast.LENGTH_SHORT
            ).show()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        findViewById<Button>(R.id.hapusButton).setOnClickListener {
            dbref.removeValue()
            Toast.makeText(
                this, "Data guardian berhasil dihapus",
                Toast.LENGTH_SHORT
            ).show()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}