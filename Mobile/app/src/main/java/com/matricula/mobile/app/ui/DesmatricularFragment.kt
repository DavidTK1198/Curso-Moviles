package com.matricula.mobile.app.ui

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.annotation.Nullable
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.matricula.mobile.viewModels.InscripcionViewModel
import com.matricula.mobile.R
import com.matricula.mobile.adapter.DesmatricularAdapter
import com.matricula.mobile.adapter.InscripcionAdapter
import com.matricula.mobile.apiService.InscripcionService
import com.matricula.mobile.models.*
import com.matricula.mobile.viewModels.AlumnoViewModel
import kotlinx.coroutines.*
import java.net.SocketTimeoutException


class DesmatricularFragment : FragmentUtils() {

    lateinit var recyclerViewElement: RecyclerView
    lateinit var adaptador: DesmatricularAdapter
    private lateinit var listaInscripcion: ArrayList<Inscripcion>
    private  lateinit var alumno: Alumno
    private val InscripcionViewModel: InscripcionViewModel by activityViewModels()
    private val alumnoViewModel: AlumnoViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_inscripciones, container, false)
        listaInscripcion = ArrayList()
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
        InscripcionViewModel.getInscripcionesList()!!.observe(viewLifecycleOwner) { Inscripciones ->
            listaInscripcion = Inscripciones as ArrayList<Inscripcion>
            refresh()
        }
       alumno= alumnoViewModel.getAlumno()!!.value!!
        adaptador = DesmatricularAdapter(this.activity!!, listaInscripcion)
        recyclerViewElement.adapter = adaptador
        val volver= view.findViewById<FloatingActionButton>(R.id.volverMain).setOnClickListener{
            setToolbarTitle("Alumnos")
            changeFragment(AlumnosFragment())
        }

        getListOfInscripciones()
        return view;
    }
    private fun contract() {
        val inscripcion: Observer<Inscripcion> = object : Observer<Inscripcion> {
            @Override
            override fun onChanged(@Nullable ins: Inscripcion?) {
                InscripcionViewModel.setInscripcion(ins!!)
                desMatricular()
            }
        }
        adaptador.getInscripcionActual()!!.observe(this, inscripcion)
    }

    private fun mensaje(){
        AlertDialog.Builder(this.activity!!)
            .setTitle("Resultado")
            .setIcon(R.drawable.ic_success)
            .setMessage("Retirado correctamente!!!")
            .setPositiveButton("Ok"){ dialog,_->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun refresh() {
        if (InscripcionViewModel.check_state() == true) {
            adaptador = DesmatricularAdapter(this.activity!!, listaInscripcion)
            recyclerViewElement.adapter = adaptador
            contract()
        } else {
            //hacer mensaje de error
        }
    }


    private fun  getListOfInscripciones() {
        initLoading()
        CoroutineScope(Dispatchers.IO).launch {
            val call = InscripcionService.getInstance().obtenerInscripcionesPorAlumno(alumno.cedula)
            val nInscripcions = call.body()
            if (call.isSuccessful) {
                withContext(Dispatchers.Main) {
                    InscripcionViewModel.setState(true)
                    InscripcionViewModel.updateModel(nInscripcions!!)
                    refresh()
                }
            } else {
                withContext(Dispatchers.Main) {
                    InscripcionViewModel.setState(false)
                    InscripcionViewModel.setMensaje(call.message())
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

    private fun desMatricular() {
        val ins=InscripcionViewModel.getInscripcion()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val call = InscripcionService.getInstance().desMatricular(ins!!.idEntidad.toString())
                val nGrupos = call.body()
                if (call.isSuccessful) {
                    withContext(Dispatchers.Main) {
                        InscripcionViewModel.setState(true)
                        getListOfInscripciones()
                        mensaje()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        InscripcionViewModel.setState(false)
                        InscripcionViewModel.setMensaje(call.message())
                        stopLoading()
                    }//mensaje de error del servidor...
                }
            }catch (e:SocketTimeoutException){

            }
        }
    }
}

