package com.example.foodfireproject

import Fragments.FoodFragment
import Fragments.homeFragment
import Fragments.settingsFragment
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class Home : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth

    fun changeFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply{
            replace(R.id.fContainer,fragment)
            commit()
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Initialize Firebase Auth
        auth = Firebase.auth
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets


        }


        val homeFragment = homeFragment()
        val foodFragment = FoodFragment()
        val settingFragment = settingsFragment()

        changeFragment(homeFragment)

        findViewById<BottomNavigationView>(R.id.bottom_nav).setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.ic_home -> {
                    changeFragment(homeFragment)
                    true
                }

                R.id.ic_food -> {
                    changeFragment(foodFragment)
                    true
                }

                R.id.ic_settings -> {
                    changeFragment(settingFragment)
                    true
                }

                else -> false

            }
        }



        //findViewById<BottomNavigationView>(R.id.bottom_nav)
        //sign out button
        findViewById<Button>(R.id.signoutButton).setOnClickListener{
            auth.signOut()
            Toast.makeText(this, "Signed out successfully", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }




    }









}