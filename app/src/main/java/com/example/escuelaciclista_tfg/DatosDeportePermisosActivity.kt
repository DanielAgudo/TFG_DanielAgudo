package com.example.escuelaciclista_tfg

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class DatosDeportePermisosActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_datos_deporte_permisos)

        // RECIBIR TODOS LOS DATOS
        val nombre = intent.getStringExtra("nombre")
        val fecha = intent.getStringExtra("fecha")
        val dni = intent.getStringExtra("dni")
        val direccion = intent.getStringExtra("direccion")
        val telefono = intent.getStringExtra("telefono")

        val nombreTutor = intent.getStringExtra("nombreTutor")
        val dniTutor = intent.getStringExtra("dniTutor")
        val telefonoTutor = intent.getStringExtra("telefonoTutor")
        val emailTutor = intent.getStringExtra("emailTutor")

        val alergias = intent.getStringExtra("alergias")
        val condicion = intent.getStringExtra("condicion")
        val medicamentos = intent.getStringExtra("medicamentos")
        val telefonoEmergencia = intent.getStringExtra("telefonoEmergencia")

        // SPINNERS
        val spTipo = findViewById<Spinner>(R.id.spTipoBicicleta)
        val spTalla = findViewById<Spinner>(R.id.spTalla)
        val spModalidad = findViewById<Spinner>(R.id.spModalidad)

        val opcionesTipo = arrayOf("Bici de carretera", "Bici de montaña", "Ambas")
        val opcionesTalla = arrayOf("XS", "S", "M", "L")
        val opcionesModalidad = arrayOf("Carretera", "Montaña", "Ambas")

        spTipo.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, opcionesTipo)
        spTalla.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, opcionesTalla)
        spModalidad.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, opcionesModalidad)

        // CHECKBOXES
        val cbDatos = findViewById<CheckBox>(R.id.cbProteccionDatos)
        val cbFotos = findViewById<CheckBox>(R.id.cbFotosRedes)
        val cbMedica = findViewById<CheckBox>(R.id.cbAsistenciaMedica)
        val cbNormas = findViewById<CheckBox>(R.id.cbNormasEscuela)

        val btnGuardar = findViewById<Button>(R.id.btnGuardar)
        val btnBorrar = findViewById<Button>(R.id.btnBorrar)

        // BORRAR
        btnBorrar.setOnClickListener {
            spTipo.setSelection(0)
            spTalla.setSelection(0)
            spModalidad.setSelection(0)

            cbDatos.isChecked = false
            cbFotos.isChecked = false
            cbMedica.isChecked = false
            cbNormas.isChecked = false

            Toast.makeText(this, "Datos borrados", Toast.LENGTH_SHORT).show()
        }

        // GUARDAR FINAL
        btnGuardar.setOnClickListener {

            // VALIDAR PERMISOS
            if (!cbDatos.isChecked || !cbFotos.isChecked || !cbMedica.isChecked || !cbNormas.isChecked) {
                Toast.makeText(this, "Debes aceptar todos los permisos", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val alumno = hashMapOf(
                "nombre_apellidos" to nombre,
                "fecha_nacimiento" to fecha,
                "dni" to dni,
                "direccion" to direccion,
                "telefono" to telefono,

                "nombre_tutor" to nombreTutor,
                "dni_tutor" to dniTutor,
                "telefono_tutor" to telefonoTutor,
                "email_tutor" to emailTutor,

                "alergias" to alergias,
                "condicion_medica" to condicion,
                "medicamentos" to medicamentos,
                "telefono_emergencias" to telefonoEmergencia,

                "tipo_bicicleta" to spTipo.selectedItem.toString(),
                "talla" to spTalla.selectedItem.toString(),
                "modalidad" to spModalidad.selectedItem.toString()
            )

            // COMPROBAR DNI DUPLICADO (SEGURIDAD FINAL)
            db.collection("alumnos")
                .whereEqualTo("dni", dni)
                .get()
                .addOnSuccessListener { documents ->

                    if (!documents.isEmpty) {
                        Toast.makeText(this, "Ya existe un alumno con ese DNI", Toast.LENGTH_LONG).show()
                    } else {

                        // GUARDAR EN FIREBASE
                        db.collection("alumnos")
                            .add(alumno)
                            .addOnSuccessListener {

                                Toast.makeText(this, "Alumno guardado correctamente", Toast.LENGTH_SHORT).show()

                                val intent = Intent(this, ListaActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                            .addOnFailureListener {
                                Toast.makeText(this, "Error al guardar en Firebase", Toast.LENGTH_LONG).show()
                            }
                    }
                }
        }
    }
}