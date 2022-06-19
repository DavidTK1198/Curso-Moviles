package com.matricula.mobile.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.matricula.mobile.models.Carrera
import com.matricula.mobile.models.Curso


class CursoViewModel: ViewModel() {

    private var cursoListLiveData: MutableLiveData<List<Curso>>? = null
    private var carrera:MutableLiveData<Carrera>?= MutableLiveData<Carrera>()
    private var state:MutableLiveData<Boolean>?=null
    private var message:MutableLiveData<String>?=null

    fun getCursosList(): LiveData<List<Curso>>? {
        if (cursoListLiveData == null) {
            cursoListLiveData = MutableLiveData<List<Curso>>()
            state=MutableLiveData<Boolean>()
            message=MutableLiveData<String>()
        }
        return cursoListLiveData
    }

    fun check_state(): Boolean? {
        return state!!.value
    }
    fun setState(estado:Boolean){
        state!!.value=estado
    }
    fun updateModel(NCursos:List<Curso>) {
        cursoListLiveData!!.value = NCursos
    }

    fun setCarrera(c: Carrera){
       carrera!!.value=c
    }
    fun setMensaje(mensaje:String){
        message!!.value=mensaje
    }

    fun getCarrera(): Carrera {
        return this.carrera!!.value!!
    }
}