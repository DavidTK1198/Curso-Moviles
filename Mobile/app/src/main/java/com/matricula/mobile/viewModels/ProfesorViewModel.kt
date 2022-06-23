package com.matricula.mobile.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.matricula.mobile.models.Profesor



class ProfesorViewModel: ViewModel() {

    private var ProfesorListLiveData: MutableLiveData<List<Profesor>>? = null
    private var state:MutableLiveData<Boolean>?=null
    private var profesor:MutableLiveData<Profesor>?=MutableLiveData<Profesor>()
    private var message:MutableLiveData<String>?=null

    fun getProfesorsList(): LiveData<List<Profesor>>? {
        if (ProfesorListLiveData == null) {
            ProfesorListLiveData = MutableLiveData<List<Profesor>>()
            state=MutableLiveData<Boolean>()
            message=MutableLiveData<String>()
        }
        return ProfesorListLiveData
    }

    fun check_state(): Boolean? {
        return state!!.value
    }
    fun setState(estado:Boolean){
        state!!.value=estado
    }
    fun updateModel(NProfesors:List<Profesor>) {
        ProfesorListLiveData!!.value = NProfesors
    }

    fun setprofesor(profesor: Profesor){
        this.profesor!!.value=profesor
    }
    fun setMensaje(mensaje:String){
        message!!.value=mensaje
    }

    fun getProfesor(): Profesor? {
        return this.profesor!!.value
    }
}