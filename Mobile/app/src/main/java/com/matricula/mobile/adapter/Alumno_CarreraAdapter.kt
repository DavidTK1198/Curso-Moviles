package com.matricula.mobile.adapter
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.matricula.mobile.R
import com.matricula.mobile.models.Carrera
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class Alumno_CarreraAdapter(val c: Context, val CarreraList:ArrayList<Carrera>): RecyclerView.Adapter<Alumno_CarreraAdapter.CarreraViewHolder>(), Filterable
{
    private var itemsList: ArrayList<Carrera>? = null
    var carreraLiveData:MutableLiveData<Carrera>? = null
    init {
        this.itemsList=CarreraList
        carreraLiveData = MutableLiveData<Carrera>()
    }
    inner class CarreraViewHolder(val v:View):RecyclerView.ViewHolder(v){
        var name:TextView
        var mbNum:TextView
        lateinit var button:Button
        init {
            name = v.findViewById(R.id.mTitle_S)
            mbNum = v.findViewById(R.id.mSubTitle_S)
            button=v.findViewById(R.id.selecionar_btn)
            button.setOnClickListener{
                carreraLiveData!!.value=itemsList!!.get(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarreraViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v  = inflater.inflate(R.layout.list_item_button,parent,false)
        return CarreraViewHolder(v)
    }

    override fun onBindViewHolder(holder: CarreraViewHolder, position: Int) {
        val newList = itemsList?.get(position)
        holder.name.text = newList!!.codigo
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
                    itemsList = CarreraList
                } else {
                    val resultList = ArrayList<Carrera>()
                    for (row in CarreraList) {
                        if (row.nombre!!.toLowerCase().contains(charSearch.toLowerCase()) ||
                            row.codigo!!.toLowerCase().contains(charSearch)) {
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
                itemsList = results?.values as ArrayList<Carrera>
                notifyDataSetChanged()
            }

        }
    }

    fun getCarreraActual(): LiveData<Carrera>?{
        if (carreraLiveData== null) {
            carreraLiveData = MutableLiveData<Carrera>()
        }
        return carreraLiveData
    }
}