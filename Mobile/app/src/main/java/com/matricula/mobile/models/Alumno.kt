package com.sistema.logicaDeNegocio

import com.matricula.mobile.models.Carrera

class Alumno {
    var cedula: String
    var nombre: String
    var teléfono: String
    var email: String
    var fech_nac: String
    private var carrera: Carrera

    constructor() {
        cedula = ""
        nombre = ""
        teléfono = ""
        email = ""
        fech_nac = ""
        carrera = Carrera()
    }

    constructor(cedula: String, nombre: String, teléfono: String, email: String, fech_nac: String, carrera: Carrera) {
        this.cedula = cedula
        this.nombre = nombre
        this.teléfono = teléfono
        this.email = email
        this.fech_nac = fech_nac
        this.carrera = carrera
    }

    fun getCarrera(): Carrera {
        return carrera
    }

    fun setCarrera(carrera: Carrera) {
        this.carrera = carrera
    }
}