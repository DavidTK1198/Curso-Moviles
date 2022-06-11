package com.example.peopleapp

import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.matricula.sqllite.R
import com.matricula.sqllite.accesodatos.DatabaseHelper
import com.matricula.sqllite.controller.ui.CreateEstudianteFragment
import com.matricula.sqllite.controller.ui.EditEstudianteFragment
import com.matricula.sqllite.logicanegocio.Estudiante
import com.matricula.sqllite.logicanegocio.EstudianteModel
import com.matricula.sqllite.util.FragmentUtils
import com.matricula.sqllite.util.RecyclerView_Adapter
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import java.util.*
import kotlin.collections.ArrayList

class EstudiantesFragment(helper: DatabaseHelper): FragmentUtils(){
    private lateinit var appBarConfiguration: AppBarConfiguration
    private var model =EstudianteModel.instance
    lateinit var recyclerViewElement: RecyclerView
    lateinit var adaptador: RecyclerView_Adapter
    var helper=helper
    var position: Int = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_estudiantes, container, false)

        val searchIcon = view.findViewById<ImageView>(R.id.search_mag_icon)
        searchIcon.setColorFilter(Color.BLACK)
         model.sethelper(helper)
        val cancelIcon = view.findViewById<ImageView>(R.id.search_close_btn)
        cancelIcon.setColorFilter(Color.BLACK)


        val textView = view.findViewById<TextView>(R.id.search_src_text)
        textView.setTextColor(Color.BLACK)

        recyclerViewElement = view.findViewById(R.id.recycleView)
        recyclerViewElement.layoutManager = LinearLayoutManager(recyclerViewElement.context)
        recyclerViewElement.setHasFixedSize(true)

        view.findViewById<SearchView>(R.id.person_search)
            .setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    adaptador.filter.filter(newText)

                    return false
                }
            })

        getListOfEstudiantes()

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.START or ItemTouchHelper.END,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {


            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val fromPosition: Int = viewHolder.adapterPosition
                val toPosition: Int = target.adapterPosition

                Collections.swap(model.estudiantes, fromPosition, toPosition)

                recyclerViewElement.adapter?.notifyItemMoved(fromPosition, toPosition)

                return false
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                position = viewHolder.adapterPosition

                if (direction == ItemTouchHelper.LEFT) {//Delete

                    var index = getIndex(position)
                    model.delete()
                    recyclerViewElement.adapter?.notifyItemRemoved(position)

                    Snackbar.make(recyclerViewElement, model.estudiante.nombre + " eliminado/a", Snackbar.LENGTH_LONG).setAction("Undo") {
                        //estudiantes.getestudiantes().add(position, Estudiante)
                        recyclerViewElement.adapter?.notifyItemInserted(position)
                    }.show()

                    adaptador = RecyclerView_Adapter(model.getAllEstudiantes()!!)
                    recyclerViewElement.adapter = adaptador

                } else { //Edit
                         getIndex(position)
                    var editFragment = EditEstudianteFragment()
                    setToolbarTitle("Editar Estudiante")
                   changeFragment(fragmentUtils = editFragment)
                }
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                RecyclerViewSwipeDecorator.Builder(
                    this@EstudiantesFragment.context,
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(this@EstudiantesFragment.context!!, R.color.red))
                    .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
                    .addSwipeRightBackgroundColor(
                        ContextCompat.getColor(
                            this@EstudiantesFragment.context!!,
                            R.color.green
                        )
                    )
                    .addSwipeRightActionIcon(R.drawable.ic_baseline_edit_24)
                    .create()
                    .decorate()
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }

        }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerViewElement)

        val add: FloatingActionButton = view.findViewById(R.id.add)
        add.setOnClickListener { view ->
            changeFragment(CreateEstudianteFragment())
        }
        return view;
    }
    private fun getListOfEstudiantes() {
        val Nestudiantes = ArrayList<Estudiante>()
        for (p in model.getAllEstudiantes()!!) {
            Nestudiantes.add(p)
        }
        adaptador = RecyclerView_Adapter(Nestudiantes)
        recyclerViewElement.adapter = adaptador
    }
    private fun getIndex(index: Int){
      var index = index
       var adapterItems = adaptador.itemsList
        var listaestudiantes = model.getAllEstudiantes()
       model.estudiante = adapterItems?.get(index)!!
        index = listaestudiantes!!.indexOfFirst {
           it.cedula == model.estudiante.cedula
        }

    }
}