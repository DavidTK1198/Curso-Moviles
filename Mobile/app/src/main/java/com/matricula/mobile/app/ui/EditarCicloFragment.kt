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
import com.google.gson.Gson
import com.matricula.mobile.R
import com.matricula.mobile.apiService.CicloService
import com.matricula.mobile.models.Carrera
import com.matricula.mobile.models.Ciclo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditarCicloFragment: FragmentUtils() {
    private lateinit var editTextAnnio : EditText
    private lateinit var editTextFechaInicio : EditText
    private lateinit var editTextFechaFinal : EditText
    private lateinit var editTextnum : EditText
    private  lateinit var estado:EditText
    private lateinit var ciclo: Ciclo
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_editar_ciclos, container, false)

        view.findViewById<Button>(R.id.btn_Estado).setOnClickListener{
            GestionarEstado()
        }

        view.findViewById<Button>(R.id.btn_volver_cycle).setOnClickListener {
            volver()
        }
        editTextAnnio= view.findViewById(R.id.editText_annio_E)
        editTextFechaInicio= view.findViewById(R.id.editTextTextFechaInicio_E)
        editTextFechaFinal= view.findViewById(R.id.editfecfinal)
        editTextnum= view.findViewById(R.id.editText_Numero_E)
        estado= view.findViewById(R.id.Estado)

        editTextFechaFinal.isEnabled=false
        editTextAnnio.isEnabled=false
        editTextFechaInicio.isEnabled=false
        editTextnum.isEnabled=false
        estado.isEnabled=false
        val bundle = this.arguments
        if (bundle != null) {
            val json = bundle.get("ciclo").toString() // Key
            val gson = Gson()
            ciclo = gson.fromJson(json, Ciclo::class.java)
            rellenar()
        }else{
            setToolbarTitle("Ciclos")
            changeFragment(CiclosFragment())
        }
        return view
    }
    private fun GestionarEstado() {

            CoroutineScope(Dispatchers.IO).launch {
                try {
                if(ciclo.estado==1) {
                    ciclo.estado=2
                    var call = CicloService.getInstance().desactivarCiclo(ciclo)
                    if (call.isSuccessful) {
                        withContext(Dispatchers.Main) {
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                        }
                    }
                }else{
                    ciclo.estado=1
                    var call = CicloService.getInstance().activar(ciclo)
                        if (call.isSuccessful) {
                            withContext(Dispatchers.Main) {
                            }
                        } else {
                            withContext(Dispatchers.Main) {}
                }
                }
                } catch (e: Exception) {

                }
            }


    }
    private fun rellenar(){
        editTextFechaFinal.setText(ciclo.fec_final)
        editTextAnnio.setText(ciclo.annio.toString())
        editTextFechaInicio.setText(ciclo.fec_inicio)
        editTextnum.setText(toRoman(ciclo.numero))
        if(ciclo!!.estado==1)
            estado.setText("Activo")
        else
            estado.setText("Inactivo")
    }

    private fun volver(){
        setToolbarTitle("Ciclos")
        changeFragment(CiclosFragment())
    }
    private fun toRoman(valor:Int): String {
        return when(valor){
            1-> "I"
            2->"II"
            3->"III"
            else-> ""
        }
    }
}
