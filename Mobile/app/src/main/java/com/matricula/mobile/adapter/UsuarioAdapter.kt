package com.matricula.mobile.adapter
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.matricula.mobile.R
import com.matricula.mobile.models.Usuario
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class UsuarioAdapter(val c: Context, val UsuarioList:ArrayList<Usuario>): RecyclerView.Adapter<UsuarioAdapter.UsuarioViewHolder>(), Filterable
{
    private var itemsList: ArrayList<Usuario>? = null
    var UsuarioLiveData:MutableLiveData<Usuario>? = null



    init {
        this.itemsList=UsuarioList
        UsuarioLiveData = MutableLiveData<Usuario>()
    }
    inner class UsuarioViewHolder(val v:View):RecyclerView.ViewHolder(v){
        var name:TextView
        var mbNum:TextView
        var mMenus:ImageView

        init {
            name = v.findViewById(R.id.mTitle)
            mbNum = v.findViewById(R.id.mSubTitle)
            mMenus = v.findViewById(R.id.mMenus)
            mMenus.setOnClickListener { popupMenus(it) }
        }

        private fun popupMenus(v:View) {
            val popupMenus = PopupMenu(c,v)
            popupMenus.inflate(R.menu.usuario_menu)
            popupMenus.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.editText->{
                        AlertDialog.Builder(c)
                            .setTitle("Editar")
                            .setIcon(R.drawable.ic_warning)
                            .setMessage("¿Está seguro que desea Editar?")
                            .setPositiveButton("Sí"){
                                    dialog,_->
                                Toast.makeText(c,"Se editara el Usuario",Toast.LENGTH_SHORT).show()
                                dialog.dismiss()
                                UsuarioLiveData!!.value = itemsList!!.get(adapterPosition)

                            }
                            .setNegativeButton("Cancelar"){
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsuarioViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v  = inflater.inflate(R.layout.list_item,parent,false)
        return UsuarioViewHolder(v)
    }

    override fun onBindViewHolder(holder: UsuarioViewHolder, position: Int) {
        val newList = itemsList?.get(position)
        holder.name.text = newList!!.rol
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
                    itemsList = UsuarioList
                } else {
                    val resultList = ArrayList<Usuario>()
                    for (row in UsuarioList) {
                        if (row.nombre!!.toLowerCase().contains(charSearch.toLowerCase()) ||
                            row.rol!!.toLowerCase().contains(charSearch)) {
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
                itemsList = results?.values as ArrayList<Usuario>
                notifyDataSetChanged()
            }

        }
    }

    fun getUsuarioActual(): LiveData<Usuario>?{
        if (UsuarioLiveData== null) {
            UsuarioLiveData = MutableLiveData<Usuario>()
        }
        return UsuarioLiveData
    }
}