package com.example.escuelaciclista_tfg

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class EditarAlumnoActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_alumno)

        // 🔥 RECIBIR DATOS
        val id = intent.getStringExtra("id")
        val nombre = intent.getStringExtra("nombre")
        val dni = intent.getStringExtra("dni")
        val telefono = intent.getStringExtra("telefonoTutor")
        val modalidad = intent.getStringExtra("modalidad")

        // CAMPOS
        val etNombre = findViewById<EditText>(R.id.etNombre)
        val etDni = findViewById<EditText>(R.id.etDni)
        val etTelefono = findViewById<EditText>(R.id.etTelefonoTutor)
        val spModalidad = findViewById<Spinner>(R.id.spModalidad)
        val btnGuardar = findViewById<Button>(R.id.btnGuardarCambios)

        // SPINNER
        val opciones = arrayOf("Carretera", "Montaña", "Ambas")
        val adapterSpinner = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, opciones)
        spModalidad.adapter = adapterSpinner

        //CARGAR DATOS
        etNombre.setText(nombre)
        etDni.setText(dni)
        etTelefono.setText(telefono)

        // Seleccionar modalidad actual
        val posicion = opciones.indexOf(modalidad)
        if (posicion >= 0) {
            spModalidad.setSelection(posicion)
        }

        //BOTÓN GUARDAR
        btnGuardar.setOnClickListener {

            val nuevoNombre = etNombre.text.toString()
            val nuevoDni = etDni.text.toString()
            val nuevoTelefono = etTelefono.text.toString()
            val nuevaModalidad = spModalidad.selectedItem.toString()

            val dniRegex = Regex("^[0-9]{8}[A-Za-z]$")

            // VALIDACIONES
            if (nuevoNombre.isBlank()) {
                etNombre.error = "Campo obligatorio"
                return@setOnClickListener
            }

            if (!dniRegex.matches(nuevoDni)) {
                etDni.error = "DNI inválido"
                return@setOnClickListener
            }

            //MAPA ACTUALIZADO
            val alumnoActualizado = hashMapOf(
                "nombre_apellidos" to nuevoNombre,
                "dni" to nuevoDni,
                "telefono_tutor" to nuevoTelefono,
                "modalidad" to nuevaModalidad
            )

            //ACTUALIZAR FIREBASE
            db.collection("alumnos")
                .document(id!!)
                .update(alumnoActualizado as Map<String, Any>)
                .addOnSuccessListener {
                    Toast.makeText(this, "Alumno actualizado", Toast.LENGTH_SHORT).show()
                    finish() // volver atrás
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error al actualizar", Toast.LENGTH_LONG).show()
                }
        }
    }
}