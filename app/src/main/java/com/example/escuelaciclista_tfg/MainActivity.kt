package com.example.escuelaciclista_tfg

import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.escuelaciclista_tfg.R

class MainActivity : AppCompatActivity() {

    // Declaramos variables para los campos del formulario
    private lateinit var etNombreApellidos: EditText
    private lateinit var etFechaNacimiento: EditText
    private lateinit var etDNI: EditText
    private lateinit var etDireccion: EditText
    private lateinit var etTelefono: EditText

    private lateinit var etNombreTutor: EditText
    private lateinit var etDNITutor: EditText
    private lateinit var etTelefonoTutor: EditText
    private lateinit var etEmailTutor: EditText

    private lateinit var etAlergias: EditText
    private lateinit var etCondicionMedica: EditText
    private lateinit var etMedicamentos: EditText
    private lateinit var etTelefonoEmergencias: EditText

    private lateinit var spTipoBicicleta: Spinner
    private lateinit var spTalla: Spinner
    private lateinit var spModalidad: Spinner

    private lateinit var cbProteccionDatos: CheckBox
    private lateinit var cbFotosRedes: CheckBox
    private lateinit var cbAsistenciaMedica: CheckBox
    private lateinit var cbNormasEscuela: CheckBox

    private lateinit var btnGuardar: Button
    private lateinit var btnBorrar: Button
    private lateinit var btnVerLista: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Mantener tu configuración original de insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicialización de campos
        etNombreApellidos = findViewById(R.id.etNombreApellidos)
        etFechaNacimiento = findViewById(R.id.etFechaNacimiento)
        etDNI = findViewById(R.id.etDNI)
        etDireccion = findViewById(R.id.etDireccion)
        etTelefono = findViewById(R.id.etTelefono)

        etNombreTutor = findViewById(R.id.etNombreTutor)
        etDNITutor = findViewById(R.id.etDNITutor)
        etTelefonoTutor = findViewById(R.id.etTelefonoTutor)
        etEmailTutor = findViewById(R.id.etEmailTutor)

        etAlergias = findViewById(R.id.etAlergias)
        etCondicionMedica = findViewById(R.id.etCondicionMedica)
        etMedicamentos = findViewById(R.id.etMedicamentos)
        etTelefonoEmergencias = findViewById(R.id.etTelefonoEmergencias)

        spTipoBicicleta = findViewById(R.id.spTipoBicicleta)
        spTalla = findViewById(R.id.spTalla)
        spModalidad = findViewById(R.id.spModalidad)

        cbProteccionDatos = findViewById(R.id.cbProteccionDatos)
        cbFotosRedes = findViewById(R.id.cbFotosRedes)
        cbAsistenciaMedica = findViewById(R.id.cbAsistenciaMedica)
        cbNormasEscuela = findViewById(R.id.cbNormasEscuela)

        btnGuardar = findViewById(R.id.btnGuardar)
        btnBorrar = findViewById(R.id.btnBorrar)
        btnVerLista = findViewById(R.id.btnVerLista)

        // Llenamos los Spinners
        setupSpinners()

        // Eventos de botones
        btnGuardar.setOnClickListener { guardarDatos() }
        btnBorrar.setOnClickListener { limpiarCampos() }
        btnVerLista.setOnClickListener {
            Toast.makeText(this, "Abrir lista de alumnos (a implementar)", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupSpinners() {
        val tiposBici = arrayOf("Bici de carretera", "Bici de montaña", "Ambas")
        val tallas = arrayOf("XS", "S", "M", "L")
        val modalidades = arrayOf("Carretera", "Montaña", "Ambas")

        spTipoBicicleta.adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, tiposBici)
        spTalla.adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, tallas)
        spModalidad.adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, modalidades)
    }

    private fun guardarDatos() {
        // Validaciones básicas
        val dniRegex = Regex("^[0-9]{8}[A-Za-z]\$")
        val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")

        val dniValido = etDNI.text.toString().matches(dniRegex)
        val dniTutorValido = etDNITutor.text.toString().matches(dniRegex)
        val emailValido = etEmailTutor.text.toString().matches(emailRegex)

        if (etNombreApellidos.text.isEmpty() || !dniValido) {
            Toast.makeText(this, "Introduce nombre y un DNI válido", Toast.LENGTH_SHORT).show()
            return
        }

        if (etNombreTutor.text.isEmpty() || !dniTutorValido || !emailValido) {
            Toast.makeText(this, "Datos del tutor incompletos o incorrectos", Toast.LENGTH_SHORT).show()
            return
        }

        if (!cbProteccionDatos.isChecked || !cbFotosRedes.isChecked ||
            !cbAsistenciaMedica.isChecked || !cbNormasEscuela.isChecked
        ) {
            Toast.makeText(this, "Debes aceptar todos los permisos", Toast.LENGTH_SHORT).show()
            return
        }

        Toast.makeText(this, "Datos guardados correctamente ✅", Toast.LENGTH_LONG).show()
    }

    private fun limpiarCampos() {
        listOf(
            etNombreApellidos, etFechaNacimiento, etDNI, etDireccion, etTelefono,
            etNombreTutor, etDNITutor, etTelefonoTutor, etEmailTutor,
            etAlergias, etCondicionMedica, etMedicamentos, etTelefonoEmergencias
        ).forEach { it.text.clear() }

        cbProteccionDatos.isChecked = false
        cbFotosRedes.isChecked = false
        cbAsistenciaMedica.isChecked = false
        cbNormasEscuela.isChecked = false

        spTipoBicicleta.setSelection(0)
        spTalla.setSelection(0)
        spModalidad.setSelection(0)

        Toast.makeText(this, "Formulario limpiado 🧹", Toast.LENGTH_SHORT).show()
    }
}
