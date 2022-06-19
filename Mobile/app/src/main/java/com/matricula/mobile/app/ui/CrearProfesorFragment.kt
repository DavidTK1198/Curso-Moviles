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
import com.matricula.mobile.apiService.ProfesorService
import com.matricula.mobile.models.Profesor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CrearProfesorFragment: FragmentUtils() {
    private lateinit  var editTextName:EditText
    private lateinit var editTextCodigo :EditText
    private lateinit var editTexttelefono :EditText
    private lateinit var editTextmail :EditText
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_crear_profesor, container, false)

        view.findViewById<Button>(R.id.btn_guardarP).setOnClickListener{
            this.crearProfesor()
        }

        view.findViewById<Button>(R.id.btn_volver_profesor).setOnClickListener {
            volver()
        }
        editTexttelefono=view.findViewById(R.id.editText_Tel_profesor)
        editTextName=view.findViewById(R.id.editText_Name_Professor)
        editTextCodigo= view.findViewById(R.id.editText_Cedula_prof)
        editTextmail=view.findViewById(R.id.editText_mail)
        return view
    }
    private fun crearProfesor() {
        var name = editTextName?.text.toString()
        var ced = editTextCodigo?.text.toString()
        var tel = editTexttelefono?.text.toString()
        var mail=editTextmail?.text.toString()
        var Profesor = Profesor(ced,name,tel, mail)
        if (validarDatos()) {
            insertarProfesor(Profesor)
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
        editTexttelefono?.setText("")
        editTextmail?.setText("")
    }
    private fun volver(){
        setToolbarTitle("Profesores")
        changeFragment(ProfesoresFragment())
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
        bundle.putString("La Profesor ${editTextName.text.toString()} con el código ${editTextCodigo.text.toString()} fue creada " +
                "correctamente", "mensaje")
        dialog.arguments=bundle
        dialog.show(childFragmentManager,"agregar")
        limpiar()
    }

    private fun validarDatos():Boolean{//Manejo de errores
        return(editTextName?.text.toString()!="" &&
                editTextCodigo?.text.toString()!=""&& editTexttelefono?.text.toString()!=""
                &&editTextmail?.text.toString()!="")
    }

    private fun insertarProfesor(Profesor: Profesor){
        initLoading()
        CoroutineScope(Dispatchers.IO).launch {
            val call = ProfesorService.getInstance().ingresarProfesor(Profesor)
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