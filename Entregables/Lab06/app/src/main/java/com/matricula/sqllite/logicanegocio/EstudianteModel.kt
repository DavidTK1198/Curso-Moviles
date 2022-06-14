package com.matricula.sqllite.logicanegocio

import android.util.Log
import com.matricula.sqllite.accesodatos.DatabaseHelper

class EstudianteModel private constructor() {
    var estudiante:Estudiante=Estudiante()
    var estudiantes:ArrayList<Estudiante> =ArrayList()
    private  lateinit  var dbHelper : DatabaseHelper

    private object HOLDER {
        val INSTANCE = EstudianteModel()
    }
    companion object {
        val instance: EstudianteModel by lazy {
            HOLDER.INSTANCE
        }
    }

    fun sethelper(helper: DatabaseHelper){
        this.dbHelper=helper
    }

    fun getHelper():DatabaseHelper{
        return this.dbHelper
    }

    fun getAllEstudiantes():ArrayList<Estudiante>? {
        val res = dbHelper.allEstudiante
        this.estudiantes=ArrayList()
        if (res.count == 0) {
            return estudiantes
        } else {
            val buffer = StringBuffer()
            while (res.moveToNext()) {
                estudiante=Estudiante(res.getInt(0),res.getString(1),res.getString(2),res.getInt(3))
                estudiantes.add(estudiante)
            }


        }
        return estudiantes
    }

    fun agregar(){
        this.dbHelper.insertDataEstudiante(estudiante.nombre,estudiante.apellido,estudiante.edad)
    }
    fun delete(){
        this.dbHelper.deleteDataEstudiante(estudiante.cedula.toString())
    }

    fun update(){
        this.dbHelper.updateDataEstudiante(estudiante.cedula.toString(),estudiante.nombre,estudiante.apellido,estudiante.edad.toString())
    }
    fun cursos(){
        val res=  this.dbHelper.allCurso_Estudiante(estudiante.cedula.toString())
        if (res.count == 0) {

        } else {
            val buffer = StringBuffer()
            while (res.moveToNext()) {
                var id=res.getColumnIndex("ID")
                var des=res.getColumnIndex("DESCRIPCION")
                var cred=res.getColumnIndex("CREDITOS")
               var curso=Curso(res.getInt(id),res.getString(des),res.getInt(cred))
                Log.d("curso",curso.nombre)

            }


        }
    }

}