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
import com.google.gson.Gson
import com.matricula.mobile.viewModels.UsuarioViewModel
import com.matricula.mobile.R
import com.matricula.mobile.adapter.UsuarioAdapter
import com.matricula.mobile.apiService.UsuarioService
import com.matricula.mobile.models.Usuario
import kotlinx.coroutines.*
import java.net.SocketTimeoutException


class UsuariosFragment : FragmentUtils() {

    lateinit var recyclerViewElement: RecyclerView
    lateinit var adaptador: UsuarioAdapter
    private lateinit var listaUsuario: ArrayList<Usuario>
    private val UsuarioViewModel: UsuarioViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_usuarios, container, false)
        listaUsuario = ArrayList()
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
        UsuarioViewModel.getUsuariosList()!!.observe(viewLifecycleOwner) { Usuarios ->
            listaUsuario = Usuarios as ArrayList<Usuario>
            refresh()
        }
        getListOfUsuarios()
        adaptador = UsuarioAdapter(this.activity!!, listaUsuario)
        recyclerViewElement.adapter = adaptador
        return view;
    }

    private fun refresh() {
        if (UsuarioViewModel.check_state() == true) {
            adaptador = UsuarioAdapter(this.activity!!, listaUsuario)
            recyclerViewElement.adapter = adaptador
            contract()
        } else {
            //hacer mensaje de error
        }
    }

    private fun contract() {
        val Usuario: Observer<Usuario> = object : Observer<Usuario> {
            @Override
            override fun onChanged(@Nullable Usuarios: Usuario?) {
                val editar = EditarUsuarioFragment()
                setToolbarTitle("Editar Usuario")
                var bundle = Bundle();
                var Usuario = adaptador.getUsuarioActual()!!.value
                val gson = Gson()
                var json = gson.toJson(Usuario)
                bundle.putString("Usuario", json)
                editar.arguments = bundle
                changeFragment(editar)
            }
        }
        adaptador.getUsuarioActual()!!.observe(this, Usuario)
    }

    private fun  getListOfUsuarios() {
        initLoading()
        CoroutineScope(Dispatchers.IO).launch {
            val call = UsuarioService.getInstance().obtenerUsuarios()
            val nUsuarios = call.body()
            if (call.isSuccessful) {
                withContext(Dispatchers.Main) {
                    UsuarioViewModel.setState(true)
                    UsuarioViewModel.updateModel(nUsuarios!!)
                    stopLoading()
                }
            } else {
                withContext(Dispatchers.Main) {
                    UsuarioViewModel.setState(false)
                    UsuarioViewModel.setMensaje(call.message())
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

