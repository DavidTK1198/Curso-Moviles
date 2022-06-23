package com.matricula.mobile.adapter
import android.content.Context
import android.view.LayoutInflater
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.matricula.mobile.R
import com.matricula.mobile.models.Ciclo
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class Oferta_CicloAdapter(val c: Context, val CicloList:ArrayList<Ciclo>): RecyclerView.Adapter<Oferta_CicloAdapter.CicloViewHolder>(), Filterable
{
    private var itemsList: ArrayList<Ciclo>? = null
    var CicloLiveData:MutableLiveData<Ciclo>? = null
    init {
        this.itemsList=CicloList
        CicloLiveData = MutableLiveData<Ciclo>()
    }
    inner class CicloViewHolder(val v:View):RecyclerView.ViewHolder(v){
        var name:TextView
        var mbNum:TextView
        lateinit var button:Button
        init {
            name = v.findViewById(R.id.mTitle_S)
            mbNum = v.findViewById(R.id.mSubTitle_S)
            button=v.findViewById(R.id.selecionar_btn)
            button.setOnClickListener{
                CicloLiveData!!.value=itemsList!!.get(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CicloViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v  = inflater.inflate(R.layout.list_item_button,parent,false)
        return CicloViewHolder(v)
    }

    override fun onBindViewHolder(holder: CicloViewHolder, position: Int) {
        val newList = itemsList?.get(position)
        holder.name.text = "Ciclo ${convertToRoman(newList!!.numero)}"
        holder.mbNum.text = newList!!.annio.toString()
    }

    override fun getItemCount(): Int {
        return  itemsList?.size!!
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    itemsList = CicloList
                } else {
                    val resultList = ArrayList<Ciclo>()
                    for (row in CicloList) {
                        if (row.numero.toString()!!.toLowerCase().contains(charSearch.toLowerCase()) ||
                            row.annio.toString()!!.toLowerCase().contains(charSearch)) {
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
                itemsList = results?.values as ArrayList<Ciclo>
                notifyDataSetChanged()
            }

        }
    }


    fun getCicloActual(): LiveData<Ciclo>?{
        if (CicloLiveData== null) {
            CicloLiveData = MutableLiveData<Ciclo>()
        }
        return CicloLiveData
    }

    private  fun convertToRoman(int: Int): String {
        when (int) {
            1 -> return "I"
            2 -> return "II"
            3 -> return "III"
            else->return ""
        }
    }
}