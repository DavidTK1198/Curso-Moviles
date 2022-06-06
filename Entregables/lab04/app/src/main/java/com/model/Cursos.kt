package com.model

import com.sistema.logicaDeNegocio.Carrera
import com.sistema.logicaDeNegocio.Curso

class Cursos {
    private var cursos:ArrayList<Curso> =ArrayList<Curso>()
    init {

        addCurso(Curso("EIF2001", "Programacion I", 4, 8, Carrera()))
        addCurso(Curso("ECO2001", "Economia I", 4, 8, Carrera()))
        addCurso(Curso("MAT2001", "Calculo I", 4, 8, Carrera()))
        addCurso(Curso("ART2001", "Arte I", 4, 8, Carrera()))
    }
    private object HOLDER {
        val INSTANCE = Cursos()
    }
    companion object {
        val instance: Cursos by lazy {
            HOLDER.INSTANCE
        }
    }
    fun addCurso(curso: Curso){
        cursos?.add(curso)
    }
    fun getCurso(codigo: String): Curso? {
        for (c: Curso in cursos!!){
            if(c.codigo.equals(codigo)){
                return c;
            }
        }
        return null;
    }
    fun getCursos(): ArrayList<Curso>{
        return this.cursos!!
    }
    fun deleteCurso(position: Int){
        cursos!!.removeAt(position)
    }
}