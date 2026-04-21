package com.example.escuelaciclista_tfg

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class miLista : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var adapter: AlumnoAdapter
    private lateinit var btnMenu: Button

    private var listaAlumnos = mutableListOf<Alumno>()

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_mi_lista)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )

            insets
        }

        recyclerView = findViewById(R.id.recyclerAlumnos)
        searchView = findViewById(R.id.searchView)
        btnMenu = findViewById(R.id.btnMenu)

        adapter = AlumnoAdapter(listaAlumnos)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        cargarMisAlumnos()

        // BUSCADOR
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?) = false

            override fun onQueryTextChange(newText: String?): Boolean {
                filtrar(newText ?: "")
                return true
            }
        })

        //BOTÓN MENÚ
        btnMenu.setOnClickListener {

            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onResume() {
        super.onResume()

        cargarMisAlumnos()
    }

    // CARGAR MIS ALUMNOS
    private fun cargarMisAlumnos() {

        val uid = FirebaseAuth.getInstance().currentUser?.uid

        db.collection("alumnos")
            .whereEqualTo("usuarioId", uid)
            .get()
            .addOnSuccessListener { result ->

                listaAlumnos.clear()

                for (document in result) {

                    val alumno = document.toObject(Alumno::class.java)

                    alumno.id = document.id

                    listaAlumnos.add(alumno)
                }

                adapter.actualizarLista(listaAlumnos)
            }

            .addOnFailureListener { e ->

                Toast.makeText(
                    this,
                    "Error al cargar alumnos: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
    }

    // FILTRAR DNI
    private fun filtrar(texto: String) {

        val listaFiltrada = listaAlumnos.filter {

            it.dni.lowercase().contains(texto.lowercase())
        }

        adapter.actualizarLista(listaFiltrada)
    }
}