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
import com.matricula.sqllite.util.FragmentUtils
class EditCursoFragment : FragmentUtils() {
    private var model = CursoModel.instance

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_edit_cursos, container, false)

        view.findViewById<Button>(R.id.btn_guardarCurso).setOnClickListener{
            EditEstudiante()
        }

        view.findViewById<Button>(R.id.btn_volver_curso).setOnClickListener {
            volver()
        }

       var Nombre = view?.findViewById<EditText>(R.id.nombre_Curso)
        var creditos_Curso = view?.findViewById<EditText>(R.id.creditos_Curso)
       var codigo_Curso = view?.findViewById<EditText>(R.id.codigo_Curso)

        codigo_Curso!!.setText(model.curso.codigo.toString())
        Nombre!!.setText(model.curso.nombre)
        creditos_Curso!!.setText(model.curso.creditos)
        codigo_Curso.isEnabled=false

        return view
    }

    private fun EditEstudiante(){
        var message:String? = null
        var nombre_Curso = view?.findViewById<EditText>(R.id.nombre_Curso)
        var codigo_Curso = view?.findViewById<EditText>(R.id.codigo_Curso)
        var creditos_Curso = view?.findViewById<EditText>(R.id.creditos_Curso)

        var nombre = nombre_Curso?.text.toString()
        var codigo = codigo_Curso?.text.toString()
        var creditos = creditos_Curso?.text.toString()
        model.curso.codigo=codigo.toInt()
        model.curso.nombre=nombre
        model.curso.creditos=creditos.toInt()
        model.update()
        message = "Curso Editado"
        Snackbar
            .make(view!!, message!!, Snackbar.LENGTH_LONG)
            .setAction("Action", null)
            .show()
        codigo_Curso?.setText(model.curso.codigo)
        nombre_Curso?.setText(model.curso.nombre)
        creditos_Curso?.setText(model.curso.creditos.toString())
    }
    private fun volver(){
        setToolbarTitle("Personas")
        changeFragment(CoursesFragment(model.getHelper()))
    }
}