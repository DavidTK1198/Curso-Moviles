package com.matricula.sqllite.controller.ui

import android.os.Bundle
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


class CreateCourseFragmentFragment : FragmentUtils() {
    private var model = CursoModel.instance

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_create_estudiante, container, false)

        view.findViewById<Button>(R.id.btn_guardarPersona).setOnClickListener{
            createCurso()
        }

        view.findViewById<Button>(R.id.btn_volver).setOnClickListener {
            volver()
        }

        return view
    }

    private fun createCurso(){
        var message:String? = null
        var editTextName = view?.findViewById<EditText>(R.id.editText_Name)
        var editTextUser = view?.findViewById<EditText>(R.id.editText_User)
        var editTextPassword = view?.findViewById<EditText>(R.id.exitText_edad)

        var codigo = editTextName?.text.toString()
        var nombre = editTextUser?.text.toString()
        var creditos = editTextPassword?.text.toString()
        model.curso.codigo=codigo.toInt()
        model.curso.nombre=nombre
        model.curso.creditos=creditos.toInt()
        model.agregar()
        message = "Curso Agregado"
        Snackbar
            .make(view!!, message!!, Snackbar.LENGTH_LONG)
            .setAction("Action", null)
            .show()
        editTextName?.setText("")
        editTextUser?.setText("")
        editTextPassword?.setText("")
    }
    private fun volver(){
        setToolbarTitle("Personas")
        changeFragment(EstudiantesFragment(model.getHelper()))
    }
}