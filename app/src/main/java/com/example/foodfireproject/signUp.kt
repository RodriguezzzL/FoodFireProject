package com.example.foodfireproject

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import java.util.Date

class signUp : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)

        // Initialize Firebase Auth
        auth = Firebase.auth


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        /* Submitting first name and last name values when create account button is pressed. */

        findViewById<Button>(R.id.createAccountButton).setOnClickListener {

            val email = findViewById<EditText>(R.id.EmailET).text.toString()
            val password = findViewById<EditText>(R.id.passwordCreateET).text.toString()
            val firstName = findViewById<EditText>(R.id.firstNameET).text.toString()
            val lastName = findViewById<EditText>(R.id.lastNameET).text.toString()

            //checking if any field is empty
            if(email.isEmpty() || password.isEmpty() || firstName.isEmpty() || lastName.isEmpty()){
                Toast.makeText(this,"Please fill in all fields.", Toast.LENGTH_SHORT).show()
            }

            //storing values into database and auth
            else {
                val db = FirebaseFirestore.getInstance()
                val user: MutableMap<String,Any> = HashMap()
                user["fireName"] = firstName
                user["lastName"] = lastName
                user["Email"] = email
                user["password"] = password
                user["createdAt"] = Timestamp(Date(System.currentTimeMillis()))
                db.collection("users")
                    .add(user)
                    .addOnSuccessListener {
                        Log.d("dbfirebase", "save: $user")
                    }
                    .addOnFailureListener{
                        Log.d("dbfirebase Failed", "$user")
                    }

                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success")
                            val user = auth.currentUser
                            startActivity(Intent(this, Home::class.java))
                            Toast.makeText(
                                baseContext,
                                "Account successfully created.",
                                Toast.LENGTH_SHORT,
                            ).show()
                        }
                        else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.exception)
                            Toast.makeText(
                                baseContext,
                                "Authentication failed.",
                                Toast.LENGTH_SHORT,
                            ).show()

                        }
                    }
            }
        }
    }
}