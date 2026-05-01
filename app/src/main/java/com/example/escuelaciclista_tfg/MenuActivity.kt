package com.example.escuelaciclista_tfg

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MenuActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val tvBienvenida = findViewById<TextView>(R.id.tvBienvenida)

        val btnNuevoAlumno = findViewById<Button>(R.id.btnNuevoAlumno)
        val btnListaAlumnos = findViewById<Button>(R.id.btnListaAlumnos)
        val btnMisAlumnos = findViewById<Button>(R.id.btnMisAlumnos)
        val btnDirectores = findViewById<Button>(R.id.btnDirectores) // NUEVO

        // CARGAR DIRECTOR
        val uid = FirebaseAuth.getInstance().currentUser?.uid

        if (uid != null) {

            db.collection("directores")
                .document(uid)
                .get()
                .addOnSuccessListener { document ->

                    if (document.exists()) {

                        val nombre = document.getString("nombre")

                        tvBienvenida.text = "Bienvenido $nombre"

                    } else {

                        tvBienvenida.text = "Bienvenido (sin ficha)"
                    }
                }
                .addOnFailureListener {

                    tvBienvenida.text = "Bienvenido (error)"
                }

        } else {

            tvBienvenida.text = "Bienvenido (sin login)"
        }

        // BOTÓN NUEVO ALUMNO
        btnNuevoAlumno.setOnClickListener {

            val intent = Intent(this, DatosPersonalesActivity::class.java)
            startActivity(intent)
        }

        // BOTÓN LISTA ALUMNOS
        btnListaAlumnos.setOnClickListener {

            val intent = Intent(this, ListaActivity::class.java)
            startActivity(intent)
        }

        // BOTÓN MIS ALUMNOS
        btnMisAlumnos.setOnClickListener {

            val intent = Intent(this, miLista::class.java)
            startActivity(intent)
        }

        // BOTÓN DIRECTORES (NUEVO)
        btnDirectores.setOnClickListener {

            val intent = Intent(this, ListaDirectoresActivity::class.java)
            startActivity(intent)
        }
    }
}