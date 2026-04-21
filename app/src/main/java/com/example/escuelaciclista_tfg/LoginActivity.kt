package com.example.escuelaciclista_tfg

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        val etUsuario = findViewById<EditText>(R.id.etUsuario)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnAcceder = findViewById<Button>(R.id.btnAcceder)

        // BOTÓN ACCEDER
        btnAcceder.setOnClickListener {

            val email = etUsuario.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Introduce email y contraseña", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->

                    if (task.isSuccessful) {

                        val usuario = auth.currentUser
                        val uid = usuario?.uid ?: ""

                        // BUSCAR DIRECTOR
                        db.collection("directores")
                            .document(uid)
                            .get()
                            .addOnSuccessListener { document ->

                                if (document.exists()) {

                                    val nombreDirector =
                                        document.getString("nombre") ?: "Director"

                                    Toast.makeText(
                                        this,
                                        "Bienvenido $nombreDirector",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                } else {

                                    Toast.makeText(
                                        this,
                                        "Login correcto",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                                val intent =
                                    Intent(this, MenuActivity::class.java)

                                startActivity(intent)
                                finish()
                            }

                            .addOnFailureListener {

                                val intent =
                                    Intent(this, MenuActivity::class.java)

                                startActivity(intent)
                                finish()
                            }

                    } else {

                        Toast.makeText(
                            this,
                            "Error: ${task.exception?.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }
    }
}