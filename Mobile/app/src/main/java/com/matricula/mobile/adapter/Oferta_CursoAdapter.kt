package com.matricula.mobile.adapter
import android.content.Context
import android.view.LayoutInflater
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.matricula.mobile.R
import com.matricula.mobile.models.Curso
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class Oferta_CursoAdapter(val c: Context, val CursoList:ArrayList<Curso>): RecyclerView.Adapter<Oferta_CursoAdapter.CursoViewHolder>(), Filterable
{
    private var itemsList: ArrayList<Curso>? = null
    var CursoLiveData:MutableLiveData<Curso>? = null
    init {
        this.itemsList=CursoList
        CursoLiveData = MutableLiveData<Curso>()
    }
    inner class CursoViewHolder(val v:View):RecyclerView.ViewHolder(v){
        var name:TextView
        var mbNum:TextView
        lateinit var button:Button
        init {
            name = v.findViewById(R.id.mTitle_S)
            mbNum = v.findViewById(R.id.mSubTitle_S)
            button=v.findViewById(R.id.selecionar_btn)
            button.setOnClickListener{
                CursoLiveData!!.value=itemsList!!.get(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CursoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v  = inflater.inflate(R.layout.list_item_button,parent,false)
        return CursoViewHolder(v)
    }

    override fun onBindViewHolder(holder: CursoViewHolder, position: Int) {
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
                    itemsList = CursoList
                } else {
                    val resultList = ArrayList<Curso>()
                    for (row in CursoList) {
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
                itemsList = results?.values as ArrayList<Curso>
                notifyDataSetChanged()
            }

        }
    }

    fun getCursoActual(): LiveData<Curso>?{
        if (CursoLiveData== null) {
            CursoLiveData = MutableLiveData<Curso>()
        }
        return CursoLiveData
    }
}