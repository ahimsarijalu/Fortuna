package com.example.fortuna

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.*
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import android.widget.Toast

import android.content.pm.PackageInfo
import android.net.Uri
import android.view.View


class MainActivity : AppCompatActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val home = Home()
        val guardianList = GuardianList()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainerView, home)
            commit()
        }

        findViewById<BottomNavigationItemView>(R.id.homeBtn).setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragmentContainerView, home)
                commit()
            }
        }

        findViewById<BottomNavigationItemView>(R.id.contactBtn).setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragmentContainerView, guardianList)
                commit()
            }
        }

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return@setOnClickListener
            }
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location : Location? ->
                    var longitude = location?.longitude
                    var latitude = location?.latitude

                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(" https://wa.me/6285741441130?text=Saya%20sedang%20dalam%20bahaya,%20Tolong%20segera%20ke%20sini.%20Berikut%20lokasi%20saya:%20"))
                    startActivity(browserIntent)
                }
        }


    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_screen_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.logout_item) {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, Login::class.java))
            finish()
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}