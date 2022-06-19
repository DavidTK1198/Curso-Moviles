package com.matricula.mobile.app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.fragment.app.activityViewModels
import com.google.android.material.snackbar.Snackbar
import com.matricula.mobile.R
import com.matricula.mobile.apiService.AlumnoService
import com.matricula.mobile.models.Alumno
import com.matricula.mobile.viewModels.AlumnoViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CrearAlumnoFragment: FragmentUtils() {
    private lateinit  var editTextName: EditText
    private lateinit var editTextCodigo : EditText
    private lateinit var editTexttelefono : EditText
    private lateinit var editTextmail : EditText
    private lateinit var fec_nac : EditText
    private val alumnoViewModel: AlumnoViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_crear_alumno, container, false)

        view.findViewById<Button>(R.id.btn_guardar_alumno).setOnClickListener{
            this.crearAlumno()
        }
        view.findViewById<Button>(R.id.cargarCarrera).setOnClickListener{
            this.selecionar()
        }
        view.findViewById<Button>(R.id.btn_volver_alumno).setOnClickListener {
            volver()
        }
        editTexttelefono=view.findViewById(R.id.editText_Tel_al)
        editTextName=view.findViewById(R.id.editText_Name_al)
        editTextCodigo= view.findViewById(R.id.editText_Cedula_al)
        editTextmail=view.findViewById(R.id.editTextEmail_al)
        fec_nac=view.findViewById(R.id.editTextTextfec_al2)
        return view
    }
    private fun crearAlumno() {
        var name = editTextName?.text.toString()
        var ced = editTextCodigo?.text.toString()
        var tel = editTexttelefono?.text.toString()
        var mail=editTextmail?.text.toString()
        var carrera=alumnoViewModel.getCarrera()
        //var Alumno = Alumno(ced,name,tel, mail)
        if (validarDatos()) {
          //  insertarAlumno(Alumno)
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
        setToolbarTitle("Alumnos")
        changeFragment(AlumnosFragment())
    }

    private fun selecionar(){
        setToolbarTitle("Selecionar Carrera")
        changeFragment(Alumno_CarreraFragment())
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
        val dialog=SuccessDiaglogFragment()
        val bundle = Bundle()
        bundle.putString("La Alumno ${editTextName.text.toString()} con el código ${editTextCodigo.text.toString()} fue creada " +
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

    private fun insertarAlumno(Alumno: Alumno){
        initLoading()
        CoroutineScope(Dispatchers.IO).launch {
            val call = AlumnoService.getInstance().ingresarAlumno(Alumno)
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