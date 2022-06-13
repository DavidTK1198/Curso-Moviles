package com.matricula.mobile.models
import java.io.Serializable
class Usuario @JvmOverloads constructor(
    var id: String = "",
    var clave: String = "",
    var nombre: String = "",
    var rol: String = ""
): Serializable {
}