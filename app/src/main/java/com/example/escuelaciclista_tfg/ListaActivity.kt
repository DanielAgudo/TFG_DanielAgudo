package com.example.escuelaciclista_tfg

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class ListaActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var adapter: AlumnoAdapter
    private lateinit var btnMenu: Button

    private var listaAlumnos = mutableListOf<Alumno>()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista)

        recyclerView = findViewById(R.id.recyclerAlumnos)
        searchView = findViewById(R.id.searchView)
        btnMenu = findViewById(R.id.btnMenu)

        adapter = AlumnoAdapter(listaAlumnos)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        cargarAlumnos()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?) = false

            override fun onQueryTextChange(newText: String?): Boolean {
                filtrar(newText ?: "")
                return true
            }
        })

        btnMenu.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }
    }

    private fun cargarAlumnos() {
        db.collection("alumnos")
            .get()
            .addOnSuccessListener { result ->

                listaAlumnos.clear()

                for (document in result) {
                    val alumno = document.toObject(Alumno::class.java)
                    listaAlumnos.add(alumno)
                }

                adapter.actualizarLista(listaAlumnos)
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
    }

    private fun filtrar(texto: String) {
        val listaFiltrada = listaAlumnos.filter {
            it.dni.lowercase().contains(texto.lowercase())
        }
        adapter.actualizarLista(listaFiltrada)
    }
}