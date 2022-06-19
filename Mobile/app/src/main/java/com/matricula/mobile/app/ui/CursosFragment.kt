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
import com.matricula.mobile.adapter.CursoAdapter
import com.matricula.mobile.apiService.CursoService
import com.matricula.mobile.models.Carrera
import com.matricula.mobile.viewModels.CursoViewModel
import com.matricula.mobile.models.Curso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException

class CursosFragment: FragmentUtils() {

    lateinit var recyclerViewElement: RecyclerView
    lateinit var adaptador: CursoAdapter
    private lateinit var addsBtn: FloatingActionButton
    private lateinit var listaCurso: ArrayList<Curso>
    private val cursoViewModel: CursoViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_carreras, container, false)
        listaCurso = ArrayList()
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
        cursoViewModel.getCursosList()!!.observe(viewLifecycleOwner) { cursos ->
            listaCurso = cursos as ArrayList<Curso>
            refresh()
        }
        getListOfCursos()
        adaptador = CursoAdapter(this.activity!!, listaCurso)
        recyclerViewElement.adapter = adaptador
        return view;
    }

    private fun refresh() {
        if (cursoViewModel.check_state() == true) {
            adaptador = CursoAdapter(this.activity!!, listaCurso)
            recyclerViewElement.adapter = adaptador
            contract()
        } else {
            //hacer mensaje de error
        }
    }

    private fun contract() {
        val carrera: Observer<Curso> = object : Observer<Curso> {
            @Override
            override fun onChanged(@Nullable cursos: Curso?) {
                if (adaptador.check_state() == true) {
                    eliminarCurso()
                } else {
                    val editar = EditarCarreraFragment()
                    setToolbarTitle("Editar Carrera")
                    var bundle =  Bundle();
                    var carrera=adaptador.getCarreraActual()!!.value
                    val gson = Gson()
                    var json=gson.toJson(carrera)
                    bundle.putString("carrera", json)
                    editar.arguments=bundle
                    changeFragment(editar)
                }
            }
        }
        adaptador.getCarreraActual()!!.observe(this, carrera)
    }

    private fun  getListOfCursos() {
        initLoading()
        var carrera=cursoViewModel.getCarrera()
        CoroutineScope(Dispatchers.IO).launch {
            val call = CursoService.getInstance().obtenerCursosPorCarrera(carrera.codigo!!)
            val nCarreras = call.body()
            if (call.isSuccessful) {
                withContext(Dispatchers.Main) {
                    cursoViewModel.setState(true)
                    cursoViewModel.updateModel(nCarreras!!)
                    stopLoading()
                }
            } else {
                withContext(Dispatchers.Main) {
                    cursoViewModel.setState(false)
                    cursoViewModel.setMensaje(call.message())
                    stopLoading()
                }//mensaje de error del servidor...
            }
        }
    }

    private fun eliminarCurso() {
        initLoading()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                var codigo = adaptador.getCarreraActual()!!.value!!.codigo
                val call = CursoService.getInstance().eliminarCurso(codigo!!)
                if (call.isSuccessful) {
                    withContext(Dispatchers.Main) {
                        stopLoading()
                        dialogDeleteSuccess()
                        getListOfCursos()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        cursoViewModel.setState(false)
                        cursoViewModel.setMensaje(call.message())
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