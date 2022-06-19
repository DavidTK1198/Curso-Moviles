package com.matricula.mobile.models

/**
 *
 * @author DavidTK1198
 */
class Curso(var codigo: String, var nombre: String, var creditos: Int, var hsemanales: Int, var carrera: Carrera) {
    private var grupos: ArrayList<Grupo>

    constructor() : this("", "", 0, 0, Carrera()) {
        grupos = ArrayList()
    }

    init {
        grupos = ArrayList()
    }

    fun agregarGrupo(grupo: Grupo) {
        grupos.add(grupo)
    }

    fun getGrupos(): ArrayList<*> {
        return grupos
    }

    fun setGrupos(grupos: ArrayList<Grupo>) {
        this.grupos = grupos
    }
}