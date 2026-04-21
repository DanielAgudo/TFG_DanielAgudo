package com.example.escuelaciclista_tfg

data class Alumno(
    var id: String = "",
    var nombre_apellidos: String = "",
    var fecha_nacimiento: String = "",
    var dni: String = "",
    var direccion: String = "",
    var telefono: String = "",
    var nombre_tutor: String = "",
    var dni_tutor: String = "",
    var telefono_tutor: String = "",
    var email_tutor: String = "",
    var alergias: String = "",
    var condicion_medica: String = "",
    var medicamentos: String = "",
    var telefono_emergencias: String = "",
    var tipo_bicicleta: String = "",
    var talla: String = "",
    var modalidad: String = "",

    var usuarioId: String = "",
    var usuarioEmail: String = ""
)