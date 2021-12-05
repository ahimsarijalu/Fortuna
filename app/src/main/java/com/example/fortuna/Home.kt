package com.example.fortuna

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Home : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)

        val greetings: TextView = view.findViewById(R.id.greetings)
        val user = Firebase.auth.currentUser
        if (user != null) {
            greetings.text = "Halo, ${user.displayName}"
        }

        view.findViewById<Button>(R.id.callPoliceBtn).setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:110")
            startActivity(intent)
        }

        view.findViewById<Button>(R.id.callAmbulanceBtn).setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:118")
            startActivity(intent)
        }

        view.findViewById<Button>(R.id.callSARBtn).setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:115")
            startActivity(intent)
        }

        view.findViewById<Button>(R.id.callPemadamBtn).setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:113")
            startActivity(intent)
        }

        view.findViewById<Button>(R.id.callKomnasHAMBtn).setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:0213925230")
            startActivity(intent)
        }

        view.findViewById<Button>(R.id.callKomnasPerempuanBtn).setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:0213903963")
            startActivity(intent)
        }

        return view
    }
}