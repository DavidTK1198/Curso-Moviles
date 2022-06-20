package com.matricula.mobile.adapter
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.matricula.mobile.R
import com.matricula.mobile.models.Inscripcion

class InscripcionAdapter(val c: Context, val InscripcionList:ArrayList<Inscripcion>): RecyclerView.Adapter<InscripcionAdapter.InscripcionViewHolder>(),
    Filterable {
    private var itemsList: ArrayList<Inscripcion>? = null

    init {
        this.itemsList = InscripcionList
    }

    inner class InscripcionViewHolder(val v: View) : RecyclerView.ViewHolder(v) {
        var name: TextView
        var mbNum: TextView

        init {
            name = v.findViewById(R.id.mTitle)
            mbNum = v.findViewById(R.id.mSubTitle)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InscripcionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.item_inscripcion, parent, false)
        return InscripcionViewHolder(v)
    }

    override fun onBindViewHolder(holder: InscripcionViewHolder, position: Int) {
        val newList = itemsList?.get(position)
        holder.name.text = newList!!.grupo.curso.nombre
        if (newList!!.nota == 0)
            holder.mbNum.text = "Calificación sin definir "
        else
            holder.mbNum.text = newList!!.nota.toString()
    }


    override fun getItemCount(): Int {
        return itemsList?.size!!
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    itemsList = InscripcionList
                } else {
                    val resultList = ArrayList<Inscripcion>()
                    for (row in InscripcionList) {
                        if (row.grupo.curso.nombre!!.toLowerCase().contains(charSearch.toLowerCase())) {
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
                itemsList = results?.values as ArrayList<Inscripcion>
                notifyDataSetChanged()
            }

        }
    }

}


