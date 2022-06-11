package com.matricula.sqllite.logicanegocio

class Estudiante {
    var cedula: String
    var apellido: String
    var nombre:String
    var edad: Int

    constructor() {
        cedula = ""
        apellido = ""
        nombre = ""
        edad= 0
    }

    constructor(cedula: String, nombre: String, apellido: String, edad:Int) {
        this.cedula = cedula
        this.nombre=nombre
       this.apellido=apellido
        this.edad = edad
    }


}