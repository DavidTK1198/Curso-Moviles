package com.sistema.logicaDeNegocio


/**
 *
 * @author DavidTK1198
 */
class Carrera {
    var codigo: String? = null
    var nombre: String? = null
    var titulo: String? = null
    private var cursos: List<Curso>? = null

    constructor() {}
    constructor(codigo: String?, nombre: String?, titulo: String?) {
        this.codigo = codigo
        this.nombre = nombre
        this.titulo = titulo
        cursos = ArrayList<Curso>()
    }

    fun getCursos(): List<Curso>? {
        return cursos
    }

    fun setCursos(cursos: List<Curso>?) {
        this.cursos = cursos
    }
}