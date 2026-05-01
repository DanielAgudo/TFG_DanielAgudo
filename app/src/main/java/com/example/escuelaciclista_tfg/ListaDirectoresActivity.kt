package com.example.escuelaciclista_tfg

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class ListaDirectoresActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DirectorAdapter

    private val lista = mutableListOf<Director>()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_lista_directores)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recyclerView = findViewById(R.id.recyclerDirectores)
        val btnVolver = findViewById<Button>(R.id.btnVolver)

        adapter = DirectorAdapter(lista) { director ->

            val intent = Intent(this, AlumnosDirectorActivity::class.java)
            intent.putExtra("uid", director.uid)
            startActivity(intent)
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // CARGAR DIRECTORES
        cargarDirectores()

        // BOTÓN VOLVER
        btnVolver.setOnClickListener {
            finish()
        }
    }

    private fun cargarDirectores() {

        db.collection("directores")
            .get()
            .addOnSuccessListener { result ->

                lista.clear()

                for (doc in result) {

                    val director = doc.toObject(Director::class.java)

                    // IMPORTANTE: asignar UID correctamente
                    director.uid = doc.id

                    lista.add(director)
                }

                adapter.notifyDataSetChanged()
            }
    }
}