package com.model

import com.sistema.logicaDeNegocio.Carrera

class Carreras {
    private var carreras:ArrayList<Carrera> =ArrayList<Carrera>()
    init {

        addCarrera(Carrera("01", "Ingenieria en Sistemas", "bachillerato"))
        addCarrera(Carrera("02", "Matematicas", "bachillerato"))
        addCarrera(Carrera("03", "Artes Visuales", "bachillerato"))
        addCarrera(Carrera("04", "Economia", "bachillerato"))
    }
    private object HOLDER {
        val INSTANCE = Carreras()
    }
    companion object {
        val instance: Carreras by lazy {
            HOLDER.INSTANCE
        }
    }
    fun addCarrera(carrera: Carrera){
        carreras?.add(carrera)
    }
    fun getCarrera(codigo: String): Carrera? {
        for (c: Carrera in carreras!!){
            if(c.codigo.equals(codigo)){
                return c;
            }
        }
        return null;
    }
    fun getUsuarios(): ArrayList<Carrera>{
        return this.carreras!!
    }
    fun deleteUsuario(position: Int){
        carreras!!.removeAt(position)
    }
}