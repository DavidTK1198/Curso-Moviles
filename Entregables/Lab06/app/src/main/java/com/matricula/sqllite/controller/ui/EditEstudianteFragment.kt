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
import com.matricula.sqllite.logicanegocio.EstudianteModel
import com.matricula.sqllite.util.FragmentUtils
class EditEstudianteFragment : FragmentUtils() {
    private var model = EstudianteModel.instance

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

        var editTextName = view?.findViewById<EditText>(R.id.Nombre)
        var editTextUser = view?.findViewById<EditText>(R.id.apellido)
        var editTextedad = view?.findViewById<EditText>(R.id.edad)
        var editTextID = view?.findViewById<EditText>(R.id.ID)

        editTextID!!.setText(model.estudiante.cedula.toString())
        editTextName!!.setText(model.estudiante.nombre)
        editTextUser!!.setText(model.estudiante.apellido)
        editTextedad!!.setText(model.estudiante.edad.toString())
        editTextID.isEnabled=false

        return view
    }

    private fun EditEstudiante(){
        var message:String? = null
        var editTextName = view?.findViewById<EditText>(R.id.Nombre)
        var editTextUser = view?.findViewById<EditText>(R.id.apellido)
        var editTextPassword = view?.findViewById<EditText>(R.id.edad)

        var name = editTextName?.text.toString()
        var appellido = editTextUser?.text.toString()
        var edad = editTextPassword?.text.toString()
        model.estudiante.nombre=name
        model.estudiante.apellido=appellido
        model.estudiante.edad=edad.toInt()
        model.update()
        message = "Estudiante Editado"
        Snackbar
            .make(view!!, message!!, Snackbar.LENGTH_LONG)
            .setAction("Action", null)
            .show()
        editTextName?.setText(model.estudiante.nombre)
        editTextUser?.setText(model.estudiante.apellido)
        editTextPassword?.setText(model.estudiante.edad.toString())
    }
    private fun volver(){
        setToolbarTitle("Personas")
        changeFragment(EstudiantesFragment(model.getHelper()))
    }
}