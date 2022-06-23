package com.matricula.mobile.adapter
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.matricula.mobile.R
import com.matricula.mobile.models.Grupo
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class GrupoAdapter(val c: Context, val GrupoList:ArrayList<Grupo>): RecyclerView.Adapter<GrupoAdapter.GrupoViewHolder>(), Filterable
{
    private var itemsList: ArrayList<Grupo>? = null
    var GrupoLiveData:MutableLiveData<Grupo>? = null
    private var state:MutableLiveData<Int>?=null



    init {
        this.itemsList=GrupoList
        GrupoLiveData = MutableLiveData<Grupo>()
    }
    inner class GrupoViewHolder(val v:View):RecyclerView.ViewHolder(v){
        var name:TextView
        var mbNum:TextView
        var mMenus:ImageView

        init {
            name = v.findViewById(R.id.mTitle)
            mbNum = v.findViewById(R.id.mSubTitle)
            mMenus = v.findViewById(R.id.mMenus)
            state=MutableLiveData<Int>()
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
                                Toast.makeText(c,"Se editara la Grupo",Toast.LENGTH_SHORT).show()
                                dialog.dismiss()
                                state!!.value=0
                                GrupoLiveData!!.value = itemsList!!.get(adapterPosition)

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
                                state!!.value=1
                                GrupoLiveData!!.value = itemsList!!.get(adapterPosition)
                                state!!.value=0
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GrupoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v  = inflater.inflate(R.layout.list_item,parent,false)
        return GrupoViewHolder(v)
    }

    override fun onBindViewHolder(holder: GrupoViewHolder, position: Int) {
        val newList = itemsList?.get(position)
        holder.name.text = newList!!.curso.nombre
        holder.mbNum.text = newList!!.getProfesor().nombre
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
                        if (row.curso.nombre!!.toLowerCase().contains(charSearch.toLowerCase()) ||
                            row.getProfesor().nombre!!.toLowerCase().contains(charSearch)) {
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
    fun check_state(): Int? {
        return state!!.value
    }


}