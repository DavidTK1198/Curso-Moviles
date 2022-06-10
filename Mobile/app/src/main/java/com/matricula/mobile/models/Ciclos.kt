package com.model

import com.sistema.logicaDeNegocio.Ciclo

class Ciclos {
    private var ciclos:ArrayList<Ciclo> =ArrayList<Ciclo>()
    init {

        addCiclo(Ciclo(1, 2020, 1, 0, "10/02/2020", "10/06/2020"))
        addCiclo(Ciclo(2, 2020, 2, 0, "10/07/2020", "10/11/2020"))
        addCiclo(Ciclo(3, 2021, 3, 1, "10/02/2021", "10/06/2021"))
    }
    private object HOLDER {
        val INSTANCE = Ciclos()
    }
    companion object {
        val instance: Ciclos by lazy {
            HOLDER.INSTANCE
        }
    }
    fun addCiclo(persona: Ciclo){
        ciclos?.add(persona)
    }
    fun getUsuario(id: String): Ciclo? {
        for (c: Ciclo in ciclos!!){
            if(c.id.equals(id)){
                return c;
            }
        }
        return null;
    }
    fun getUsuarios(): ArrayList<Ciclo>{
        return this.ciclos!!
    }
    fun deleteUsuario(position: Int){
        ciclos!!.removeAt(position)
    }
}