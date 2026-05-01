package com.example.escuelaciclista_tfg

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class AlumnosDirectorActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AlumnoAdapter

    private val lista = mutableListOf<Alumno>()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista)

        recyclerView = findViewById(R.id.recyclerAlumnos)
        val btnMenu = findViewById<Button>(R.id.btnMenu) // ← AÑADIR ESTO

        adapter = AlumnoAdapter(lista)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // BOTÓN VOLVER
        btnMenu.setOnClickListener {
            finish()
        }

        val uid = intent.getStringExtra("uid")

        if (uid != null) {
            cargarAlumnos(uid)
        }
    }

    private fun cargarAlumnos(uid: String) {
        db.collection("alumnos")
            .whereEqualTo("usuarioId", uid)
            .get()
            .addOnSuccessListener { result ->

                lista.clear()

                for (doc in result) {
                    val alumno = doc.toObject(Alumno::class.java)
                    alumno.id = doc.id
                    lista.add(alumno)
                }

                adapter.actualizarLista(lista)
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error al cargar alumnos", Toast.LENGTH_SHORT).show()
            }
    }
}