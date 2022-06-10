package com.model

import com.sistema.logicaDeNegocio.Profesor

class Profesores {
    private var profesores:ArrayList<Profesor> =ArrayList<Profesor>()
    init {
        addUsuario(Profesor("03", "Enrique Lopez", "84840724", "enrique@gmail.com"))
        addUsuario(Profesor("04", "Sofia Guevara", "84840725", "sofia@gmail.com"))
        addUsuario(Profesor("05", "Ricardo Zamora", "84840726", "ricardo@gmail.com"))
        addUsuario(Profesor("06", "Fernando Perez", "84840727", "fernando@gmail.com"))
    }
    private object HOLDER {
        val INSTANCE = Profesores()
    }
    companion object {
        val instance: Profesores by lazy {
            HOLDER.INSTANCE
        }
    }
    fun addUsuario(profesor: Profesor){
        profesores?.add(profesor)
    }
    fun getUsuario(cedula: String): Profesor? {
        for (p: Profesor in profesores!!){
            if(p.cedula.equals(cedula)){
                return p;
            }
        }
        return null;
    }
    fun getUsuarios(): ArrayList<Profesor>{
        return this.profesores!!
    }
    fun deleteUsuario(position: Int){
        profesores!!.removeAt(position)
    }
}