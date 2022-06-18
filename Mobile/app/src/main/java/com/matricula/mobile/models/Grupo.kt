package com.sistema.logicaDeNegocio

import com.matricula.mobile.models.Alumno


/**
 *
 * @author DavidTK1198
 */
class Grupo {
    var numero: Int
    private var ciclo: Ciclo
    var horario: String
    private var estudiantes: List<Alumno?>
    private var profesor: Profesor
    var curso: Curso
    var idEntidad = 0
    var cupo: Int
    var disponible: Int

    constructor(
        cupo: Int,
        disponible: Int,
        numero: Int,
        horario: String,
        ciclo: Ciclo,
        profesor: Profesor,
        curso: Curso
    ) {
        this.numero = numero ////
        this.horario = horario
        this.profesor = profesor
        this.curso = curso
        this.estudiantes = ArrayList<Alumno>()
        this.ciclo = ciclo
        this.cupo = cupo
        this.disponible = cupo
    }

    fun getCiclo(): Ciclo {
        return ciclo
    }

    fun setCiclo(ciclo: Ciclo) {
        this.ciclo = ciclo
    }

    constructor() {
        numero = 0
        horario = ""
        estudiantes = ArrayList<Alumno>()
        profesor = Profesor()
        curso = Curso()
        ciclo = Ciclo()
        cupo = 0
        disponible = 0
        idEntidad = 0
    }

    fun getEstudiantes(): List<*> {
        return estudiantes
    }

    fun setEstudiantes(estudiantes: List<Alumno?>) {
        this.estudiantes = estudiantes
    }

    fun getProfesor(): Profesor {
        return profesor
    }

    fun setProfesor(profesor: Profesor) {
        this.profesor = profesor
    }
}