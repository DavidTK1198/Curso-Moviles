package com.matricula.mobile.app.ui

import android.app.AlertDialog
import android.os.Bundle
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
import com.matricula.mobile.R
import com.matricula.mobile.adapter.MatriculaAdapter
import com.matricula.mobile.adapter.Oferta_CursoAdapter
import com.matricula.mobile.apiService.GrupoService
import com.matricula.mobile.apiService.InscripcionService
import com.matricula.mobile.models.Grupo
import com.matricula.mobile.models.Inscripcion
import com.matricula.mobile.viewModels.AlumnoViewModel
import com.matricula.mobile.viewModels.GrupoViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException


class MatricularFragment: FragmentUtils() {

    lateinit var recyclerViewElement: RecyclerView
    lateinit var adaptador: MatriculaAdapter
    private lateinit var listaGrupo: ArrayList<Grupo>
    private  lateinit var  ciclo:Button
    private lateinit var backBtn: FloatingActionButton
    private val grupoViewModel: GrupoViewModel by activityViewModels()
    private val alumnoViewModel: AlumnoViewModel by activityViewModels()
    private lateinit var carrera:Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_matricular, container, false)
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

        ciclo= view.findViewById<Button>(R.id.Ciclocarga)
        ciclo.setOnClickListener{
            setToolbarTitle("Ciclos")
            changeFragment(Matricula_CicloFragment())
        }
        backBtn = view.findViewById(R.id.volverMain)
        backBtn.setOnClickListener { view ->
            setToolbarTitle("Inicio")
            changeFragment(InicioFragment())
        }

        if(grupoViewModel.getCiclo()!=null){
            ciclo.setText("Ciclo ${convertToRoman(grupoViewModel.getCiclo()!!.numero)}-${grupoViewModel.getCiclo()!!.annio}")
        }

        var cursos= view.findViewById<Button>(R.id.CargarCursos)
        cursos.setOnClickListener{
            getListOfGrupos()
        }
        grupoViewModel.getGruposList()!!.observe(viewLifecycleOwner) { Grupos ->
            listaGrupo = Grupos as ArrayList<Grupo>
            refresh()
        }
        return view;
    }

    private fun refresh() {
        if (grupoViewModel.check_state() == true) {
            adaptador = MatriculaAdapter(this.activity!!, listaGrupo)
            recyclerViewElement.adapter = adaptador
            contract()
        } else {
            //hacer mensaje de error
        }
    }

    private fun contract() {
        val carrera: Observer<Grupo> = object : Observer<Grupo> {
            @Override
            override fun onChanged(@Nullable Grupos: Grupo?) {
                grupoViewModel.updateGrupo(Grupos!!)
                matricular()
            }
        }
        adaptador.getGrupoActual()!!.observe(this, carrera)
    }

    private fun matricular() {
        val grupo=grupoViewModel.getGrupo()
        val alumno=alumnoViewModel.getAlumno()
        var ins= Inscripcion(alumno!!.value!!,0,grupo!!)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val call = InscripcionService.getInstance().matricular(ins)
                val nGrupos = call.body()
                if (call.isSuccessful) {
                    withContext(Dispatchers.Main) {
                        grupoViewModel.setState(true)
                        stopLoading()
                        mensaje()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        grupoViewModel.setState(false)
                        grupoViewModel.setMensaje(call.message())
                        stopLoading()
                    }//mensaje de error del servidor...
                }
            }catch (e:SocketTimeoutException){

            }
        }
    }

    private fun mensaje(){
        AlertDialog.Builder(this.activity!!)
            .setTitle("Resultado")
            .setIcon(R.drawable.ic_success)
            .setMessage("Matriculado correctamente!!!")
            .setPositiveButton("Ok"){ dialog,_->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private  fun  validar(): Boolean {
        if (ciclo.text.toString()==" Debe Cargar un Ciclo") {
            AlertDialog.Builder(this.activity!!)
                .setTitle("Alerta")
                .setIcon(R.drawable.ic_warning)
                .setMessage("Debe selecionar tanto el ciclo")
                .setPositiveButton("Ok") { dialog, _ ->
                    dialog.dismiss()
                }
                .create()
                .show()
            return false
        }
        return true
    }

    private fun  getListOfGrupos() {
        if(validar()) {
            initLoading()
            var car = grupoViewModel.getCiclo()
            CoroutineScope(Dispatchers.IO).launch {
                try {
                val call = GrupoService.getInstance().obtenerGruposPorCiclo(car!!.id.toString())
                val nGrupos = call.body()
                if (call.isSuccessful) {
                    withContext(Dispatchers.Main) {
                        grupoViewModel.setState(true)
                        grupoViewModel.updateModel(nGrupos!!)
                        stopLoading()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        grupoViewModel.setState(false)
                        grupoViewModel.setMensaje(call.message())
                        stopLoading()
                    }//mensaje de error del servidor...
                }
                }catch (e:SocketTimeoutException){

                }
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

    private  fun convertToRoman(int: Int): String {
        when (int) {
            1 -> return "I"
            2 -> return "II"
            3 -> return "III"
            else->return ""
        }
    }
}