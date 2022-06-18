package com.sistema.logicaDeNegocio

import com.matricula.mobile.models.Alumno

class Inscripcion {
    var estudiante: Alumno
    var nota: Int
    var grupo: Grupo
    var idEntidad = 0

    constructor(estudiante: Alumno, nota: Int, grupo: Grupo) {
        this.estudiante = estudiante
        this.nota = nota
        this.grupo = grupo
    }

    constructor() {
        estudiante = Alumno()
        nota = 0
        grupo = Grupo()
        idEntidad = 0
    }
}