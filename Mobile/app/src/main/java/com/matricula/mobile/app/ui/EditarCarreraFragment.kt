package com.matricula.mobile.app.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.matricula.mobile.R
import com.matricula.mobile.apiService.CarreraService
import com.matricula.mobile.models.Carrera
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditarCarreraFragment: FragmentUtils() {

    private lateinit  var editTextName: EditText
    private lateinit var editTextCodigo : EditText
    private lateinit var editTexttitulo : EditText
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_editar_carreras, container, false)

        view.findViewById<Button>(R.id.editar_guardarCareer).setOnClickListener{
            this.crearCarrera()
        }

        view.findViewById<Button>(R.id.editar_volver_career).setOnClickListener {
            volver()
        }
        editTexttitulo=view.findViewById(R.id.edit_Title_Career)
        editTextName=view.findViewById(R.id.edit_Name_Career)
        editTextCodigo= view.findViewById(R.id.edit_Code_Career)
        val bundle = this.arguments
        if (bundle != null) {
            val json = bundle.get("carrera").toString() // Key
            val gson = Gson()
            val carrera = gson.fromJson(json,Carrera::class.java)
            rellenar(carrera)
        }else{
            setToolbarTitle("Carreras")
            changeFragment(CarrerasFragment())
        }
        return view
    }
    private fun crearCarrera() {
        var name = editTextName?.text.toString()
        var codigo = editTextCodigo?.text.toString()
        var titulo = editTexttitulo?.text.toString()
        var carrera = Carrera(codigo, name, titulo)
        if (validarDatos()) {
            modificarCarrera(carrera)
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

    private  fun rellenar(carrera: Carrera){
        editTextName?.setText(carrera.nombre)
        editTextCodigo?.setText(carrera.codigo)
        editTexttitulo?.setText(carrera.titulo)
    }
    private fun volver(){
        setToolbarTitle("Carreras")
        changeFragment(CarrerasFragment())
    }

    private fun initLoading(){
        val loader = view?.findViewById<ProgressBar>(R.id.loading)
        loader?.visibility= View.VISIBLE
    }

    private fun stopLoading(){
        val loader=view?.findViewById<ProgressBar>(R.id.loading)
        loader?.visibility= View.GONE
        AlertDialog.Builder(this.activity!!)
            .setTitle("Resultado")
            .setIcon(R.drawable.ic_success)
            .setMessage("Modificado correctamente!!!")
            .setPositiveButton("Ok"){ dialog,_->
                dialog.dismiss()
            }
            .create()
            .show()
    }



    private fun validarDatos():Boolean{//Manejo de errores
        return(editTextName?.text.toString()!="" &&
                editTextCodigo?.text.toString()!=""&& editTexttitulo?.text.toString()!="")
    }

    private fun modificarCarrera(carrera: Carrera){
        initLoading()
        CoroutineScope(Dispatchers.IO).launch {
            try {
            val call = CarreraService.getInstance().modificarCarrera(carrera)
            if (call.isSuccessful) {
                withContext(Dispatchers.Main) {
                    stopLoading()
                }
            } else {
                stopLoading()
            }
            } catch (e: Exception) {

            }
        }
    }
}