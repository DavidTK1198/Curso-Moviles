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
                var id=res.getColumnIndex("ID")
                var des=res.getColumnIndex("DESCRIPCION")
                var cred=res.getColumnIndex("CREDITOS")
                curso=Curso(res.getInt(id),res.getString(des),res.getInt(cred))
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
    fun matricular(ID:Int,pos:Int){
        this.dbHelper.insertDataCurso_Estudiante(cursos[pos].codigo,ID)
    }
    fun update(){
        this.dbHelper.updateDataCurso(curso.codigo, curso.nombre, curso.creditos)
    }
}