package com.matricula.sqllite.util

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.matricula.sqllite.R
import com.matricula.sqllite.logicanegocio.Curso
import com.matricula.sqllite.logicanegocio.Estudiante

class RecyclerView_Adapter_Curso(private var items: ArrayList<Curso>): RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {

    var itemsList: ArrayList<Curso>? = null
    private  lateinit var  mlistener: onItemClickListener
    lateinit var mcontext: Context
     interface onItemClickListener{
         fun onItemClick(position: Int){

         }
     }
    fun setOnItemClickListener(listener: onItemClickListener){
        mlistener=listener
    }
    class PersonHolder(itemView: View,listener: onItemClickListener) : RecyclerView.ViewHolder(itemView){
            init {
                itemView.setOnClickListener{
                    listener.onItemClick(adapterPosition )
                }
            }

    }

    init {
        this.itemsList = items


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val personListView = LayoutInflater.from(parent.context).inflate(R.layout.template_estudiantes, parent, false)
        val sch = PersonHolder(personListView,mlistener)
        mcontext = parent.context
        return sch
    }

    override fun getItemCount(): Int {
        return itemsList?.size!!
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val item = itemsList?.get(position)

        holder.itemView.findViewById<TextView>(R.id.ID_Es)?.text = item?.codigo.toString()
        holder.itemView.findViewById<TextView>(R.id.tvNombre)?.text = item?.nombre


    }


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    itemsList = items
                } else {
                    val resultList = ArrayList<Curso>()
                    for (row in items) {
                        if (row.nombre.toLowerCase().contains(charSearch.toLowerCase())) {
                            resultList.add(row)
                        }
                    }
                    itemsList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = itemsList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                itemsList = results?.values as ArrayList<Curso>
                notifyDataSetChanged()
            }

        }
    }


}


