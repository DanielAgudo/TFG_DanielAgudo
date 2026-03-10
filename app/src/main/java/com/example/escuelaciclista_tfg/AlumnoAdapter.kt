package com.example.escuelaciclista_tfg

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
class AlumnoAdapter(private var lista: List<Alumno>) :
    RecyclerView.Adapter<AlumnoAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombre: TextView = view.findViewById(R.id.tvNombre)
        val dni: TextView = view.findViewById(R.id.tvDni)
        val telefonoTutor: TextView = view.findViewById(R.id.tvTelefonoTutor)
        val modalidad: TextView = view.findViewById(R.id.tvModalidad)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_alumno, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = lista.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val alumno = lista[position]
        holder.nombre.text = alumno.nombre_apellidos
        holder.dni.text = "DNI: ${alumno.dni}"
        holder.telefonoTutor.text = "Telefono Tutor: ${alumno.telefono_tutor}"
        holder.modalidad.text = "Modalidad: ${alumno.modalidad}"
    }

    fun actualizarLista(nuevaLista: List<Alumno>) {
        lista = nuevaLista
        notifyDataSetChanged()
    }
}