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
import com.matricula.mobile.adapter.CicloAdapter
import com.matricula.mobile.apiService.CicloService
import com.matricula.mobile.apiService.CursoService
import com.matricula.mobile.models.Ciclo
import com.matricula.mobile.viewModels.CicloViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException

class CiclosFragment: FragmentUtils() {

    lateinit var recyclerViewElement: RecyclerView
    lateinit var adaptador: CicloAdapter
    private lateinit var addsBtn: FloatingActionButton
    private lateinit var listaCiclo: ArrayList<Ciclo>
    private val cicloViewModel: CicloViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_carreras, container, false)
        listaCiclo = ArrayList()
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
            setToolbarTitle("Crear Curso")
            changeFragment(CrearCursoFragment())
        }
        cicloViewModel.getCiclosList()!!.observe(viewLifecycleOwner) { ciclos ->
            listaCiclo = ciclos as ArrayList<Ciclo>
            refresh()
        }
        getListOfCiclos()
        adaptador = CicloAdapter(this.activity!!, listaCiclo)
        recyclerViewElement.adapter = adaptador
        return view;
    }

    private fun refresh() {
        if (cicloViewModel.check_state() == true) {
            adaptador = CicloAdapter(this.activity!!, listaCiclo)
            recyclerViewElement.adapter = adaptador
            contract()
        } else {
            //hacer mensaje de error
        }
    }

    private fun contract() {
        val ciclo: Observer<Ciclo> = object : Observer<Ciclo> {
            @Override
            override fun onChanged(@Nullable ciclos: Ciclo?) {
                if (adaptador.check_state() == true) {
                    eliminarCiclo()
                } else {
                    val editar = EditarCarreraFragment()
                    setToolbarTitle("Editar Carrera")
                    var bundle =  Bundle();
                    var ciclo=adaptador.getCicloActual()!!.value
                    val gson = Gson()
                    var json=gson.toJson(ciclo)
                    bundle.putString("ciclo", json)
                    editar.arguments=bundle
                    changeFragment(editar)
                }
            }
        }
        adaptador.getCicloActual()!!.observe(this, ciclo)
    }

    private fun  getListOfCiclos() {
        initLoading()
        CoroutineScope(Dispatchers.IO).launch {
            val call = CicloService.getInstance().obtenerCiclos()
            val nCiclos = call.body()
            if (call.isSuccessful) {
                withContext(Dispatchers.Main) {
                    cicloViewModel.setState(true)
                    cicloViewModel.updateModel(nCiclos!!)
                    stopLoading()
                }
            } else {
                withContext(Dispatchers.Main) {
                    cicloViewModel.setState(false)
                    cicloViewModel.setMensaje(call.message())
                    stopLoading()
                }//mensaje de error del servidor...
            }
        }
    }

    private fun eliminarCiclo() {
        initLoading()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                var codigo = adaptador.getCicloActual()!!.value!!.id
                val call = CursoService.getInstance().eliminarCurso(id.toString()!!)
                if (call.isSuccessful) {
                    withContext(Dispatchers.Main) {
                        stopLoading()
                        dialogDeleteSuccess()
                        getListOfCiclos()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        cicloViewModel.setState(false)
                        cicloViewModel.setMensaje(call.message())
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
        loader?.visibility= View.VISIBLE
    }

    private fun stopLoading(){
        val loader=view?.findViewById<ProgressBar>(R.id.loading)
        loader?.visibility= View.GONE
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