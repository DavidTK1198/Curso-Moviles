package com.matricula.sqllite.logicanegocio
import com.matricula.sqllite.accesodatos.DatabaseHelper
class CursoModel {
    var curso:Curso=Curso()
    var cursos:ArrayList<Curso> =ArrayList()
    private  lateinit  var dbHelper : DatabaseHelper

    private object HOLDER {
        val INSTANCE = CursoModel()
    }
    companion object {
        val instance: CursoModel by lazy {
            HOLDER.INSTANCE
        }
    }

    fun sethelper(helper: DatabaseHelper){
        this.dbHelper=helper
    }

    fun getHelper():DatabaseHelper{
        return this.dbHelper
    }

    fun getAllCursos():ArrayList<Curso>? {
        val res = dbHelper.allCurso
        this.cursos=ArrayList()
        if (res.count == 0) {
            return cursos
        } else {
            val buffer = StringBuffer()
            while (res.moveToNext()) {
                curso=Curso(res.getInt(0),res.getString(1),res.getInt(3))
                cursos.add(curso)
            }


        }
        return cursos
    }

    fun agregar(){
        this.dbHelper.insertDataCurso(curso.codigo, curso.nombre, curso.creditos)
    }
    fun delete(){
        this.dbHelper.deleteDataCurso(curso.codigo.toString())
    }

    fun update(){
        this.dbHelper.updateDataCurso(curso.codigo, curso.nombre, curso.creditos)
    }
}