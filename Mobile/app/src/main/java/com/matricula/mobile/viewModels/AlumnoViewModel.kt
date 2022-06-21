package com.matricula.mobile.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.matricula.mobile.models.Alumno
import com.matricula.mobile.models.Carrera

class AlumnoViewModel: ViewModel() {

    private var AlumnoListLiveData: MutableLiveData<List<Alumno>>? = null
    private var state:MutableLiveData<Boolean>?=null
    private var message:MutableLiveData<String>?=null
    private var carrera:MutableLiveData<Carrera>?=MutableLiveData<Carrera>()
    private var alumno:MutableLiveData<Alumno>?=MutableLiveData<Alumno>()

    fun getAlumnosList(): LiveData<List<Alumno>>? {
        if (AlumnoListLiveData == null) {
            AlumnoListLiveData = MutableLiveData<List<Alumno>>()
            state=MutableLiveData<Boolean>()
            message=MutableLiveData<String>()
        }
        return AlumnoListLiveData
    }

    fun check_state(): Boolean? {
        return state!!.value
    }
    fun setState(estado:Boolean){
        state!!.value=estado
    }
    fun updateModel(NAlumnos:List<Alumno>) {
        AlumnoListLiveData!!.value = NAlumnos
    }
    fun getCarrera(): Carrera? {
        return this.carrera!!.value
    }
    fun updateCarrera(carrera: Carrera){
        this.carrera!!.value=carrera
    }
    fun setMensaje(mensaje:String){
        message!!.value=mensaje
    }

    fun getAlumno(): MutableLiveData<Alumno>? {
        return this.alumno
    }

    fun updateAlumno(al: Alumno) {
        this.alumno!!.value=al
    }
}