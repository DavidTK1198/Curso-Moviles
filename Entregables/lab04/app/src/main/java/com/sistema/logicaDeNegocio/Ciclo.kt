package com.sistema.logicaDeNegocio

class Ciclo {
    var annio: Int
    var numero: Int
    var id: Int
    var fec_inicio: String
    var fec_final: String
    var isEstado: Int

    constructor(id: Int, annio: Int, numero: Int, estado: Int, fec_inicio: String, fec_final: String) {
        this.annio = annio
        this.numero = numero
        this.id = id
        this.fec_inicio = fec_inicio
        this.fec_final = fec_final
        isEstado = estado
    }

    constructor() {
        annio = 0
        numero = 0
        id = 0
        fec_inicio = ""
        fec_final = ""
        isEstado = 0
    }
}