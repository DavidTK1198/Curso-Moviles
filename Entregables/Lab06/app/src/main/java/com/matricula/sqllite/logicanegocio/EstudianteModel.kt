package com.matricula.sqllite.logicanegocio

class EstudianteModel {
    private var uniqueInstance:EstudianteModel=EstudianteModel()
    private var estudiante:Estudiante=Estudiante()
    init {
        setEstudiante(Estudiante())
    }
    private object HOLDER {
        val INSTANCE = EstudianteModel()
    }
    companion object {
        val instance: EstudianteModel by lazy {
            HOLDER.INSTANCE
        }
    }

     fun getModel():EstudianteModel{
        return this.uniqueInstance!!
    }

    fun setEstudiante(estudiante: Estudiante){
        this.estudiante=estudiante
    }

    fun getEstudiante():Estudiante{
        return  this.estudiante
    }
}