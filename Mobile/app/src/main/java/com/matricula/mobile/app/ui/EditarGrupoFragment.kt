package com.matricula.mobile.app.ui

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
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
import com.matricula.mobile.apiService.GrupoService
import com.matricula.mobile.models.Ciclo
import com.matricula.mobile.models.Curso
import com.matricula.mobile.models.Grupo
import com.matricula.mobile.models.Profesor
import com.matricula.mobile.viewModels.GrupoViewModel
import com.matricula.mobile.viewModels.ProfesorViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException

class EditarGrupoFragment: FragmentUtils() {
    private lateinit var editCupo :EditText
    private lateinit var editHorario :EditText
    private lateinit var grupo:Grupo
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_edit_groups, container, false)

        view.findViewById<Button>(R.id.btn_guardarGroup).setOnClickListener{
            this.crearGrupo()
        }

        view.findViewById<Button>(R.id.btn_volver_group).setOnClickListener {
            volver()
        }
        editCupo=view.findViewById(R.id.editText_Cupo)
        editHorario = view.findViewById(R.id.editTextHorario)

        val bundle = this.arguments
        if (bundle != null) {
            val json = bundle.get("Grupo").toString() // Key
            val gson = Gson()
            val grupo = gson.fromJson(json,Grupo::class.java)
            this.grupo=grupo
            rellenar(grupo)
        }else{
            setToolbarTitle("Carreras")
            changeFragment(CarrerasFragment())
        }

        return view
    }

    private fun rellenar(grupo: Grupo?) {
        editCupo?.setText(grupo!!.cupo.toString())
        editHorario?.setText(grupo!!.horario)
    }

    private fun crearGrupo() {
        var codigo = editCupo.text.toString()
        var titulo = editHorario ?.text.toString()
        try {
            var c = Grupo(codigo.toInt(),codigo.toInt(), this.grupo.numero, titulo, Ciclo(), Profesor(), Curso())
            c.idEntidad=grupo.idEntidad
            if (validarDatos()) {
                modificarGrupo(c)
            }else{
                val  message = "Debe Completar todos los datos!!"
                Snackbar
                    .make(view!!, message!!, Snackbar.LENGTH_LONG)
                    .setAction("Action", null)
                    .show()}
        }catch (e:Exception){

        }

    }


    private fun volver(){
        setToolbarTitle("Grupos")
        changeFragment(GruposFragment())
    }

    private fun initLoading(){
        val loader = view?.findViewById<ProgressBar>(R.id.loading)
        loader?.visibility=View.VISIBLE
    }




    private fun validarDatos():Boolean{//Manejo de errores
        return(editHorario?.text.toString()!=""&& editCupo?.text.toString()!="")
    }

    private fun modificarGrupo(Grupo: Grupo) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val call = GrupoService.getInstance().modificarGrupo(Grupo)
                if (call.isSuccessful) {
                    withContext(Dispatchers.Main) {
                    }
                } else {
                    withContext(Dispatchers.Main) {
                    }
                }
            } catch (e: SocketTimeoutException) {
                Log.d("xd","mamado")
            }

        }
    }

}
