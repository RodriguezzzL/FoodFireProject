package com.example.foodfireproject

import Fragments.FoodFragment
import Fragments.homeFragment
import Fragments.settingsFragment
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
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


        //findViewById<BottomNavigationView>(R.id.bottom_nav)
        //sign out button


        fun permissionRequestAndChangeFragment(fragment:Fragment){
            val permissionList = mutableListOf<String>()
            if(!(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
                Log.d("test", "1st if PL")
                permissionList.add(android.Manifest.permission.ACCESS_COARSE_LOCATION)
            }
//            if(!(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)){
//                Log.d("test", "2nd if PL")
//                permissionList.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
//            }
            if(permissionList.isNotEmpty()){
                Log.d("test", "PL is not empty ask for permission")
                ActivityCompat.requestPermissions(this,permissionList.toTypedArray(),100)

            }
            else
            {
                Log.d("test", "changing to foodfrag")
                changeFragment(fragment)
            }

        }
        fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
        ) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            when(requestCode){
                100 -> {
                    for (index in grantResults.indices) {
                        if (grantResults[index] == PackageManager.PERMISSION_GRANTED){
                            val selectedFrag = FoodFragment()
                            changeFragment(selectedFrag)
                            Log.d("Permission", "Your ${permissions[index]} successful")
                        }
                    }

                }
            }
        }


        val homeFragment = homeFragment()
        val foodFragment = FoodFragment()
        val settingFragment = settingsFragment()

        changeFragment(homeFragment)

        findViewById<BottomNavigationView>(R.id.bottom_nav).setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.ic_home -> {
                    Log.d("test", "home fragment")
                    changeFragment(homeFragment)
                    true
                }

                R.id.ic_food -> {
                    Log.d("test", "food Fragment")
                    permissionRequestAndChangeFragment(foodFragment)
                    true
                }

                R.id.ic_settings -> {
                    Log.d("test", "settings Fragment")
                    changeFragment(settingFragment)
                    true
                }

                else -> false

            }
        }




    }

}