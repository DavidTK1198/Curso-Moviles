package com.matricula.mobile.app.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.fragment.app.activityViewModels
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.matricula.mobile.R
import com.matricula.mobile.apiService.AlumnoService
import com.matricula.mobile.apiService.InscripcionService
import com.matricula.mobile.models.Alumno
import com.matricula.mobile.models.Carrera
import com.matricula.mobile.viewModels.AlumnoViewModel
import com.matricula.mobile.viewModels.InscripcionViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NotaAlumnoFragment : FragmentUtils() {
    private lateinit  var editTextName: EditText
    private lateinit var editTextCodigo : EditText
    private lateinit var nota : EditText
    private val InscripcionViewModel: InscripcionViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.nota_alumno, container, false)

        view.findViewById<Button>(R.id.btn_guardar_alumno).setOnClickListener{
                ponerNota()
        }
   
        view.findViewById<Button>(R.id.btn_volver_alumno).setOnClickListener {
            volver()
        }
        editTextName=view.findViewById(R.id.editText_Name_al)
        editTextCodigo= view.findViewById(R.id.editText_Cedula_al)
        nota=view.findViewById(R.id.nota_esSet)
        editTextCodigo.isEnabled=false
        editTextName.isEnabled=false
        val carrera= view.findViewById<EditText>(R.id.CarreraAl)
        if(InscripcionViewModel.getInscripcion()!=null)
            rellenar()
     
      
        return view
    }

    private fun rellenar() {
        var alumno=InscripcionViewModel.getInscripcion()!!.estudiante
         editTextName?.setText(alumno.nombre)
         editTextCodigo?.setText(alumno!!.cedula)
        nota.setText(InscripcionViewModel.getInscripcion()!!.nota.toString())
    }

    private fun volver(){
        setToolbarTitle("Alumnos")
        changeFragment(NotasAlumnoFragment())
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
    }

    private fun validarDatos():Boolean{//Manejo de errores
    return false
    }

    private fun ponerNota(){
        initLoading()
        val ins=InscripcionViewModel.getInscripcion()
        var nota=nota.text.toString().toInt()
        ins!!.nota=nota
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val call = InscripcionService.getInstance().colocarNota(ins!!)
                if (call.isSuccessful) {
                    withContext(Dispatchers.Main) {
                        stopLoadingSuccess()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        stopLoadingError()
                    }
                }
            } catch (e: Exception) {

            }
        }
    }
}