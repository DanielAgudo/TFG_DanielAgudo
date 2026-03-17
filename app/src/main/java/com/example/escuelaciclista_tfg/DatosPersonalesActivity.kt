package com.example.escuelaciclista_tfg

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class DatosPersonalesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_datos_personales)

        // 🔹 CAMPOS
        val etNombre = findViewById<EditText>(R.id.etNombreApellidos)
        val etFecha = findViewById<EditText>(R.id.etFechaNacimiento)
        val etDni = findViewById<EditText>(R.id.etDNI)
        val etDireccion = findViewById<EditText>(R.id.etDireccion)
        val etTelefono = findViewById<EditText>(R.id.etTelefono)

        val btnGuardar = findViewById<Button>(R.id.btnGuardar)
        val btnBorrar = findViewById<Button>(R.id.btnBorrar)

        // OBTENER LOS TEXTVIEW
        val tvNombre = (etNombre.parent as LinearLayout).getChildAt(0) as TextView
        val tvFecha = (etFecha.parent as LinearLayout).getChildAt(0) as TextView
        val tvDni = (etDni.parent as LinearLayout).getChildAt(0) as TextView
        val tvDireccion = (etDireccion.parent as LinearLayout).getChildAt(0) as TextView

        // SELECTOR DE FECHA
        etFecha.setOnClickListener {
            val calendario = Calendar.getInstance()

            val datePicker = DatePickerDialog(
                this,
                { _, year, month, day ->
                    val fecha = "%02d/%02d/%04d".format(day, month + 1, year)
                    etFecha.setText(fecha)
                },
                calendario.get(Calendar.YEAR),
                calendario.get(Calendar.MONTH),
                calendario.get(Calendar.DAY_OF_MONTH)
            )

            datePicker.show()
        }

        // BOTÓN BORRAR
        btnBorrar.setOnClickListener {
            etNombre.text.clear()
            etFecha.text.clear()
            etDni.text.clear()
            etDireccion.text.clear()
            etTelefono.text.clear()

            resetColores(tvNombre, tvFecha, tvDni, tvDireccion)

            Toast.makeText(this, "Formulario borrado correctamente", Toast.LENGTH_SHORT).show()
        }

        // BOTÓN GUARDAR (AHORA ES SIGUIENTE)
        btnGuardar.setOnClickListener {

            resetColores(tvNombre, tvFecha, tvDni, tvDireccion)

            val nombre = etNombre.text.toString()
            val fecha = etFecha.text.toString()
            val dni = etDni.text.toString()
            val direccion = etDireccion.text.toString()
            val telefono = etTelefono.text.toString()

            val dniRegex = Regex("^[0-9]{8}[A-Za-z]$")

            var valido = true

            if (nombre.isEmpty()) {
                tvNombre.setTextColor(Color.RED)
                valido = false
            }

            if (fecha.isEmpty()) {
                tvFecha.setTextColor(Color.RED)
                valido = false
            }

            if (dni.isEmpty() || !dniRegex.matches(dni)) {
                tvDni.setTextColor(Color.RED)
                Toast.makeText(this, "DNI inválido (8 números + letra)", Toast.LENGTH_SHORT).show()
                valido = false
            }

            if (direccion.isEmpty()) {
                tvDireccion.setTextColor(Color.RED)
                valido = false
            }

            if (!valido) return@setOnClickListener

            // PASAR DATOS A SIGUIENTE PANTALLA
            val intent = Intent(this, DatosTutorActivity::class.java)

            intent.putExtra("nombre", nombre)
            intent.putExtra("fecha", fecha)
            intent.putExtra("dni", dni)
            intent.putExtra("direccion", direccion)
            intent.putExtra("telefono", telefono)

            startActivity(intent)
        }
    }

    private fun resetColores(vararg textViews: TextView) {
        textViews.forEach { it.setTextColor(Color.WHITE) }
    }
}