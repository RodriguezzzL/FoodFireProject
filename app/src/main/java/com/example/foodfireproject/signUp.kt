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

class signUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        /* Submitting first name and last name values when create account button is pressed. */
        findViewById<Button>(R.id.createAccountButton).setOnClickListener {


            // getting values from

            val firstName = findViewById<EditText>(R.id.firstNameET).text.toString()
            val lastName = findViewById<EditText>(R.id.lastNameET).text.toString()


            val db = FirebaseFirestore.getInstance()
            val user: MutableMap<String, Any> = HashMap()
            user["firstName"] = firstName
            user["lastName"] = lastName

            db.collection("User-Information")
                .add(user)
                .addOnSuccessListener {
                    Log.d("dbfirebase", "save: $user")
                }
                .addOnFailureListener {
                    Log.d("dbfirebase Failed", "save $user")
                }
            db.collection("User-Information")
                .get()
                .addOnCompleteListener {
                    val result: StringBuffer = StringBuffer()
                    if (it.isSuccessful) {
                        for (document in it.result!!) {
                            Log.d(
                                "dbfirebase", "retrive: " +
                                        "${document.data.getValue("firstName")}" +
                                        "${document.data.getValue("lastName")}"
                            )

                        }
                    }
                }


        }

    }
}