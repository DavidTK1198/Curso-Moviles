package com.model

import com.sistema.logicaDeNegocio.Alumno
import com.sistema.logicaDeNegocio.Carrera

class Alumnos {
    private var alumnos:ArrayList<Alumno> =ArrayList<Alumno>()
    init {

        addAlumno(Alumno("07", "Luis Zaragoza", "84840734", "luis@gmail.com", "07/02/1998", Carrera()))
        addAlumno(Alumno("08", "Elena Miranda", "84840735", "elena@gmail.com", "07/03/1998", Carrera()))
        addAlumno(Alumno("09", "Rocio Flores", "84840736", "rocio@gmail.com", "07/04/1998", Carrera()))
        addAlumno(Alumno("10", "Margarita Lettucci", "84840737", "margarita@gmail.com", "07/05/1998", Carrera()))
    }
    private object HOLDER {
        val INSTANCE = Alumnos()
    }
    companion object {
        val instance: Alumnos by lazy {
            HOLDER.INSTANCE
        }
    }
    fun addAlumno(alumno: Alumno){
        alumnos?.add(alumno)
    }
    fun getAlumno(cedula: String): Alumno? {
        for (a: Alumno in alumnos!!){
            if(a.cedula.equals(cedula)){
                return a;
            }
        }
        return null;
    }
    fun getAlumnos(): ArrayList<Alumno>{
        return this.alumnos!!
    }
    fun deleteAlumno(position: Int){
        alumnos!!.removeAt(position)
    }
}