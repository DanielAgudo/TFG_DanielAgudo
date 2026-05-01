package com.example.escuelaciclista_tfg

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DirectorAdapter(
    private val lista: List<Director>,
    private val onClick: (Director) -> Unit
) : RecyclerView.Adapter<DirectorAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNombre: TextView = view.findViewById(R.id.tvNombreDirector)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_director, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = lista.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val director = lista[position]

        holder.tvNombre.text = director.nombre

        holder.itemView.setOnClickListener {
            onClick(director)
        }
    }
}