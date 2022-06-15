package com.matricula.mobile.app.ui

import androidx.recyclerview.widget.RecyclerView
import com.matricula.mobile.utils.CarreraAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.matricula.mobile.R
import com.matricula.mobile.models.Carrera
import kotlin.collections.ArrayList
class CarrerasFragment: FragmentUtils() {

    lateinit var recyclerViewElement: RecyclerView
    lateinit var adaptador: CarreraAdapter

    var position: Int = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_carreras, container, false)

        recyclerViewElement = view.findViewById(R.id.mRecycler)
        recyclerViewElement.layoutManager = LinearLayoutManager(recyclerViewElement.context)
        recyclerViewElement.setHasFixedSize(true)

       view.findViewById<SearchView>(R.id.carrera_search)
          .setOnQueryTextListener(object : SearchView.OnQueryTextListener {
               override fun onQueryTextSubmit(query: String?): Boolean {
                   return false
               }

              override fun onQueryTextChange(newText: String?): Boolean {
                 adaptador.filter.filter(newText)

                   return false
              }
           })

        getListOfCarreras()

        return view;
    }
    private fun  getListOfCarreras() {
        val NCarreras = ArrayList<Carrera>()
        NCarreras.add(Carrera("EIF","Sistemas","Bachillerato"))
        NCarreras.add(Carrera("MAT","Matematica","Bachillerato"))
//        for (p in model.getAllCursos()!!) {
//            Ncursos.add(p)
//        }
        adaptador = CarreraAdapter(this.activity!!,NCarreras)
        recyclerViewElement.adapter = adaptador
    }

}