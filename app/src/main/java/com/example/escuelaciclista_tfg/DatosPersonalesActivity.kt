package com.example.escuelaciclista_tfg

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class DatosPersonalesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_datos_personales)

        // CAMPOS
        val etNombre = findViewById<EditText>(R.id.etNombreApellidos)
        val etFecha = findViewById<EditText>(R.id.etFechaNacimiento)
        val etDNI = findViewById<EditText>(R.id.etDNI)
        val etDireccion = findViewById<EditText>(R.id.etDireccion)
        val etTelefono = findViewById<EditText>(R.id.etTelefono)

        val btnGuardar = findViewById<Button>(R.id.btnGuardar)
        val btnBorrar = findViewById<Button>(R.id.btnBorrar)

        // CALENDARIO
        etFecha.setOnClickListener {

            val calendario = Calendar.getInstance()

            val year = calendario.get(Calendar.YEAR)
            val month = calendario.get(Calendar.MONTH)
            val day = calendario.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDay ->

                    val fecha = String.format(
                        "%02d/%02d/%04d",
                        selectedDay,
                        selectedMonth + 1,
                        selectedYear
                    )

                    etFecha.setText(fecha)

                },
                year,
                month,
                day
            )

            // ❗ NO permitir fechas futuras
            datePicker.datePicker.maxDate = System.currentTimeMillis()

            datePicker.show()
        }

        // AUTO LETRA DNI
        etDNI.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val texto = s.toString()

                if (texto.length == 8 && texto.all { it.isDigit() }) {
                    val letra = calcularLetraDNI(texto)
                    etDNI.setText(texto + letra)
                    etDNI.setSelection(etDNI.text.length)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // BORRAR
        btnBorrar.setOnClickListener {
            etNombre.text.clear()
            etFecha.text.clear()
            etDNI.text.clear()
            etDireccion.text.clear()
            etTelefono.text.clear()

            limpiarErrores(etNombre, etFecha, etDNI, etDireccion)

            Toast.makeText(this, "Formulario borrado", Toast.LENGTH_SHORT).show()
        }

        // SIGUIENTE
        btnGuardar.setOnClickListener {

            limpiarErrores(etNombre, etFecha, etDNI, etDireccion)

            val nombre = etNombre.text.toString()
            val fecha = etFecha.text.toString()
            val dni = etDNI.text.toString()
            val direccion = etDireccion.text.toString()
            val telefono = etTelefono.text.toString()

            val dniRegex = Regex("^[0-9]{8}[A-Za-z]$")

            var valido = true

            if (nombre.isBlank()) {
                etNombre.error = "Campo obligatorio"
                valido = false
            }

            if (fecha.isBlank()) {
                etFecha.error = "Campo obligatorio"
                valido = false
            }

            if (!dniRegex.matches(dni)) {
                etDNI.error = "DNI inválido"
                valido = false
            }

            if (direccion.isBlank()) {
                etDireccion.error = "Campo obligatorio"
                valido = false
            }

            if (!valido) return@setOnClickListener

            val intent = Intent(this, DatosTutorActivity::class.java)
            intent.putExtra("nombre", nombre)
            intent.putExtra("fecha", fecha)
            intent.putExtra("dni", dni)
            intent.putExtra("direccion", direccion)
            intent.putExtra("telefono", telefono)

            startActivity(intent)
        }
    }

    private fun limpiarErrores(vararg editTexts: EditText) {
        editTexts.forEach { it.error = null }
    }

    private fun calcularLetraDNI(dni: String): String {
        val letras = "TRWAGMYFPDXBNJZSQVHLCKE"
        val numero = dni.toInt()
        return letras[numero % 23].toString()
    }
}