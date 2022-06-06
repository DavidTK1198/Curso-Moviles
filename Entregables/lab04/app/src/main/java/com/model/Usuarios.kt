package com.model
import com.sistema.logicaDeNegocio.Usuario

class Usuarios private constructor() {
    private var usuarios:ArrayList<Usuario> =ArrayList<Usuario>()
    init {
        addUsuario(Usuario("01", "01", "Richard Millie", "administrador"))
        addUsuario(Usuario("02", "02", "Dolce Gabanna", "matriculador"))
        addUsuario(Usuario("03", "03", "Enrique Lopez", "profesor"))
        addUsuario(Usuario("04", "04", "Sofia Guevara", "profesor"))
        addUsuario(Usuario("05", "05", "Ricardo Zamora", "profesor"))
        addUsuario(Usuario("06", "06", "Fernando Perez", "profesor"))
        addUsuario(Usuario("07", "07", "Luis Zaragoza", "alumno"))
        addUsuario(Usuario("08", "08", "Elena Miranda", "alumno"))
        addUsuario(Usuario("09", "09", "Rocio Flores", "alumno"))
        addUsuario(Usuario("10", "10", "Margarita Lettucci", "alumno"))
    }
    private object HOLDER {
        val INSTANCE = Usuarios()
    }
    companion object {
        val instance: Usuarios by lazy {
            HOLDER.INSTANCE
        }
    }
    fun addUsuario(usuario: Usuario){
        usuarios?.add(usuario)
    }
    fun getUsuario(id: String): Usuario? {
        for (u: Usuario in usuarios!!){
            if(u.id.equals(id)){
                return u;
            }
        }
        return null;
    }
    fun getUsuarios(): ArrayList<Usuario>{
        return this.usuarios!!
    }
    fun deleteUsuario(position: Int){
        usuarios!!.removeAt(position)
    }
}