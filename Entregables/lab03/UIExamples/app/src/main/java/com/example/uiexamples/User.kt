package com.example.uiexamples

import java.io.Serializable


class User : Serializable {

    var user:String = ""
    var password:String = ""
    var nombre:String = ""
    var foto:Int = 0
    var rol:String=""
    var address: String = ""
    var city: String = ""
    var code: String = ""
    var country: String = ""
    var email: String = ""
    var phoneNumber: String = ""
    var position: String = ""

    internal constructor(user:String, password:String, nombre:String, foto:Int,rol:String,
                         city: String  ,address:String,code: String,country: String,
                         email: String,phoneNumber: String,position: String = ""){
        this.user = user
        this.password = password
        this.nombre = nombre
        this.foto = foto
        this.rol=rol
        this.city=city
        this.address=address
        this.email=email
        this.phoneNumber=phoneNumber
        this.position=position
        this.country=country
        this.code=code
    }

}