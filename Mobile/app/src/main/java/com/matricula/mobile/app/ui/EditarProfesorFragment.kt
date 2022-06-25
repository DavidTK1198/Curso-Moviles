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
import com.matricula.mobile.apiService.ProfesorService
import com.matricula.mobile.models.Carrera
import com.matricula.mobile.models.Profesor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditarProfesorFragment: FragmentUtils() {

    private lateinit  var editTextName: EditText
    private lateinit var editTextCodigo : EditText
    private lateinit var editTexttelefono : EditText
    private lateinit var editTextmail : EditText
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_edit_profesor, container, false)

        view.findViewById<Button>(R.id.btn_guardarPE).setOnClickListener{
            this.crearProfesor()
        }

        view.findViewById<Button>(R.id.btn_volver_pe).setOnClickListener {
            volver()
        }
        editTexttelefono=view.findViewById(R.id.editText_Tel_pe)
        editTextName=view.findViewById(R.id.editText_Name_Professor_edit)
        editTextCodigo= view.findViewById(R.id.editText_Cedula_pE)
        editTextmail=view.findViewById(R.id.editText_telP)
        val bundle = this.arguments
        if (bundle != null) {
            val json = bundle.get("profesor").toString() // Key
            val gson = Gson()
            val p = gson.fromJson(json, Profesor::class.java)
            rellenar(p)
        }else{
            setToolbarTitle("Carreras")
            changeFragment(CarrerasFragment())
        }
        return view
    }
    private fun crearProfesor() {
        var name = editTextName?.text.toString()
        var ced = editTextCodigo?.text.toString()
        var tel = editTexttelefono?.text.toString()
        var mail=editTextmail?.text.toString()
        var Profesor = Profesor(ced,name,tel, mail)
        if (validarDatos()) {
            modificarProfesor(Profesor)
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
        loader?.visibility= View.VISIBLE
    }

    private fun stopLoadingError(){
        val loader=view?.findViewById<ProgressBar>(R.id.loading)
        loader?.visibility= View.GONE
    }

    private fun stopLoadingSuccess(){
        val loader=view?.findViewById<ProgressBar>(R.id.loading)
        loader?.visibility= View.GONE
        AlertDialog.Builder(this.activity!!)
            .setTitle("Resultado")
            .setIcon(R.drawable.ic_success)
            .setMessage("Creado correctamente!!!")
            .setPositiveButton("Ok"){ dialog,_->
                dialog.dismiss()
            }
            .create()
            .show()
        limpiar()
    }

    private fun validarDatos():Boolean{//Manejo de errores
        return(editTextName?.text.toString()!="" &&
                editTextCodigo?.text.toString()!=""&& editTexttelefono?.text.toString()!=""
                &&editTextmail?.text.toString()!="")
    }

    private fun modificarProfesor(Profesor: Profesor){
        initLoading()
        CoroutineScope(Dispatchers.IO).launch {
            val call = ProfesorService.getInstance().modificarProfesor(Profesor)
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

    private  fun rellenar(profesor: Profesor){
        editTextName?.setText(profesor.nombre)
        editTextCodigo?.setText(profesor.cedula)
        editTexttelefono?.setText(profesor.teléfono)
        editTextmail?.setText(profesor.email)
    }
}