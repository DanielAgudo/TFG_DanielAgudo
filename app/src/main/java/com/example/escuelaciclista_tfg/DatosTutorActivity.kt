package com.example.escuelaciclista_tfg

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class DatosTutorActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_datos_tutor)

        //DATOS RECIBIDOS
        val dniAlumno = intent.getStringExtra("dni")

        val nombre = intent.getStringExtra("nombre")
        val fecha = intent.getStringExtra("fecha")
        val direccion = intent.getStringExtra("direccion")
        val telefono = intent.getStringExtra("telefono")

        //CAMPOS
        val etNombreTutor = findViewById<EditText>(R.id.etNombreTutor)
        val etDniTutor = findViewById<EditText>(R.id.etDNITutor)
        val etTelefonoTutor = findViewById<EditText>(R.id.etTelefonoTutor)
        val etEmailTutor = findViewById<EditText>(R.id.etEmailTutor)

        val btnGuardar = findViewById<Button>(R.id.btnGuardar)
        val btnBorrar = findViewById<Button>(R.id.btnBorrar)

        //AUTO LETRA DNI
        etDniTutor.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val texto = s.toString()

                if (texto.length == 8 && texto.all { it.isDigit() }) {
                    val letra = calcularLetraDNI(texto)
                    etDniTutor.setText(texto + letra)
                    etDniTutor.setSelection(etDniTutor.text.length)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        //BORRAR
        btnBorrar.setOnClickListener {
            etNombreTutor.text.clear()
            etDniTutor.text.clear()
            etTelefonoTutor.text.clear()
            etEmailTutor.text.clear()

            limpiarErrores(etNombreTutor, etDniTutor, etTelefonoTutor, etEmailTutor)

            Toast.makeText(this, "Formulario borrado", Toast.LENGTH_SHORT).show()
        }

        //GUARDAR
        btnGuardar.setOnClickListener {

            limpiarErrores(etNombreTutor, etDniTutor, etTelefonoTutor, etEmailTutor)

            val nombreTutor = etNombreTutor.text.toString()
            val dniTutor = etDniTutor.text.toString()
            val telefonoTutor = etTelefonoTutor.text.toString()
            val emailTutor = etEmailTutor.text.toString()

            val dniRegex = Regex("^[0-9]{8}[A-Za-z]$")
            val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")

            var valido = true

            // VALIDACIONES
            if (nombreTutor.isBlank()) {
                etNombreTutor.error = "Campo obligatorio"
                valido = false
            }

            if (!dniRegex.matches(dniTutor)) {
                etDniTutor.error = "DNI inválido"
                valido = false
            }

            if (telefonoTutor.isBlank()) {
                etTelefonoTutor.error = "Campo obligatorio"
                valido = false
            }

            if (!emailRegex.matches(emailTutor)) {
                etEmailTutor.error = "Email inválido"
                valido = false
            }

            // DNI IGUAL AL ALUMNO
            if (dniTutor == dniAlumno) {
                etDniTutor.error = "No puede ser igual al DNI del alumno"
                valido = false
            }

            if (!valido) return@setOnClickListener

            // COMPROBAR DUPLICADO EN FIREBASE
            db.collection("alumnos")
                .whereEqualTo("dni_tutor", dniTutor)
                .get()
                .addOnSuccessListener { documentos ->

                    if (!documentos.isEmpty) {
                        etDniTutor.error = "Este DNI ya está registrado"
                    } else {

                        val intent = Intent(this, DatosMedicosActivity::class.java)

                        intent.putExtra("nombre", nombre)
                        intent.putExtra("fecha", fecha)
                        intent.putExtra("dni", dniAlumno)
                        intent.putExtra("direccion", direccion)
                        intent.putExtra("telefono", telefono)

                        intent.putExtra("nombreTutor", nombreTutor)
                        intent.putExtra("dniTutor", dniTutor)
                        intent.putExtra("telefonoTutor", telefonoTutor)
                        intent.putExtra("emailTutor", emailTutor)

                        startActivity(intent)
                    }
                }
        }
    }

    private fun limpiarErrores(vararg editTexts: EditText) {
        editTexts.forEach { it.error = null }
    }

    // LETRA DNI
    private fun calcularLetraDNI(dni: String): String {
        val letras = "TRWAGMYFPDXBNJZSQVHLCKE"
        val numero = dni.toInt()
        return letras[numero % 23].toString()
    }
}