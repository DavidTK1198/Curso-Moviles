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
import com.matricula.mobile.apiService.CicloService
import com.matricula.mobile.models.Ciclo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CrearCicloFragment: FragmentUtils() {
        private lateinit  var editTextNumero: EditText
        private lateinit var editTextCodigo : EditText
        private lateinit var editTextAnnio : EditText
        private lateinit var editTextFechaInicio : EditText
        private lateinit var editTextFechaFinal : EditText
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_crear_ciclos, container, false)

        view.findViewById<Button>(R.id.btn_guardarCycle).setOnClickListener{
            this.crearCurso()
        }

        view.findViewById<Button>(R.id.btn_volver_cycle).setOnClickListener {
            volver()
        }
        editTextNumero=view.findViewById(R.id.editText_Numero)
        editTextCodigo= view.findViewById(R.id.editText_Codigo)
        editTextAnnio= view.findViewById(R.id.editText_annio)
        editTextFechaInicio= view.findViewById(R.id.editTextTextFechaInicio)
        editTextFechaFinal= view.findViewById(R.id.editTextTextPersonName2)
        return view
    }
    private fun crearCurso() {
        var num = editTextNumero?.text.toString()
        var codigo = editTextCodigo?.text.toString()
        var annio = editTextAnnio?.text.toString()
        var fechaIni = editTextFechaInicio?.text.toString()
        var fechaFin = editTextFechaFinal?.text.toString()
        var ciclo = Ciclo(num.toInt(), codigo.toInt(), annio.toInt(), 1, fechaIni, fechaFin)
        if (validarDatos()) {
           // insertarCiclo(ciclo)
        }else{
            val  message = "Debe Completar todos los datos!!"
            Snackbar
                .make(view!!, message!!, Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show()}
    }

    private fun limpiar(){
        editTextNumero?.setText("")
        editTextCodigo?.setText("")
        editTextAnnio?.setText("")
        editTextFechaInicio?.setText("")
        editTextFechaFinal?.setText("")
    }
    private fun volver(){
        setToolbarTitle("Ciclos")
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
        bundle.putString("La carrera ${editTextNumero.text.toString()} con el código ${editTextCodigo.text.toString()} fue creada " +
                "correctamente", "mensaje")
        dialog.arguments=bundle
        dialog.show(childFragmentManager,"agregar")
        limpiar()
    }

    private fun validarDatos():Boolean{//Manejo de errores
        return(editTextNumero?.text.toString()!="" &&
                editTextCodigo?.text.toString()!=""&& editTextCodigo?.text.toString()!="")
    }

//    private fun insertarCiclo(ciclo: Ciclo){
//        initLoading()
//        CoroutineScope(Dispatchers.IO).launch {
//           // val call = CicloService.getInstance().ingresarCiclo(ciclo)
//            if (call.isSuccessful) {
//                withContext(Dispatchers.Main) {
//                    stopLoadingSuccess()
//                }
//            } else {
//                withContext(Dispatchers.Main) {
//                    stopLoadingError()
//                }
//            }
//        }
//    }
}
