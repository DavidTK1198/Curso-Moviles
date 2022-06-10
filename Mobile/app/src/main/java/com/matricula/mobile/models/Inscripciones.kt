package com.model

import com.sistema.logicaDeNegocio.*

class Inscripciones {
    private var inscripciones:ArrayList<Inscripcion> =ArrayList<Inscripcion>()
    init {

        addInscripcion(Inscripcion(Alumno(), 100, Grupo()))
        addInscripcion(Inscripcion(Alumno(), 100, Grupo()))
        addInscripcion(Inscripcion(Alumno(), 100, Grupo()))
        addInscripcion(Inscripcion(Alumno(), 100, Grupo()))
    }
    private object HOLDER {
        val INSTANCE = Inscripciones()
    }
    companion object {
        val instance: Inscripciones by lazy {
            HOLDER.INSTANCE
        }
    }
    fun addInscripcion(curso: Inscripcion){
        inscripciones?.add(curso)
    }
    fun getInscripcion(idEntidad: String): Inscripcion? {
        for (i: Inscripcion in inscripciones!!){
            if(i.idEntidad.equals(idEntidad)){
                return i;
            }
        }
        return null;
    }
    fun getInscripciones(): ArrayList<Inscripcion>{
        return this.inscripciones!!
    }
    fun deleteGrupo(position: Int){
        inscripciones!!.removeAt(position)
    }
}