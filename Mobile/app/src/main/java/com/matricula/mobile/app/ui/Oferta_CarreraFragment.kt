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
import com.matricula.mobile.adapter.Alumno_CarreraAdapter
import com.matricula.mobile.apiService.CarreraService
import com.matricula.mobile.models.Carrera
import com.matricula.mobile.viewModels.CarreraViewModel
import kotlinx.coroutines.*
import java.net.SocketTimeoutException


class Oferta_CarreraFragment: FragmentUtils() {
    lateinit var recyclerViewElement: RecyclerView
    lateinit var adaptador: Alumno_CarreraAdapter
    private lateinit var addsBtn: FloatingActionButton
    private lateinit var listaCarrera: ArrayList<Carrera>
    private val carreraViewModel: CarreraViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_carreras, container, false)
        listaCarrera = ArrayList()
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
        carreraViewModel.getCarrerasList()!!.observe(viewLifecycleOwner) { carreras ->
            listaCarrera = carreras as ArrayList<Carrera>
            refresh()
        }
        getListOfCarreras()
        adaptador = Alumno_CarreraAdapter(this.activity!!, listaCarrera)
        recyclerViewElement.adapter = adaptador
        return view;
    }

    private fun refresh() {
        if (carreraViewModel.check_state() == true) {
            adaptador = Alumno_CarreraAdapter(this.activity!!, listaCarrera)
            recyclerViewElement.adapter = adaptador
            contract()
        } else {
            //hacer mensaje de error
        }
    }

    private fun contract() {
        val carrera: Observer<Carrera> = object : Observer<Carrera> {
            @Override
            override fun onChanged(@Nullable carreras: Carrera?) {
                volverOferta()
            }
        }
        adaptador.getCarreraActual()!!.observe(this, carrera)
    }

    private fun volverOferta(){
        var carrera= adaptador.getCarreraActual()!!.value
        carreraViewModel.setCarrera(carrera!!)
        val a = OfertaFragment()
        setToolbarTitle("Oferta Académica")
        changeFragment(a)
    }

    private fun  getListOfCarreras() {
        initLoading()
        CoroutineScope(Dispatchers.IO).launch {
            val call = CarreraService.getInstance().obtenerCarreras()
            val nCarreras = call.body()
            if (call.isSuccessful) {
                withContext(Dispatchers.Main) {
                    carreraViewModel.setState(true)
                    carreraViewModel.updateModel(nCarreras!!)
                    stopLoading()
                }
            } else {
                withContext(Dispatchers.Main) {
                    carreraViewModel.setState(false)
                    carreraViewModel.setMensaje(call.message())
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

