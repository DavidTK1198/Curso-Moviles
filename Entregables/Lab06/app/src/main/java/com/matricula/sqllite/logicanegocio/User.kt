package com.matricula.sqllite.logicanegocio
import java.io.Serializable
data class User (

    var username:String = "MasterKotlin",
    var password:String="123",
): Serializable {

}

val user=User()

fun login(Username:String,Password:String):Boolean{
    if (user.username==Username && user.password==Password){
        return true
    }
    return false
}
