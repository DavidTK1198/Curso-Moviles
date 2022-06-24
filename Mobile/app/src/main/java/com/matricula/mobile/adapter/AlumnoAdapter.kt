package com.matricula.mobile.adapter
import android.app.AlertDialog
import android.content.ClipData.Item
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
import com.matricula.mobile.models.Alumno
import com.matricula.mobile.models.Usuario

class AlumnoAdapter(val c: Context, val AlumnoList:ArrayList<Alumno>,usuario: Usuario): RecyclerView.Adapter<AlumnoAdapter.AlumnoViewHolder>(), Filterable
{
    private var itemsList: ArrayList<Alumno>? = null
    var AlumnoLiveData:MutableLiveData<Alumno>? = null
    private var state:MutableLiveData<Int>?=null
    private val usuario=usuario

    init {
        this.itemsList=AlumnoList
        AlumnoLiveData = MutableLiveData<Alumno>()
    }
    inner class AlumnoViewHolder(val v:View):RecyclerView.ViewHolder(v){
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
            if(usuario.rol=="ADM")
            popupMenus.inflate(R.menu.alumno_menu)
            else
                popupMenus.inflate(R.menu.matriculador_menu)
            popupMenus.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.editText->{
                        AlertDialog.Builder(c)
                            .setTitle("Editar")
                            .setIcon(R.drawable.ic_warning)
                            .setMessage("¿Está seguro que desea Editar?")
                            .setPositiveButton("Sí"){
                                    dialog,_->
                                Toast.makeText(c,"Se editara la Alumno",Toast.LENGTH_SHORT).show()
                                dialog.dismiss()
                                state!!.value=1
                                AlumnoLiveData!!.value = itemsList!!.get(adapterPosition)

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
                            .setMessage("¿Está seguro que desea Eliminar?")
                            .setPositiveButton("Sí"){
                                    dialog,_->
                                dialog.dismiss()
                                state!!.value=2
                                AlumnoLiveData!!.value = itemsList!!.get(adapterPosition)
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
                    R.id.eshistorial->{
                        AlertDialog.Builder(c)
                            .setTitle("Historial Académico")
                            .setIcon(R.drawable.ic_warning)
                            .setMessage("¿Está seguro que desea ver el historial?")
                            .setPositiveButton("Sí"){
                                    dialog,_->
                                dialog.dismiss()
                                state!!.value=3
                                AlumnoLiveData!!.value = itemsList!!.get(adapterPosition)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlumnoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v  = inflater.inflate(R.layout.list_item,parent,false)
        return AlumnoViewHolder(v)
    }

    override fun onBindViewHolder(holder: AlumnoViewHolder, position: Int) {
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
                    itemsList = AlumnoList
                } else {
                    val resultList = ArrayList<Alumno>()
                    for (row in AlumnoList) {
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
                itemsList = results?.values as ArrayList<Alumno>
                notifyDataSetChanged()
            }

        }
    }

    fun getAlumnoActual(): LiveData<Alumno>?{
        if (AlumnoLiveData== null) {
            AlumnoLiveData = MutableLiveData<Alumno>()
        }
        return AlumnoLiveData
    }
    fun check_state(): Int? {
        return state!!.value
    }


}