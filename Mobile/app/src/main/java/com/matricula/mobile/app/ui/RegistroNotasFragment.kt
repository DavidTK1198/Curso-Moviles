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
import com.matricula.mobile.viewModels.GrupoViewModel
import com.matricula.mobile.R
import com.matricula.mobile.adapter.RegistroNotaAdapter
import com.matricula.mobile.apiService.GrupoService
import com.matricula.mobile.app.MainActivity
import com.matricula.mobile.models.Grupo
import com.matricula.mobile.models.Usuario
import kotlinx.coroutines.*
import java.net.SocketTimeoutException


class RegistroNotasFragment : FragmentUtils() {

    lateinit var recyclerViewElement: RecyclerView
    lateinit var adaptador: RegistroNotaAdapter
    private lateinit var addsBtn: FloatingActionButton
    private lateinit var listaGrupo: ArrayList<Grupo>
    private lateinit var usuario: Usuario
    private val GrupoViewModel: GrupoViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_inscripciones, container, false)
        listaGrupo = ArrayList()
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
            changeFragment(InicioFragment())
        }
        GrupoViewModel.getGruposList()!!.observe(viewLifecycleOwner) { Grupos ->
            listaGrupo = Grupos as ArrayList<Grupo>
            refresh()
        }
        usuario = (activity as MainActivity?)!!.usuario()
        getListOfGrupo()
        adaptador = RegistroNotaAdapter(this.activity!!, listaGrupo)
        recyclerViewElement.adapter = adaptador
        return view;
    }

    private fun refresh() {
        if (GrupoViewModel.check_state() == true) {
            adaptador = RegistroNotaAdapter(this.activity!!, listaGrupo)
            recyclerViewElement.adapter = adaptador
            contract()
        } else {
            //hacer mensaje de error
        }
    }

    private fun contract() {
        val Grupo: Observer<Grupo> = object : Observer<Grupo> {
            @Override
            override fun onChanged(@Nullable ins: Grupo?) {
               GrupoViewModel.updateGrupo(ins!!)
                verEstudiantes()
            }
        }
        adaptador.getGrupoActual()!!.observe(this, Grupo)
    }

    private fun verEstudiantes(){
        setToolbarTitle("Alumnos")
        changeFragment(NotasAlumnoFragment())
    }
    private fun editar(){
//        val editar = EditarGrupoFragment()
//        setToolbarTitle("Editar Grupo")
//        var bundle =  Bundle();
//        var Grupo=adaptador.getGrupoActual()!!.value
//        val gson = Gson()
//        var json=gson.toJson(Grupo)
//        bundle.putString("Grupo", json)
//        editar.arguments=bundle
//        changeFragment(editar)
    }


    private fun  getListOfGrupo() {
        initLoading()
        CoroutineScope(Dispatchers.IO).launch {
            try {
            val call = GrupoService.getInstance().obtenerGrupoPorProfesor(usuario.id)
            val nGrupos = call.body()
            if (call.isSuccessful) {
                withContext(Dispatchers.Main) {
                    GrupoViewModel.setState(true)
                    GrupoViewModel.updateModel(nGrupos!!)
                    stopLoading()
                }
            } else {
                withContext(Dispatchers.Main) {
                    GrupoViewModel.setState(false)
                    GrupoViewModel.setMensaje(call.message())
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

