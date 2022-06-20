package com.matricula.mobile.adapter
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.matricula.mobile.R
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.matricula.mobile.models.Ciclo


class CicloAdapter(val c: Context, val CicloList:ArrayList<Ciclo>): RecyclerView.Adapter<CicloAdapter.CursoViewHolder>(), Filterable
{
    private var itemsList: ArrayList<Ciclo>? = null
    var cicloLiveData:MutableLiveData<Ciclo>? = null
    private var state:MutableLiveData<Boolean>?=null



    init {
        this.itemsList=CicloList
        cicloLiveData = MutableLiveData<Ciclo>()
    }
    inner class CursoViewHolder(val v:View):RecyclerView.ViewHolder(v){
        var name:TextView
        var mbNum:TextView
        var mMenus:ImageView

        init {
            name = v.findViewById(R.id.mTitle)
            mbNum = v.findViewById(R.id.mSubTitle)
            mMenus = v.findViewById(R.id.mMenus)
            state=MutableLiveData<Boolean>()
            mMenus.setOnClickListener { popupMenus(it) }
        }

        private fun popupMenus(v:View) {
            val popupMenus = PopupMenu(c,v)
            popupMenus.inflate(R.menu.show_menu)
            popupMenus.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.editText->{
                        AlertDialog.Builder(c)
                            .setTitle("Editar")
                            .setIcon(R.drawable.ic_warning)
                            .setMessage("¿Está seguro que desea Editar?")
                            .setPositiveButton("Sí"){
                                    dialog,_->
                                Toast.makeText(c,"Se editara la carrera",Toast.LENGTH_SHORT).show()
                                dialog.dismiss()
                                state!!.value=false
                                cicloLiveData!!.value = itemsList!!.get(adapterPosition)

                            }
                            .setNegativeButton("Cancelar"){
                                    dialog,_->
                                dialog.dismiss()
                            }
                            .create()
                            .show()

                        true
                    }
                    R.id.delete->{
                        AlertDialog.Builder(c)
                            .setTitle("Eliminar")
                            .setIcon(R.drawable.ic_warning)
                            .setMessage("¿Está seguro que desea Eliminarla?")
                            .setPositiveButton("Sí"){
                                    dialog,_->
                                dialog.dismiss()
                                state!!.value=true
                                cicloLiveData!!.value = itemsList!!.get(adapterPosition)
                                state!!.value=false
                            }
                            .setNegativeButton("No"){
                                    dialog,_->
                                dialog.dismiss()
                            }
                            .create()
                            .show()

                        true
                    }
                    else-> true
                }

            }
            popupMenus.show()
            val popup = PopupMenu::class.java.getDeclaredField("mPopup")
            popup.isAccessible = true
            val menu = popup.get(popupMenus)
            menu.javaClass.getDeclaredMethod("setForceShowIcon",Boolean::class.java)
                .invoke(menu,true)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CursoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v  = inflater.inflate(R.layout.list_item,parent,false)
        return CursoViewHolder(v)
    }

    override fun onBindViewHolder(holder: CursoViewHolder, position: Int) {
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
                            row.id.toString()!!.toLowerCase().contains(charSearch)) {
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
        if (cicloLiveData== null) {
            cicloLiveData = MutableLiveData<Ciclo>()
        }
        return cicloLiveData
    }
    fun check_state(): Boolean? {
        return state!!.value
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