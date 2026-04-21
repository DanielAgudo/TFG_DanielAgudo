package com.example.escuelaciclista_tfg

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val btnNuevoAlumno = findViewById<Button>(R.id.btnNuevoAlumno)
        val btnListaAlumnos = findViewById<Button>(R.id.btnListaAlumnos)
        val btnMisAlumnos = findViewById<Button>(R.id.btnMisAlumnos)

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
    }
}