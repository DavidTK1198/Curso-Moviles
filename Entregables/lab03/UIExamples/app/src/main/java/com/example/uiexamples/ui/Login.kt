package com.example.uiexamples.ui
import java.io.Serializable
class Login : Serializable  {
    private var user: String
    private var password: String
    private lateinit var nombre: String
    private var foto: Int = 0

    internal constructor() {
        user = ""
        password = ""
        nombre = ""
    }

    internal constructor(user: String, password: String, nombre: String, foto: Int) {
        this.user = user
        this.password = password
    }

    fun getUser(): String? {
        return user
    }

    fun setUser(user: String?) {
        this.user = user!!
    }
    fun getPassword(): String? {
        return password
    }

    fun setPassword(password: String?) {
        this.password = password!!
    }

    fun getNombre(): String?{
        return this.nombre
    }

    fun setNombre(nombre: String?){
        this.nombre = nombre!!
    }

    fun getFoto(): Int?{
        return this.foto
    }

    fun setFoto(foto: Int?){
        this.foto = foto!!
    }
}