package com.example.foodfireproject

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.FirebaseFirestore

class username : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_username)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }





        findViewById<Button>(R.id.usernameButton).setOnClickListener{

            //getting users name from user
            val username = findViewById<EditText>(R.id.userNameET).text.toString()

            val db = FirebaseFirestore.getInstance()

            val user: MutableMap<String,Any> = HashMap()

            user["userName"] = username

            db.collection("user-Information")
                .add(user)
                .addOnSuccessListener {
                    Log.d("dbfirebase", "save: $user")
                }
                .addOnFailureListener{
                    Log.d("dbfirebase Failed","save: $user")
                }







        }













    }
}