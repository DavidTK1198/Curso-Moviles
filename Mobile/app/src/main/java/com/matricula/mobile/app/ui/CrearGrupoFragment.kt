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
import com.matricula.mobile.R
import com.matricula.mobile.apiService.GrupoService
import com.matricula.mobile.models.Grupo
import com.matricula.mobile.viewModels.GrupoViewModel
import com.matricula.mobile.viewModels.ProfesorViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CrearGrupoFragment: FragmentUtils() {
    private lateinit  var editnumero:EditText
    private lateinit var editCupo :EditText
    private lateinit var editHorario :EditText
    private lateinit var  profesorbtn:Button
    private val GrupoViewModel: GrupoViewModel by activityViewModels()
    private val ProfesorViewModel: ProfesorViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_create_groups, container, false)

       view.findViewById<Button>(R.id.btn_guardarGroup).setOnClickListener{
            this.crearGrupo()
        }

        view.findViewById<Button>(R.id.btn_volver_group).setOnClickListener {
            volver()
        }
        editnumero=view.findViewById(R.id.editText_Number)
        editCupo=view.findViewById(R.id.editText_Cupo)
        editHorario = view.findViewById(R.id.editTextHorario)

       profesorbtn= view.findViewById<Button>(R.id.profesor_grupo)
        profesorbtn.setOnClickListener{
            setToolbarTitle("Profesores")
            changeFragment(Grupo_ProfesorFragment())
        }

        if(ProfesorViewModel.getProfesor()!=null){
            profesorbtn.setText(ProfesorViewModel!!.getProfesor()!!.nombre)
        }
        return view
    }
    private fun crearGrupo() {
        var name = editnumero?.text.toString()
        var codigo = editCupo.text.toString()
        var titulo = editHorario ?.text.toString()
        val profesor= ProfesorViewModel.getProfesor()
        val curso=GrupoViewModel.getCurso()
        val ciclo=GrupoViewModel.getCiclo()
        try {
            var c = Grupo(codigo.toInt(),codigo.toInt(), name.toInt(), titulo,ciclo!!,profesor!!,curso!!)
            if (validarDatos()) {
                insertarGrupo(c)
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
        editnumero?.setText("")
        editCupo?.setText("")
        editHorario?.setText("")
        val loader=view?.findViewById<ProgressBar>(R.id.loading)
        loader?.visibility=View.GONE
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
    private fun volver(){
        setToolbarTitle("Grupos")
        changeFragment(GruposFragment())
    }

    private fun initLoading(){
        val loader = view?.findViewById<ProgressBar>(R.id.loading)
        loader?.visibility=View.VISIBLE
    }

    private fun stopLoadingError(){
        val loader=view?.findViewById<ProgressBar>(R.id.loading)
        loader?.visibility=View.GONE
    }

    fun validarAgregar(): Boolean {
        if (profesorbtn.text.toString() == "Debe Seleccionar un Profesor") {
            AlertDialog.Builder(this.activity!!)
                .setTitle("Alerta")
                .setIcon(R.drawable.ic_warning)
                .setMessage("Debe Seleccionar un Profesor")
                .setPositiveButton("Ok") { dialog, _ ->
                    dialog.dismiss()
                }
                .create()
                .show()
            return false
        }
        return true
    }


    private fun validarDatos():Boolean{//Manejo de errores
        return(editnumero?.text.toString()!="" &&
                editHorario?.text.toString()!=""&& editCupo?.text.toString()!="")
    }

    private fun insertarGrupo(Grupo: Grupo){
        initLoading()
        CoroutineScope(Dispatchers.IO).launch {
            val call = GrupoService.getInstance().ingresarGrupo(Grupo)
            if (call.isSuccessful) {
                withContext(Dispatchers.Main) {
                    limpiar()
                }
            } else {
                withContext(Dispatchers.Main) {
                    stopLoadingError()
                }
            }
        }
    }
}
