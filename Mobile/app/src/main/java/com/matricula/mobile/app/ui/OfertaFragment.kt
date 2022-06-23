package com.matricula.mobile.app.ui

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
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
import com.matricula.mobile.adapter.Oferta_CursoAdapter
import com.matricula.mobile.apiService.CursoService
import com.matricula.mobile.models.Carrera
import com.matricula.mobile.viewModels.CursoViewModel
import com.matricula.mobile.models.Curso
import com.matricula.mobile.viewModels.CarreraViewModel
import com.matricula.mobile.viewModels.GrupoViewModel
import kotlinx.android.synthetic.main.fragment_oferta.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException

class OfertaFragment: FragmentUtils() {

    lateinit var recyclerViewElement: RecyclerView
    lateinit var adaptador: Oferta_CursoAdapter
    private lateinit var listaCurso: ArrayList<Curso>
    private  lateinit var  ciclo:Button
    private lateinit var backBtn: FloatingActionButton
    private val cursoViewModel: CursoViewModel by activityViewModels()
    private val grupoViewModel: GrupoViewModel by activityViewModels()
    private val carreraViewModel: CarreraViewModel by activityViewModels()
  private lateinit var carrera:Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_oferta, container, false)
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

        ciclo= view.findViewById<Button>(R.id.Ciclocarga)
           ciclo.setOnClickListener{
            setToolbarTitle("Ciclos")
            changeFragment(Oferta_CicloFragment())
        }


        if(grupoViewModel.getCiclo()!=null){
           ciclo.setText("Ciclo ${convertToRoman(grupoViewModel.getCiclo()!!.numero)}-${grupoViewModel.getCiclo()!!.annio}")
        }


        var cursos= view.findViewById<Button>(R.id.CargarCursos)
        cursos.setOnClickListener{
         getListOfCursos()
        }
        carrera= view.findViewById<Button>(R.id.Carreracarga)
           carrera.setOnClickListener{
               setToolbarTitle("Carreras")
               changeFragment(Oferta_CarreraFragment())
        }
        if(carreraViewModel.getCarrera()!=null){
            carrera.setText(carreraViewModel!!.getCarrera()!!.nombre)
        }

        backBtn = view.findViewById(R.id.volverMain)
        backBtn.setOnClickListener { view ->
            setToolbarTitle("Inicio")
            changeFragment(InicioFragment())
        }
        cursoViewModel.getCursosList()!!.observe(viewLifecycleOwner) { cursos ->
            listaCurso = cursos as ArrayList<Curso>
            refresh()
        }
        adaptador = Oferta_CursoAdapter(this.activity!!, listaCurso)
        recyclerViewElement.adapter = adaptador
        return view;
    }

    private fun refresh() {
        if (cursoViewModel.check_state() == true) {
            adaptador = Oferta_CursoAdapter(this.activity!!, listaCurso)
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
                grupoViewModel.updateCurso(cursos!!)
                grupos()
            }
        }
        adaptador.getCursoActual()!!.observe(this, carrera)
    }

    private fun grupos() {
       val grupos=GruposFragment()
        setToolbarTitle("Grupos")
        changeFragment(grupos)

    }

        private  fun  validar(): Boolean {
            if (carrera.text.toString()=="Debe Cargar Una Carrera" || ciclo.text.toString()==" Debe Cargar un Ciclo") {
                AlertDialog.Builder(this.activity!!)
                    .setTitle("Alerta")
                    .setIcon(R.drawable.ic_warning)
                    .setMessage("Debe selecionar tanto el ciclo como la carrera")
                    .setPositiveButton("Ok") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .create()
                    .show()
                return false
            }
            return true
        }

    private fun  getListOfCursos() {
        if(validar()) {
            initLoading()
            var car = carreraViewModel.getCarrera()
            CoroutineScope(Dispatchers.IO).launch {
                val call = CursoService.getInstance().obtenerCursosPorCarrera(car!!.codigo!!)
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