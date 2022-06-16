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
import com.matricula.mobile.models.Carrera
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CrearCarreraFragment: FragmentUtils() {
    private lateinit  var editTextName:EditText
    private lateinit var editTextCodigo :EditText
    private lateinit var editTexttitulo :EditText
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_crear_carrera, container, false)

        view.findViewById<Button>(R.id.btn_guardarCareer).setOnClickListener{
            this.crearCarrera()
        }

        view.findViewById<Button>(R.id.btn_volver_career).setOnClickListener {
            volver()
        }
        editTexttitulo=view.findViewById(R.id.editText_Title_Career)
        editTextName=view.findViewById(R.id.editText_Name_Career)
       editTextCodigo= view.findViewById(R.id.editText_Code_Career)
        return view
    }
    private fun crearCarrera() {
        var name = editTextName?.text.toString()
        var codigo = editTextCodigo?.text.toString()
        var titulo = editTexttitulo?.text.toString()
        var carrera = Carrera(codigo, name, titulo)
        if (validarDatos()) {
            insertarCarrera(carrera)
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
        editTexttitulo?.setText("")
    }
    private fun volver(){
        setToolbarTitle("Carreras")
        changeFragment(CarrerasFragment())
    }

    private fun initLoading(){
        val loader = view?.findViewById<ProgressBar>(R.id.loading)
        loader?.visibility=View.VISIBLE
    }

    private fun stopLoadingError(){
        val loader=view?.findViewById<ProgressBar>(R.id.loading)
        loader?.visibility=View.GONE
        val dialog=SuccessDiaglogFragment()//uno de estos pero que diga error Dialog con rojo y una X
        dialog.show(childFragmentManager,"agregar")
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
                editTextCodigo?.text.toString()!=""&& editTexttitulo?.text.toString()!="")
    }

    private fun insertarCarrera(carrera: Carrera){
        initLoading()
        CoroutineScope(Dispatchers.IO).launch {
            val call = CarreraService.getInstance().ingresarCarrera(carrera)
            if (call.isSuccessful) {
                withContext(Dispatchers.Main) {
                    stopLoadingSuccess()
                }
            } else {
            stopLoadingError()
            }
        }
    }
}
