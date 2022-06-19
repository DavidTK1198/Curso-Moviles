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

class ProfesorAdapter(val c: Context, val ProfesorList:ArrayList<Profesor>): RecyclerView.Adapter<ProfesorAdapter.ProfesorViewHolder>(), Filterable
{
    private var itemsList: ArrayList<Profesor>? = null
    var ProfesorLiveData:MutableLiveData<Profesor>? = null
    private var state:MutableLiveData<Boolean>?=null



    init {
        this.itemsList=ProfesorList
        ProfesorLiveData = MutableLiveData<Profesor>()
    }
    inner class ProfesorViewHolder(val v:View):RecyclerView.ViewHolder(v){
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
                                Toast.makeText(c,"Se editara la Profesor",Toast.LENGTH_SHORT).show()
                                dialog.dismiss()
                                state!!.value=false
                                ProfesorLiveData!!.value = itemsList!!.get(adapterPosition)

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
                                ProfesorLiveData!!.value = itemsList!!.get(adapterPosition)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfesorViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v  = inflater.inflate(R.layout.list_item,parent,false)
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
    fun check_state(): Boolean? {
        return state!!.value
    }


}