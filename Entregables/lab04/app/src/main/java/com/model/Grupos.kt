package com.model

import com.sistema.logicaDeNegocio.*

class Grupos {
    private var grupos:ArrayList<Grupo> =ArrayList<Grupo>()
    init {

        addGrupo(Grupo(30, 30, 1, "L-J 8:00.am-9:40.am", Ciclo(), Profesor(), Curso()))
        addGrupo(Grupo(30, 30, 2, "M-V 8:00.am-9:40.am", Ciclo(), Profesor(), Curso()))
        addGrupo(Grupo(30, 30, 3, "L-J 10:00.am-11:40.am", Ciclo(), Profesor(), Curso()))
        addGrupo(Grupo(30, 30, 4, "M-V 10:00.am-11:40.am", Ciclo(), Profesor(), Curso()))
    }
    private object HOLDER {
        val INSTANCE = Grupos()
    }
    companion object {
        val instance: Grupos by lazy {
            HOLDER.INSTANCE
        }
    }
    fun addGrupo(curso: Grupo){
        grupos?.add(curso)
    }
    fun getGrupo(numero: String): Grupo? {
        for (c: Grupo in grupos!!){
            if(c.numero.equals(numero)){
                return c;
            }
        }
        return null;
    }
    fun getGrupos(): ArrayList<Grupo>{
        return this.grupos!!
    }
    fun deleteGrupo(position: Int){
        grupos!!.removeAt(position)
    }
}