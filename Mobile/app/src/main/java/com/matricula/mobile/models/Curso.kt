package com.sistema.logicaDeNegocio

import com.matricula.mobile.models.Carrera

/**
 *
 * @author DavidTK1198
 */
class Curso(var codigo: String, var nombre: String, var creditos: Int, var hsemanales: Int, var carrera: Carrera) {
    private var grupos: ArrayList<Grupo>

    constructor() : this("", "", 0, 0, Carrera()) {
        grupos = ArrayList<Grupo>()
    }

    init {
        grupos = ArrayList<Grupo>()
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