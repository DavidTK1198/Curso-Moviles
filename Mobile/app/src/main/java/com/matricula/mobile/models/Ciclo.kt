package com.matricula.mobile.models

class Ciclo {
    var annio: Int
    var numero: Int
    var id: Int
    var fec_inicio: String
    var fec_final: String
    var estado: Int = 0

    constructor(id: Int, annio: Int, numero: Int, est: Int, fec_inicio: String, fec_final: String) {
        this.annio = annio
        this.numero = numero
        this.id = id
        this.fec_inicio = fec_inicio
        this.fec_final = fec_final
        this.estado = est
    }

    constructor() {
        annio = 0
        numero = 0
        id = 0
        fec_inicio = ""
        fec_final = ""
        estado = 0
    }
}