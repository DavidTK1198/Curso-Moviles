package com.example.uiexamples

import java.io.Serializable


class User : Serializable {

    var user:String = ""
    var password:String = ""
    var nombre:String = ""
    var foto:Int = 0
    var rol:String=""

    internal constructor(user:String, password:String, nombre:String, foto:Int,rol:String){
        this.user = user
        this.password = password
        this.nombre = nombre
        this.foto = foto
        this.rol=rol
    }

}