package com.matricula.mobile.app.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.matricula.mobile.R
import com.matricula.mobile.apiService.CursoService
import com.matricula.mobile.models.Carrera
import com.matricula.mobile.models.Curso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException

class EditarCursoFragment: FragmentUtils() {

    private lateinit  var editTextName: EditText
    private lateinit var editTextCodigo : EditText
    private lateinit var editTextCreditos : EditText
    private lateinit var editTextHorasSemanales : EditText
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_editar_cursos, container, false)

        view.findViewById<Button>(R.id.btn_guardarCurso_E).setOnClickListener{
            this.crearCurso()
        }

        view.findViewById<Button>(R.id.btn_volver_curso_E).setOnClickListener {
            volver()
        }
        editTextName=view.findViewById(R.id.editText_Name_Curso_E)
        editTextCodigo= view.findViewById(R.id.editText_Codigo_E)
        editTextCreditos= view.findViewById(R.id.editText_Creditos_E)
        editTextHorasSemanales= view.findViewById(R.id.editTextTextHorasSemanales_E)
        val bundle = this.arguments
        if (bundle != null) {
            val json = bundle.get("curso").toString() // Key
            val gson = Gson()
            val curso = gson.fromJson(json, Curso::class.java)
            rellenar(curso)
        }else{
            setToolbarTitle("Cursos")
            changeFragment(CarrerasFragment())
        }
        return view
    }
    private fun crearCurso() {
        var name = editTextName?.text.toString()
        var codigo = editTextCodigo?.text.toString()
        var creditos = editTextCreditos?.text.toString()
        var hsemanles = editTextHorasSemanales?.text.toString()
        var curso = Curso(codigo, name, creditos.toInt(), hsemanles.toInt(), Carrera())
        if (validarDatos()) {
            modificarCurso(curso)
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

    private  fun rellenar(curso: Curso){
        editTextName?.setText(curso.nombre)
        editTextCodigo?.setText(curso.codigo)
        editTextCreditos?.setText(curso.creditos.toString())
        editTextHorasSemanales?.setText(curso.hsemanales.toString())
    }
    private fun volver(){
        setToolbarTitle("Cursos")
        changeFragment(CursosFragment())
    }

    private fun initLoading(){
        val loader = view?.findViewById<ProgressBar>(R.id.loading)
        loader?.visibility= View.VISIBLE
    }

    private fun stopLoading(){
        val loader=view?.findViewById<ProgressBar>(R.id.loading)
        loader?.visibility= View.GONE
    }



    private fun validarDatos():Boolean{//Manejo de errores
        return(editTextName?.text.toString()!="" &&
                editTextCodigo?.text.toString()!=""&& editTextCodigo?.text.toString()!="")
    }

    private fun modificarCurso(curso: Curso) {
        initLoading()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val call = CursoService.getInstance().modificarCurso(curso)
                if (call.isSuccessful) {
                    withContext(Dispatchers.Main) {
                        stopLoading()
                    }
                } else {
                    stopLoading()
                }
            } catch (e: SocketTimeoutException) {
                Log.d("xd", "mamado")
            }
        }
    }
}