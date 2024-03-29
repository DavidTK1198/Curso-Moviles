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

class CarreraAdapter(val c: Context, val CarreraList:ArrayList<Carrera>): RecyclerView.Adapter<CarreraAdapter.CarreraViewHolder>(), Filterable
{
    private var itemsList: ArrayList<Carrera>? = null
     var carreraLiveData:MutableLiveData<Carrera>? = null
    private var state:MutableLiveData<Int>?=null



    init {
        this.itemsList=CarreraList
        carreraLiveData = MutableLiveData<Carrera>()
    }
    inner class CarreraViewHolder(val v:View):RecyclerView.ViewHolder(v){
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
            popupMenus.inflate(R.menu.cursos_menu)
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
                                state!!.value=0
                                carreraLiveData!!.value = itemsList!!.get(adapterPosition)

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
                                carreraLiveData!!.value = itemsList!!.get(adapterPosition)
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

                    R.id.cursos_list->{
                        AlertDialog.Builder(c)
                            .setTitle("Cursos")
                            .setIcon(R.drawable.ic_warning)
                            .setMessage("¿Está seguro que desea ver los cursos?")
                            .setPositiveButton("Sí"){
                                    dialog,_->
                                dialog.dismiss()
                                state!!.value=2
                                carreraLiveData!!.value = itemsList!!.get(adapterPosition)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarreraViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v  = inflater.inflate(R.layout.list_item,parent,false)
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
    fun check_state(): Int? {
        return state!!.value
    }


}