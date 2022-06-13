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
        var view = inflater.inflate(R.layout.fragment_create_curso, container, false)

        view.findViewById<Button>(R.id.btn_guardarCurso).setOnClickListener{
            createCurso()
        }

        view.findViewById<Button>(R.id.btn_volver_curso).setOnClickListener {
            volver()
        }

        return view
    }

    private fun createCurso(){
        var message:String? = null
        var editText_Name_Curso = view?.findViewById<EditText>(R.id.editText_Name_Curso)
        var editText_Codigo = view?.findViewById<EditText>(R.id.editText_Codigo)
        var editText_Creditos = view?.findViewById<EditText>(R.id.editText_Creditos)

        var nombre = editText_Name_Curso?.text.toString()
        var codigo = editText_Codigo?.text.toString()
        var creditos = editText_Creditos?.text.toString()
        model.curso.codigo=codigo.toInt()
        model.curso.nombre=nombre
        model.curso.creditos=creditos.toInt()
        model.agregar()
        message = "Curso Agregado"
        Snackbar
            .make(view!!, message!!, Snackbar.LENGTH_LONG)
            .setAction("Action", null)
            .show()
        editText_Name_Curso?.setText("")
        editText_Codigo?.setText("")
        editText_Creditos?.setText("")
    }
    private fun volver(){
        setToolbarTitle("Cursos")
        changeFragment(CoursesFragment(model.getHelper()))
    }
}