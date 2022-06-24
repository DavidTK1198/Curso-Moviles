package com.matricula.mobile.adapter
import android.content.Context
import android.view.LayoutInflater
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.matricula.mobile.R
import com.matricula.mobile.models.Grupo
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class MatriculaAdapter(val c: Context, val GrupoList:ArrayList<Grupo>): RecyclerView.Adapter<MatriculaAdapter.GrupoViewHolder>(), Filterable
{
    private var itemsList: ArrayList<Grupo>? = null
    var GrupoLiveData:MutableLiveData<Grupo>? = null
    init {
        this.itemsList=GrupoList
        GrupoLiveData = MutableLiveData<Grupo>()
    }
    inner class GrupoViewHolder(val v:View):RecyclerView.ViewHolder(v){
        var name:TextView
        var mbNum:TextView
        lateinit var button:Button
        init {
            name = v.findViewById(R.id.mTitle_S)
            mbNum = v.findViewById(R.id.mSubTitle_S)
            button=v.findViewById(R.id.selecionar_btn)
            button.setOnClickListener{
                GrupoLiveData!!.value=itemsList!!.get(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GrupoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v  = inflater.inflate(R.layout.list_item_button,parent,false)
        return GrupoViewHolder(v)
    }

    override fun onBindViewHolder(holder: GrupoViewHolder, position: Int) {
        val newList = itemsList?.get(position)
        holder.name.text = newList!!.curso.nombre + "-"+newList.disponible+" espacios disponibles"
        holder.mbNum.text = newList!!.horario
    }

    override fun getItemCount(): Int {
        return  itemsList?.size!!
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    itemsList = GrupoList
                } else {
                    val resultList = ArrayList<Grupo>()
                    for (row in GrupoList) {
                        if (row.horario!!.toLowerCase().contains(charSearch.toLowerCase()) ||
                            row.curso.nombre!!.toLowerCase().contains(charSearch)) {
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
                itemsList = results?.values as ArrayList<Grupo>
                notifyDataSetChanged()
            }

        }
    }

    fun getGrupoActual(): LiveData<Grupo>?{
        if (GrupoLiveData== null) {
            GrupoLiveData = MutableLiveData<Grupo>()
        }
        return GrupoLiveData
    }
}