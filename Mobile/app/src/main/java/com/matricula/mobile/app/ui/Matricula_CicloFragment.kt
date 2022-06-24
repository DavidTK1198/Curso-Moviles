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
import com.matricula.mobile.adapter.Oferta_CicloAdapter
import com.matricula.mobile.apiService.CicloService
import com.matricula.mobile.models.Ciclo
import com.matricula.mobile.viewModels.CicloViewModel
import com.matricula.mobile.viewModels.GrupoViewModel
import kotlinx.coroutines.*


class Matricula_CicloFragment : FragmentUtils() {
    lateinit var recyclerViewElement: RecyclerView
    lateinit var adaptador: Oferta_CicloAdapter
    private lateinit var addsBtn: FloatingActionButton
    private lateinit var listaCiclo: ArrayList<Ciclo>
    private val grupoViewModel: GrupoViewModel by activityViewModels()
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
            setToolbarTitle("")
            changeFragment(MatriculaFragment())
        }
        cicloViewModel.getCiclosList()!!.observe(viewLifecycleOwner) { ciclos ->
            listaCiclo = ciclos as ArrayList<Ciclo>
            refresh()
        }
        getListOfCiclos()
        adaptador = Oferta_CicloAdapter(this.activity!!, listaCiclo)
        recyclerViewElement.adapter = adaptador
        return view;
    }

    private fun refresh() {
        if (cicloViewModel.check_state() == true) {
            adaptador = Oferta_CicloAdapter(this.activity!!, listaCiclo)
            recyclerViewElement.adapter = adaptador
            contract()
        } else {
            //hacer mensaje de error
        }
    }

    private fun contract() {
        val Ciclo: Observer<Ciclo> = object : Observer<Ciclo> {
            @Override
            override fun onChanged(@Nullable Ciclos: Ciclo?) {
                volverCiclo()
            }
        }
        adaptador.getCicloActual()!!.observe(this, Ciclo)
    }

    private fun volverCiclo(){
        var Ciclo= adaptador.getCicloActual()!!.value
        grupoViewModel.updateCiclo(Ciclo!!)
        val a = MatriculaFragment()
        setToolbarTitle("Gestionar Matricula")
        changeFragment(a)
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

