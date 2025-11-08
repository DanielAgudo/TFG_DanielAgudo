package com.example.escuelaciclista_tfg

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Mantener compatibilidad con tu estructura base
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val etNombreApellidos = findViewById<EditText>(R.id.etNombreApellidos)
        val etFechaNacimiento = findViewById<EditText>(R.id.etFechaNacimiento)
        val etDNI = findViewById<EditText>(R.id.etDNI)
        val etDireccion = findViewById<EditText>(R.id.etDireccion)
        val etTelefono = findViewById<EditText>(R.id.etTelefono)

        val etNombreTutor = findViewById<EditText>(R.id.etNombreTutor)
        val etDNITutor = findViewById<EditText>(R.id.etDNITutor)
        val etTelefonoTutor = findViewById<EditText>(R.id.etTelefonoTutor)
        val etEmailTutor = findViewById<EditText>(R.id.etEmailTutor)

        val etAlergias = findViewById<EditText>(R.id.etAlergias)
        val etCondicionMedica = findViewById<EditText>(R.id.etCondicionMedica)
        val etMedicamentos = findViewById<EditText>(R.id.etMedicamentos)
        val etTelefonoEmergencias = findViewById<EditText>(R.id.etTelefonoEmergencias)

        val spTipoBicicleta = findViewById<Spinner>(R.id.spTipoBicicleta)
        val spTalla = findViewById<Spinner>(R.id.spTalla)
        val spModalidad = findViewById<Spinner>(R.id.spModalidad)

        val cbProteccionDatos = findViewById<CheckBox>(R.id.cbProteccionDatos)
        val cbFotosRedes = findViewById<CheckBox>(R.id.cbFotosRedes)
        val cbAsistenciaMedica = findViewById<CheckBox>(R.id.cbAsistenciaMedica)
        val cbNormasEscuela = findViewById<CheckBox>(R.id.cbNormasEscuela)

        val btnGuardar = findViewById<Button>(R.id.btnGuardar)
        val btnBorrar = findViewById<Button>(R.id.btnBorrar)


        val opcionesTipo = arrayOf("Bici de carretera", "Bici de montaña", "Ambas")
        val opcionesTalla = arrayOf("XS", "S", "M", "L")
        val opcionesModalidad = arrayOf("Carretera", "Montaña", "Ambas")

        spTipoBicicleta.adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, opcionesTipo)
        spTalla.adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, opcionesTalla)
        spModalidad.adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, opcionesModalidad)

        // ----BOTÓN BORRAR----
        btnBorrar.setOnClickListener {
            val editTexts = listOf(
                etNombreApellidos, etFechaNacimiento, etDNI, etDireccion, etTelefono,
                etNombreTutor, etDNITutor, etTelefonoTutor, etEmailTutor,
                etAlergias, etCondicionMedica, etMedicamentos, etTelefonoEmergencias
            )
            editTexts.forEach { it.text.clear() }

            cbProteccionDatos.isChecked = false
            cbFotosRedes.isChecked = false
            cbAsistenciaMedica.isChecked = false
            cbNormasEscuela.isChecked = false

            spTipoBicicleta.setSelection(0)
            spTalla.setSelection(0)
            spModalidad.setSelection(0)

            Toast.makeText(this, "Formulario borrado correctamente", Toast.LENGTH_SHORT).show()
        }


        // ----BOTÓN GUARDAR----
        btnGuardar.setOnClickListener {
            // Validaciones de los campos
            val dniRegex = Regex("^[0-9]{8}[A-Za-z]\$")
            val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")

            when {
                etNombreApellidos.text.isBlank() -> showToast("Introduce el nombre y apellidos del alumno")
                etFechaNacimiento.text.isBlank() -> showToast("Introduce la fecha de nacimiento")
                etDNI.text.isBlank() -> showToast("Introduce el DNI del alumno")
                !dniRegex.matches(etDNI.text.toString()) -> showToast("El formato del DNI no es válido")
                etDireccion.text.isBlank() -> showToast("Introduce la dirección del alumno")
                etNombreTutor.text.isBlank() -> showToast("Introduce el nombre del tutor")
                etDNITutor.text.isBlank() -> showToast("Introduce el DNI del tutor")
                !dniRegex.matches(etDNITutor.text.toString()) -> showToast("El formato del DNI del tutor no es válido")
                etTelefonoTutor.text.isBlank() -> showToast("Introduce el teléfono del tutor")
                etEmailTutor.text.isBlank() -> showToast("Introduce el correo electrónico del tutor")
                !emailRegex.matches(etEmailTutor.text.toString()) -> showToast("El correo electrónico no es válido")
                !cbProteccionDatos.isChecked || !cbFotosRedes.isChecked ||
                        !cbAsistenciaMedica.isChecked || !cbNormasEscuela.isChecked ->
                    showToast("Debes aceptar todos los permisos antes de continuar")

                else -> {
                    Toast.makeText(this, "Datos guardados correctamente", Toast.LENGTH_SHORT).show()
                    // Navegar a la segunda pantalla (MainActivity2)
                    val intent = Intent(this, MainActivity2::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}
