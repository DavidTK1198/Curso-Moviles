package com.matricula.sqllite.logicanegocio
/**
 *
 * @author DavidTK1198
 */
class Curso{
    var codigo: String
    var nombre: String
    var creditos: Int

    constructor(codigo: String, nombre: String, creditos: Int) {
        this.codigo = codigo
        this.nombre=nombre
        this.creditos=creditos
    }

}