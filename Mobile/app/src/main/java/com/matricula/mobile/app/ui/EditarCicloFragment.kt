package com.matricula.mobile.app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import com.google.android.material.snackbar.Snackbar
import com.matricula.mobile.R
import com.matricula.mobile.apiService.CicloService
import com.matricula.mobile.models.Ciclo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditarCicloFragment: FragmentUtils() {
    private lateinit var editTextAnnio : EditText
    private lateinit var editTextFechaInicio : EditText
    private lateinit var editTextFechaFinal : EditText
    private lateinit var  numero:String
    lateinit var option:Spinner
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_crear_ciclos, container, false)

        view.findViewById<Button>(R.id.btn_guardarCycle).setOnClickListener{
            crearCiclo()
        }

        view.findViewById<Button>(R.id.btn_volver_cycle).setOnClickListener {
            volver()
        }
        var options= arrayOf("I","II","III")
        editTextAnnio= view.findViewById(R.id.editText_annio)
        editTextFechaInicio= view.findViewById(R.id.editTextTextFechaInicio)
        editTextFechaFinal= view.findViewById(R.id.editTextTextPersonName2)
        option=view.findViewById(R.id.ciclos_num)
        option.adapter=ArrayAdapter(this.activity!!,android.R.layout.simple_list_item_1,options)
        option.onItemSelectedListener=object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                numero=options.get(p2)
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
        return view
    }
    private fun crearCiclo() {
        var num = toInt(numero)
        var annio = editTextAnnio?.text.toString()
        var fechaIni = editTextFechaInicio?.text.toString()
        var fechaFin = editTextFechaFinal?.text.toString()
        try {
            var ciclo = Ciclo(0,annio.toInt(),num.toInt(), 0, fechaIni, fechaFin)
            if (validarDatos()) {
                insertarCiclo(ciclo)
            }else{
                val  message = "Debe Completar todos los datos!!"
                Snackbar
                    .make(view!!, message!!, Snackbar.LENGTH_LONG)
                    .setAction("Action", null)
                    .show()}
        }catch (e:Exception){

        }

    }

    private fun limpiar(){
        editTextAnnio?.setText("")
        editTextFechaInicio?.setText("")
        editTextFechaFinal?.setText("")
    }
    private fun volver(){
        setToolbarTitle("Inicio")
        changeFragment(CiclosFragment())
    }
    private fun validarDatos():Boolean{//Manejo de errores
        return(editTextAnnio?.text.toString()!=""&& editTextFechaInicio?.text.toString()!=""
                &&editTextFechaFinal.text.toString()!="")
    }

    private fun insertarCiclo(ciclo: Ciclo) {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val call = CicloService.getInstance().ingresarCiclo(ciclo)
                if (call.isSuccessful) {
                    withContext(Dispatchers.Main) {
                        limpiar()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                    }
                }
            }
        } catch (e: Exception) {

        }
    }

    private fun toInt(valor:String): Int {
        return when(valor){
            "I"-> 1
            "II"-> 2
            "III"-> 3
            else-> 0
        }
    }
}
