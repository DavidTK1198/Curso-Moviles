package com.matricula.sqllite.logicanegocio

class Estudiante {
    var cedula: Int
    var apellido: String
    var nombre:String
    var edad: Int

    constructor() {
        cedula = 0
        apellido = ""
        nombre = ""
        edad= 0
    }

    constructor(cedula: Int, nombre: String, apellido: String, edad:Int) {
        this.cedula = cedula
        this.nombre=nombre
       this.apellido=apellido
        this.edad = edad
    }


}