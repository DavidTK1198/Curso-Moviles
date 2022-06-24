package com.matricula.mobile.app.ui

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
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
import com.google.gson.Gson
import com.matricula.mobile.R
import com.matricula.mobile.adapter.AlumnoAdapter
import com.matricula.mobile.apiService.AlumnoService
import com.matricula.mobile.app.MainActivity
import com.matricula.mobile.models.Alumno
import com.matricula.mobile.models.Usuario
import com.matricula.mobile.viewModels.AlumnoViewModel
import kotlinx.coroutines.*
import java.net.SocketTimeoutException


class AlumnosFragment : FragmentUtils() {

    lateinit var recyclerViewElement: RecyclerView
    lateinit var adaptador: AlumnoAdapter
    private lateinit var addsBtn: FloatingActionButton
    private lateinit var listaAlumno: ArrayList<Alumno>
    private lateinit var usuario: Usuario
    private val alumnoViewModel: AlumnoViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_alumnos, container, false)
        listaAlumno = ArrayList()
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
            setToolbarTitle("Crear Alumno")
            changeFragment(CrearAlumnoFragment())
        }
        alumnoViewModel.getAlumnosList()!!.observe(viewLifecycleOwner) { Alumnos ->
            listaAlumno = Alumnos as ArrayList<Alumno>
            refresh()
        }
        getListOfAlumnos()

       usuario = (activity as MainActivity?)!!.usuario()
        adaptador = AlumnoAdapter(this.activity!!, listaAlumno,usuario)
        recyclerViewElement.adapter = adaptador
        return view;
    }

    private fun refresh() {
        if (alumnoViewModel.check_state() == true) {
            adaptador = AlumnoAdapter(this.activity!!, listaAlumno,usuario)
            recyclerViewElement.adapter = adaptador
            contract()
        } else {
            //hacer mensaje de error
        }
    }

    private fun contract() {
        val Alumno: Observer<Alumno> = object : Observer<Alumno> {
            @Override
            override fun onChanged(@Nullable Alumnos: Alumno?) {
                when(adaptador.check_state()){
                    1->editar()
                    2->eliminarAlumno()
                    3->historial()
                    4->matricula()
                }
            }


        }
        adaptador.getAlumnoActual()!!.observe(this, Alumno)
    }

    private fun matricula() {
        val m=MatriculaFragment()
        setToolbarTitle("Gestionar Matricula")
        changeFragment(m)
    }

    private fun historial() {
        val historial = HistorialFragment()
        setToolbarTitle("Historial Académico")
        var bundle =  Bundle();
        var Alumno=adaptador.getAlumnoActual()!!.value
        val gson = Gson()
        var json=gson.toJson(Alumno)
        bundle.putString("Alumno", json)
        historial.arguments=bundle
        changeFragment(historial)
    }
    private fun  getListOfAlumnos() {
        initLoading()
        CoroutineScope(Dispatchers.IO).launch {
            val call = AlumnoService.getInstance().obtenerAlumnos()
            val nAlumnos = call.body()
            if (call.isSuccessful) {
                withContext(Dispatchers.Main) {
                    alumnoViewModel.setState(true)
                    alumnoViewModel.updateModel(nAlumnos!!)
                    stopLoading()
                }
            } else {
                withContext(Dispatchers.Main) {
                    alumnoViewModel.setState(false)
                    alumnoViewModel.setMensaje(call.message())
                    stopLoading()
                }//mensaje de error del servidor...
            }
        }
    }

    private fun editar(){
        val editar = EditarAlumnoFragment()
        setToolbarTitle("Editar Alumno")
        var bundle =  Bundle();
        var Alumno=adaptador.getAlumnoActual()!!.value
        val gson = Gson()
        var json=gson.toJson(Alumno)
        bundle.putString("Alumno", json)
        editar.arguments=bundle
        changeFragment(editar)
    }

    private fun eliminarAlumno() {
        initLoading()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                var codigo = adaptador.getAlumnoActual()!!.value!!.cedula
                val call = AlumnoService.getInstance().eliminarAlumno(codigo!!)
                if (call.isSuccessful) {
                    withContext(Dispatchers.Main) {
                        stopLoading()
                        dialogDeleteSuccess()
                        getListOfAlumnos()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        alumnoViewModel.setState(false)
                        alumnoViewModel.setMensaje(call.message())
                        stopLoading()
                        dialogDeleteError()
                    }//mensaje de error del servidor...
                }
            } catch (e: SocketTimeoutException) {
                Log.d("xd","mamado")
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

