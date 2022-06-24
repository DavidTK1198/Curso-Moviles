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
import com.matricula.mobile.viewModels.InscripcionViewModel
import com.matricula.mobile.R
import com.matricula.mobile.adapter.AlumnoNotaAdapter
import com.matricula.mobile.apiService.InscripcionService
import com.matricula.mobile.app.MainActivity
import com.matricula.mobile.models.Inscripcion
import com.matricula.mobile.models.Usuario
import com.matricula.mobile.viewModels.GrupoViewModel
import kotlinx.coroutines.*
import java.net.SocketTimeoutException


class NotasAlumnoFragment : FragmentUtils() {

    lateinit var recyclerViewElement: RecyclerView
    lateinit var adaptador: AlumnoNotaAdapter
    private lateinit var addsBtn: FloatingActionButton
    private lateinit var listaInscripcion: ArrayList<Inscripcion>
    private val InscripcionViewModel: InscripcionViewModel by activityViewModels()
    private val GrupoViewModel: GrupoViewModel by activityViewModels()
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
        addsBtn = view.findViewById(R.id.volverMain)
        addsBtn.setOnClickListener { view ->
            setToolbarTitle("Inicio")
            changeFragment(RegistroNotasFragment())
        }
        InscripcionViewModel.getInscripcionesList()!!.observe(viewLifecycleOwner) { Inscripcions ->
            listaInscripcion = Inscripcions as ArrayList<Inscripcion>
            refresh()
        }
        getListOfInscripcion()
        adaptador = AlumnoNotaAdapter(this.activity!!, listaInscripcion)
        recyclerViewElement.adapter = adaptador
        return view;
    }

    private fun refresh() {
        if (InscripcionViewModel.check_state() == true) {
            adaptador = AlumnoNotaAdapter(this.activity!!, listaInscripcion)
            recyclerViewElement.adapter = adaptador
            contract()
        } else {
            //hacer mensaje de error
        }
    }

    private fun contract() {
        val Inscripcion: Observer<Inscripcion> = object : Observer<Inscripcion> {
            @Override
            override fun onChanged(@Nullable ins: Inscripcion?) {
                InscripcionViewModel.setInscripcion(ins!!)
                editar()
            }
        }
        adaptador.getInscripcionActual()!!.observe(this, Inscripcion)
    }

    private fun editar(){
        val editar = NotaAlumnoFragment()
       setToolbarTitle("Asignar Nota")
          changeFragment(editar)
    }


    private fun  getListOfInscripcion() {
        initLoading()
        val grupo= GrupoViewModel.getGrupo()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val call = InscripcionService.getInstance().obtenerInscripcionesPorGrupo(grupo!!.idEntidad.toString())
                val nInscripcions = call.body()
                if (call.isSuccessful) {
                    withContext(Dispatchers.Main) {
                        InscripcionViewModel.setState(true)
                        InscripcionViewModel.updateModel(nInscripcions!!)
                        stopLoading()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        InscripcionViewModel.setState(false)
                        InscripcionViewModel.setMensaje(call.message())
                        stopLoading()
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

