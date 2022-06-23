package com.matricula.mobile.adapter
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.matricula.mobile.R
import com.matricula.mobile.models.Profesor
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class Grupo_ProfesorAdapter(val c: Context, val ProfesorList:ArrayList<Profesor>): RecyclerView.Adapter<Grupo_ProfesorAdapter.ProfesorViewHolder>(), Filterable
{
    private var itemsList: ArrayList<Profesor>? = null
    var ProfesorLiveData:MutableLiveData<Profesor>? = null
    init {
        this.itemsList=ProfesorList
        ProfesorLiveData = MutableLiveData<Profesor>()
    }
    inner class ProfesorViewHolder(val v:View):RecyclerView.ViewHolder(v){
        var name:TextView
        var mbNum:TextView
        lateinit var button:Button
        init {
            name = v.findViewById(R.id.mTitle_S)
            mbNum = v.findViewById(R.id.mSubTitle_S)
            button=v.findViewById(R.id.selecionar_btn)
            button.setOnClickListener{
                ProfesorLiveData!!.value=itemsList!!.get(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfesorViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v  = inflater.inflate(R.layout.list_item_button,parent,false)
        return ProfesorViewHolder(v)
    }

    override fun onBindViewHolder(holder: ProfesorViewHolder, position: Int) {
        val newList = itemsList?.get(position)
        holder.name.text = newList!!.cedula
        holder.mbNum.text = newList!!.nombre
    }

    override fun getItemCount(): Int {
        return  itemsList?.size!!
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    itemsList = ProfesorList
                } else {
                    val resultList = ArrayList<Profesor>()
                    for (row in ProfesorList) {
                        if (row.nombre!!.toLowerCase().contains(charSearch.toLowerCase()) ||
                            row.cedula!!.toLowerCase().contains(charSearch)) {
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
                itemsList = results?.values as ArrayList<Profesor>
                notifyDataSetChanged()
            }

        }
    }

    fun getProfesorActual(): LiveData<Profesor>?{
        if (ProfesorLiveData== null) {
            ProfesorLiveData = MutableLiveData<Profesor>()
        }
        return ProfesorLiveData
    }
}