package com.matricula.sqllite.controller.ui

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.matricula.sqllite.R
import com.matricula.sqllite.accesodatos.DatabaseHelper
import com.matricula.sqllite.logicanegocio.Curso
import com.matricula.sqllite.logicanegocio.CursoModel
import com.matricula.sqllite.logicanegocio.Estudiante
import com.matricula.sqllite.logicanegocio.EstudianteModel
import com.matricula.sqllite.util.FragmentUtils
import com.matricula.sqllite.util.RecyclerView_Adapter_Curso
import java.util.Objects


class MatricularFragment (helper: DatabaseHelper): FragmentUtils(){
    private lateinit var appBarConfiguration: AppBarConfiguration
    private var model = CursoModel.instance
    lateinit var recyclerViewElement: RecyclerView
    lateinit var adaptador: RecyclerView_Adapter_Curso
    var helper=helper
    var position: Int = 0
    private var modelEst = EstudianteModel.instance
    private lateinit var estudiante:Estudiante
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_cursos, container, false)

        val searchIcon = view.findViewById<ImageView>(R.id.search_mag_icon)
        searchIcon.setColorFilter(Color.BLACK)
        model.sethelper(helper)
        val cancelIcon = view.findViewById<ImageView>(R.id.search_close_btn)
        cancelIcon.setColorFilter(Color.BLACK)
        val textView = view.findViewById<TextView>(R.id.search_src_text)
        textView.setTextColor(Color.BLACK)
        val gson= Gson()
        val bundle = Bundle()
        var est=arguments!!.get("estudiante").toString()
        estudiante= gson.fromJson(est,Estudiante::class.java)
        modelEst.estudiante=estudiante
        recyclerViewElement = view.findViewById(R.id.recycleView_curso)
        recyclerViewElement.layoutManager = LinearLayoutManager(recyclerViewElement.context)
        recyclerViewElement.setHasFixedSize(true)

        view.findViewById<SearchView>(R.id.curso_search)
            .setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    adaptador.filter.filter(newText)

                    return false
                }
            })

        getListOfCursos()

        return view;
    }
    private fun getListOfCursos() {
        val Ncursos = ArrayList<Curso>()
        for (p in model.getAllCursos()!!) {
            Ncursos.add(p)
        }
        adaptador = RecyclerView_Adapter_Curso(Ncursos)
        recyclerViewElement.adapter = adaptador
        adaptador.setOnItemClickListener(object: RecyclerView_Adapter_Curso.onItemClickListener{
            override fun onItemClick(position: Int) {
                if(model.matricular(estudiante.cedula,position)){
                    var message = "Curso matriculado"
                    Snackbar
                        .make(view!!, message!!, Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .show()
                }else{
                   var  message = "Curso ya matriculado"
                    Snackbar
                        .make(view!!, message!!, Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .show()
                }

            }
        })
    }
    private fun getIndex(index: Int){
        var index = index
        var adapterItems = adaptador.itemsList
        var listacursos = model.getAllCursos()
        model.curso = adapterItems?.get(index)!!
        index = listacursos!!.indexOfFirst {
            it.codigo == model.curso.codigo
        }

    }
}