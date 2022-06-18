package com.matricula.mobile.app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import com.google.android.material.snackbar.Snackbar
import com.matricula.mobile.R
import com.matricula.mobile.apiService.CarreraService
import com.matricula.mobile.apiService.CursoService
import com.matricula.mobile.models.Carrera
import com.sistema.logicaDeNegocio.Curso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CrearCursoFragment: FragmentUtils() {
    private lateinit  var editTextName: EditText
    private lateinit var editTextCodigo : EditText
    private lateinit var editTextCreditos : EditText
    private lateinit var editTextHorasSemanales : EditText
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_crear_cursos, container, false)

        view.findViewById<Button>(R.id.btn_guardarCurso).setOnClickListener{
            this.crearCurso()
        }

        view.findViewById<Button>(R.id.btn_volver_curso).setOnClickListener {
            volver()
        }
        editTextName=view.findViewById(R.id.editText_Name_Curso)
        editTextCodigo= view.findViewById(R.id.editText_Codigo)
        editTextCreditos= view.findViewById(R.id.editText_Creditos)
        editTextHorasSemanales= view.findViewById(R.id.editTextTextHorasSemanales)
        return view
    }
    private fun crearCurso() {
        var name = editTextName?.text.toString()
        var codigo = editTextCodigo?.text.toString()
        var creditos = editTextCreditos?.text.toString()
        var hsemanles = editTextHorasSemanales?.text.toString()
        var curso = Curso(codigo, name, creditos.toInt(), hsemanles.toInt(), Carrera())
        if (validarDatos()) {
            insertarCurso(curso)
        }else{
            val  message = "Debe Completar todos los datos!!"
            Snackbar
                .make(view!!, message!!, Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show()}
    }

    private fun limpiar(){
        editTextName?.setText("")
        editTextCodigo?.setText("")
        editTextCreditos?.setText("")
        editTextHorasSemanales?.setText("")
    }
    private fun volver(){
        setToolbarTitle("Cursos")
        changeFragment(CursosFragment())
    }

    private fun initLoading(){
        val loader = view?.findViewById<ProgressBar>(R.id.loading)
        loader?.visibility=View.VISIBLE
    }

    private fun stopLoadingError(){
        val loader=view?.findViewById<ProgressBar>(R.id.loading)
        loader?.visibility=View.GONE
    }

    private fun stopLoadingSuccess(){
        val loader=view?.findViewById<ProgressBar>(R.id.loading)
        loader?.visibility=View.GONE
        val dialog=SuccessDiaglogFragment()
        val bundle = Bundle()
        bundle.putString("La carrera ${editTextName.text.toString()} con el código ${editTextCodigo.text.toString()} fue creada " +
                "correctamente", "mensaje")
        dialog.arguments=bundle
        dialog.show(childFragmentManager,"agregar")
        limpiar()
    }

    private fun validarDatos():Boolean{//Manejo de errores
        return(editTextName?.text.toString()!="" &&
                editTextCodigo?.text.toString()!=""&& editTextCodigo?.text.toString()!="")
    }

    private fun insertarCurso(curso: Curso){
        initLoading()
        CoroutineScope(Dispatchers.IO).launch {
            val call = CursoService.getInstance().ingresarCurso(curso)
            if (call.isSuccessful) {
                withContext(Dispatchers.Main) {
                    stopLoadingSuccess()
                }
            } else {
                withContext(Dispatchers.Main) {
                    stopLoadingError()
                }
            }
        }
    }
}
