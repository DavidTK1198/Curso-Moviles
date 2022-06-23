package com.matricula.mobile.app.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.annotation.Nullable
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.matricula.mobile.R
import com.matricula.mobile.adapter.Grupo_ProfesorAdapter
import com.matricula.mobile.apiService.ProfesorService
import com.matricula.mobile.models.Profesor
import com.matricula.mobile.viewModels.ProfesorViewModel
import kotlinx.coroutines.*
import java.net.SocketTimeoutException


class Grupo_ProfesorFragment: FragmentUtils() {
    lateinit var recyclerViewElement: RecyclerView
    lateinit var adaptador: Grupo_ProfesorAdapter
    private lateinit var addsBtn: FloatingActionButton
    private lateinit var listaProfesor: ArrayList<Profesor>
    private val ProfesorViewModel: ProfesorViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_profesores, container, false)
        listaProfesor = ArrayList()
        recyclerViewElement = view.findViewById(R.id.mRecycler)
        recyclerViewElement.layoutManager = LinearLayoutManager(recyclerViewElement.context)
        recyclerViewElement.setHasFixedSize(true)
        view.findViewById<SearchView>(R.id.input_search)
            .setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    adaptador.filter.filter(newText)

                    return false
                }
            })
        addsBtn = view.findViewById(R.id.addingBtn)
        addsBtn.setOnClickListener { view ->
            addsBtn.visibility=View.INVISIBLE
        }
        ProfesorViewModel.getProfesorsList()!!.observe(viewLifecycleOwner) { Profesors ->
            listaProfesor = Profesors as ArrayList<Profesor>
            refresh()
        }
        getListOfProfesors()
        adaptador = Grupo_ProfesorAdapter(this.activity!!, listaProfesor)
        recyclerViewElement.adapter = adaptador
        return view;
    }

    private fun refresh() {
        if (ProfesorViewModel.check_state() == true) {
            adaptador = Grupo_ProfesorAdapter(this.activity!!, listaProfesor)
            recyclerViewElement.adapter = adaptador
            contract()
        } else {
            //hacer mensaje de error
        }
    }

    private fun contract() {
        val Profesor: Observer<Profesor> = object : Observer<Profesor> {
            @Override
            override fun onChanged(@Nullable Profesors: Profesor?) {
                volverGrupo()
            }
        }
        adaptador.getProfesorActual()!!.observe(this, Profesor)
    }

    private fun volverGrupo(){
        var Profesor= adaptador.getProfesorActual()!!.value
        ProfesorViewModel.setprofesor(Profesor!!)
        val a = CrearGrupoFragment()
        setToolbarTitle("Crear Grupo")
        changeFragment(a)
    }

    private fun  getListOfProfesors() {
        initLoading()
        CoroutineScope(Dispatchers.IO).launch {
            val call = ProfesorService.getInstance().obtenerProfesores()
            val nProfesors = call.body()
            if (call.isSuccessful) {
                withContext(Dispatchers.Main) {
                    ProfesorViewModel.setState(true)
                    ProfesorViewModel.updateModel(nProfesors!!)
                    stopLoading()
                }
            } else {
                withContext(Dispatchers.Main) {
                    ProfesorViewModel.setState(false)
                    ProfesorViewModel.setMensaje(call.message())
                    stopLoading()
                }//mensaje de error del servidor...
            }
        }
    }


    private fun initLoading(){
        val loader = view?.findViewById<ProgressBar>(R.id.loading)
        loader?.visibility=View.VISIBLE
    }

    private fun stopLoading(){
        val loader=view?.findViewById<ProgressBar>(R.id.loading)
        loader?.visibility=View.GONE
    }

    private fun dialogDeleteSuccess(){
        AlertDialog.Builder(this.activity!!)
            .setTitle("Resultado")
            .setIcon(R.drawable.ic_success)
            .setMessage("Eliminada Correctamente!!!\nActualizando Contenido......")
            .setPositiveButton("Ok"){ dialog,_->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun dialogDeleteError(){
        AlertDialog.Builder(this.activity!!)
            .setTitle("Resultado")
            .setIcon(R.drawable.ic_warning)
            .setMessage("Error al eliminar.....")
            .setPositiveButton("Ok"){ dialog,_->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun diaglogTimeOut(){
        AlertDialog.Builder(this.activity!!)
            .setTitle("Alerta")
            .setIcon(R.drawable.ic_warning)
            .setMessage("Error al conectar con el servidor")
            .setPositiveButton("Ok"){ dialog,_->
                dialog.dismiss()
            }
            .create()
            .show()
    }
}

