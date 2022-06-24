package com.matricula.mobile.adapter
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.matricula.mobile.R
import com.matricula.mobile.models.Inscripcion
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class DesmatricularAdapter(val c: Context, val InscripcionList:ArrayList<Inscripcion>): RecyclerView.Adapter<DesmatricularAdapter.InscripcionViewHolder>(), Filterable
{
    private var itemsList: ArrayList<Inscripcion>? = null
    var InscripcionLiveData:MutableLiveData<Inscripcion>? = null
    init {
        this.itemsList=InscripcionList
        InscripcionLiveData = MutableLiveData<Inscripcion>()
    }
    inner class InscripcionViewHolder(val v:View):RecyclerView.ViewHolder(v){
        var name:TextView
        var mbNum:TextView
        lateinit var button:Button
        init {
            name = v.findViewById(R.id.mTitle_S)
            mbNum = v.findViewById(R.id.mSubTitle_S)
            button=v.findViewById(R.id.selecionar_btn)
            button.setOnClickListener{
                InscripcionLiveData!!.value=itemsList!!.get(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InscripcionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v  = inflater.inflate(R.layout.list_item_button,parent,false)
        return InscripcionViewHolder(v)
    }

    override fun onBindViewHolder(holder: InscripcionViewHolder, position: Int) {
        val newList = itemsList?.get(position)
        holder.name.text = newList!!.grupo.curso.nombre
        holder.mbNum.text = newList!!.grupo.curso.codigo
    }

    override fun getItemCount(): Int {
        return  itemsList?.size!!
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
                        if (row.grupo.curso.codigo!!.toLowerCase().contains(charSearch.toLowerCase()) ||
                            row.grupo.curso.nombre!!.toLowerCase().contains(charSearch)) {
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

    fun getInscripcionActual(): LiveData<Inscripcion>?{
        if (InscripcionLiveData== null) {
            InscripcionLiveData = MutableLiveData<Inscripcion>()
        }
        return InscripcionLiveData
    }
}