package com.example.escuelaciclista_tfg

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class DatosMedicosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_datos_medicos)

        // RECIBIR TODOS LOS DATOS ANTERIORES
        val nombre = intent.getStringExtra("nombre")
        val fecha = intent.getStringExtra("fecha")
        val dni = intent.getStringExtra("dni")
        val direccion = intent.getStringExtra("direccion")
        val telefono = intent.getStringExtra("telefono")

        val nombreTutor = intent.getStringExtra("nombreTutor")
        val dniTutor = intent.getStringExtra("dniTutor")
        val telefonoTutor = intent.getStringExtra("telefonoTutor")
        val emailTutor = intent.getStringExtra("emailTutor")

        // CAMPOS MÉDICOS
        val etAlergias = findViewById<EditText>(R.id.etAlergias)
        val etCondicion = findViewById<EditText>(R.id.etCondicionMedica)
        val etMedicamentos = findViewById<EditText>(R.id.etMedicamentos)
        val etTelefonoEmergencia = findViewById<EditText>(R.id.etTelefonoEmergencias)

        val btnGuardar = findViewById<Button>(R.id.btnGuardar)
        val btnBorrar = findViewById<Button>(R.id.btnBorrar)

        // BOTÓN BORRAR
        btnBorrar.setOnClickListener {
            etAlergias.text.clear()
            etCondicion.text.clear()
            etMedicamentos.text.clear()
            etTelefonoEmergencia.text.clear()

            Toast.makeText(this, "Datos médicos borrados", Toast.LENGTH_SHORT).show()
        }

        // BOTÓN GUARDAR
        btnGuardar.setOnClickListener {

            val alergias = etAlergias.text.toString()
            val condicion = etCondicion.text.toString()
            val medicamentos = etMedicamentos.text.toString()
            val telefonoEmergencia = etTelefonoEmergencia.text.toString()

            val intent = Intent(this, DatosDeportePermisosActivity::class.java)

            //ALUMNO
            intent.putExtra("nombre", nombre)
            intent.putExtra("fecha", fecha)
            intent.putExtra("dni", dni)
            intent.putExtra("direccion", direccion)
            intent.putExtra("telefono", telefono)

            //TUTOR
            intent.putExtra("nombreTutor", nombreTutor)
            intent.putExtra("dniTutor", dniTutor)
            intent.putExtra("telefonoTutor", telefonoTutor)
            intent.putExtra("emailTutor", emailTutor)

            //MÉDICOS
            intent.putExtra("alergias", alergias)
            intent.putExtra("condicion", condicion)
            intent.putExtra("medicamentos", medicamentos)
            intent.putExtra("telefonoEmergencia", telefonoEmergencia)

            startActivity(intent)
        }
    }
}