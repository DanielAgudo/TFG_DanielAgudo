package com.example.escuelaciclista_tfg

import android.app.AlertDialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class AlumnoAdapter(private var lista: MutableList<Alumno>) :
    RecyclerView.Adapter<AlumnoAdapter.ViewHolder>() {

    private val db = FirebaseFirestore.getInstance()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombre: TextView = view.findViewById(R.id.tvNombre)
        val dni: TextView = view.findViewById(R.id.tvDni)
        val telefonoTutor: TextView = view.findViewById(R.id.tvTelefonoTutor)
        val modalidad: TextView = view.findViewById(R.id.tvModalidad)

        val btnEliminar: Button = view.findViewById(R.id.btnEliminar)
        val btnEditar: Button = view.findViewById(R.id.btnEditar) // 🔥 NUEVO
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
        holder.telefonoTutor.text = "Teléfono Tutor: ${alumno.telefono_tutor}"
        holder.modalidad.text = "Modalidad: ${alumno.modalidad}"

        //BOTÓN BORRAR
        holder.btnEliminar.setOnClickListener {

            AlertDialog.Builder(holder.itemView.context)
                .setTitle("Eliminar alumno")
                .setMessage("¿Seguro que quieres borrar este alumno?")
                .setPositiveButton("Sí") { _, _ ->

                    db.collection("alumnos")
                        .document(alumno.id)
                        .delete()
                        .addOnSuccessListener {

                            lista.removeAt(position)
                            notifyItemRemoved(position)
                            notifyItemRangeChanged(position, lista.size)
                        }
                }
                .setNegativeButton("No", null)
                .show()
        }

        //BOTÓN EDITAR
        holder.btnEditar.setOnClickListener {

            val context = holder.itemView.context
            val intent = Intent(context, EditarAlumnoActivity::class.java)

            //PASAR DATOS
            intent.putExtra("id", alumno.id)
            intent.putExtra("nombre", alumno.nombre_apellidos)
            intent.putExtra("dni", alumno.dni)
            intent.putExtra("telefonoTutor", alumno.telefono_tutor)
            intent.putExtra("modalidad", alumno.modalidad)

            context.startActivity(intent)
        }
    }

    fun actualizarLista(nuevaLista: List<Alumno>) {
        lista = nuevaLista.toMutableList()
        notifyDataSetChanged()
    }
}