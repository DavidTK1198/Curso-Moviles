package com.matricula.sqllite.controller.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.example.peopleapp.EstudiantesFragment
import com.google.android.material.snackbar.Snackbar
import com.matricula.sqllite.R
import com.matricula.sqllite.logicanegocio.CursoModel
import com.matricula.sqllite.logicanegocio.EstudianteModel
import com.matricula.sqllite.util.FragmentUtils
class EditCursoFragment : FragmentUtils() {
    private var model = CursoModel.instance

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_edit_estudiante, container, false)

        view.findViewById<Button>(R.id.btn_guardarPersona).setOnClickListener{
            EditEstudiante()
        }

        view.findViewById<Button>(R.id.btn_volver).setOnClickListener {
            volver()
        }
//
//        var editTextName = view?.findViewById<EditText>(R.id.Nombre)
//        var editTextCredits = view?.findViewById<EditText>(R.id.Creditos)
//        var editTextID = view?.findViewById<EditText>(R.id.ID)
//
//        editTextID!!.setText(model.curso.codigo.toString())
//        editTextName!!.setText(model.curso.nombre)
//        editTextCredits!!.setText(model.curso.creditos)
//        editTextID.isEnabled=false

        return view
    }

    private fun EditEstudiante(){
        var message:String? = null
        var editTextName = view?.findViewById<EditText>(R.id.Nombre)
        var editTextUser = view?.findViewById<EditText>(R.id.apellido)
        var editTextPassword = view?.findViewById<EditText>(R.id.edad)

        var codigo = editTextName?.text.toString()
        var nombre = editTextUser?.text.toString()
        var creditos = editTextPassword?.text.toString()
        model.curso.codigo=codigo.toInt()
        model.curso.nombre=nombre
        model.curso.creditos=creditos.toInt()
        model.update()
        message = "Estudiante Editado"
        Snackbar
            .make(view!!, message!!, Snackbar.LENGTH_LONG)
            .setAction("Action", null)
            .show()
        editTextName?.setText(model.curso.codigo)
        editTextUser?.setText(model.curso.nombre)
        editTextPassword?.setText(model.curso.creditos.toString())
    }
    private fun volver(){
        setToolbarTitle("Personas")
        changeFragment(EstudiantesFragment(model.getHelper()))
    }
}