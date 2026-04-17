package com.example.escuelaciclista_tfg

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import android.text.Editable
import android.text.TextWatcher

class EditarAlumnoActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_alumno)

        val id = intent.getStringExtra("id")
        val nombre = intent.getStringExtra("nombre")
        val dniOriginal = intent.getStringExtra("dni") // 🔥 IMPORTANTE
        val telefono = intent.getStringExtra("telefonoTutor")
        val modalidad = intent.getStringExtra("modalidad")

        val etNombre = findViewById<EditText>(R.id.etNombre)
        val etDni = findViewById<EditText>(R.id.etDni)
        val etTelefono = findViewById<EditText>(R.id.etTelefonoTutor)
        val spModalidad = findViewById<Spinner>(R.id.spModalidad)
        val btnGuardar = findViewById<Button>(R.id.btnGuardarCambios)

        // SPINNER
        val opciones = arrayOf("Carretera", "Montaña", "Ambas")
        val adapterSpinner = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, opciones)
        spModalidad.adapter = adapterSpinner

        // CARGAR DATOS
        etNombre.setText(nombre)
        etDni.setText(dniOriginal)
        etTelefono.setText(telefono)

        val posicion = opciones.indexOf(modalidad)
        if (posicion >= 0) {
            spModalidad.setSelection(posicion)
        }

        // AUTO LETRA DNI
        var isEditing = false

        etDni.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {

                if (isEditing) return

                val texto = s.toString()

                if (texto.length == 8 && texto.all { it.isDigit() }) {

                    isEditing = true

                    val letra = calcularLetraDni(texto)
                    val dniCompleto = texto + letra

                    etDni.setText(dniCompleto)
                    etDni.setSelection(dniCompleto.length)

                    isEditing = false
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // BOTÓN GUARDAR
        btnGuardar.setOnClickListener {

            val nuevoNombre = etNombre.text.toString()
            val textoDni = etDni.text.toString()
            val nuevoTelefono = etTelefono.text.toString()
            val nuevaModalidad = spModalidad.selectedItem.toString()

            // VALIDACIONES
            if (nuevoNombre.isBlank()) {
                etNombre.error = "Campo obligatorio"
                return@setOnClickListener
            }

            if (textoDni.length != 9) {
                etDni.error = "DNI incompleto"
                return@setOnClickListener
            }

            val numeros = textoDni.substring(0, 8)
            val letra = calcularLetraDni(numeros)
            val nuevoDni = numeros + letra

            // COMPROBAR DNI DUPLICADO
            db.collection("alumnos")
                .whereEqualTo("dni", nuevoDni)
                .get()
                .addOnSuccessListener { documentos ->

                    var duplicado = false

                    for (doc in documentos) {
                        if (doc.id != id) {
                            duplicado = true
                            break
                        }
                    }

                    if (duplicado) {
                        etDni.error = "Este DNI ya pertenece a otro alumno"
                        Toast.makeText(this, "DNI duplicado", Toast.LENGTH_LONG).show()
                    } else {

                        // ACTUALIZAR
                        val alumnoActualizado = hashMapOf(
                            "nombre_apellidos" to nuevoNombre,
                            "dni" to nuevoDni,
                            "telefono_tutor" to nuevoTelefono,
                            "modalidad" to nuevaModalidad
                        )

                        db.collection("alumnos")
                            .document(id!!)
                            .update(alumnoActualizado as Map<String, Any>)
                            .addOnSuccessListener {
                                Toast.makeText(this, "Alumno actualizado", Toast.LENGTH_SHORT).show()
                                finish()
                            }
                            .addOnFailureListener {
                                Toast.makeText(this, "Error al actualizar", Toast.LENGTH_LONG).show()
                            }
                    }
                }
        }
    }

    // FUNCIÓN LETRA DNI
    private fun calcularLetraDni(numero: String): String {
        val letras = "TRWAGMYFPDXBNJZSQVHLCKE"
        val num = numero.toInt()
        return letras[num % 23].toString()
    }
}