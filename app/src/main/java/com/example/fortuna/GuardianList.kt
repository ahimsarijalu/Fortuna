package com.example.fortuna

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.core.app.ActivityCompat.finishAffinity

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class GuardianList : Fragment() {

    private lateinit var dbref: DatabaseReference
    private lateinit var guardianArrayList: ArrayList<Guardian>
    private lateinit var guardianRecyclerView: RecyclerView



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view: View = inflater.inflate(R.layout.fragment_guardian, container, false)

        guardianRecyclerView = view.findViewById(R.id.recyclerView)
        guardianRecyclerView.layoutManager = LinearLayoutManager(context)
        guardianRecyclerView.setHasFixedSize(true)

        guardianArrayList = arrayListOf()
        getGuardian()

        view.findViewById<ImageButton>(R.id.addContact).setOnClickListener {
            startActivity(Intent(this@GuardianList.context, InputGuardian::class.java))
        }

        return view
    }


    private fun getGuardian() {
        dbref = FirebaseDatabase.getInstance().getReference("Guardian")
        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (guardianSnapshot in snapshot.children) {
                        val guardian = guardianSnapshot.getValue(Guardian::class.java)
                        if (guardian != null && guardian.uid == Firebase.auth.currentUser!!.uid) {
                            guardianArrayList.add(guardian)
                        }
                    }
                    val adapter = GuardianAdapter(guardianArrayList)
                    adapter.setOnItemClickListener(object : GuardianAdapter.onItemClickListener{
                        override fun onItemClicked(key: String?, position: Int) {
                            startActivity(Intent(this@GuardianList.context, EditGuardian::class.java).putExtra("key", key))
                        }
                    })
                    guardianRecyclerView.adapter = adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }
}