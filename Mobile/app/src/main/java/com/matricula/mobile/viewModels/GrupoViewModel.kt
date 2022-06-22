package com.matricula.mobile.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.matricula.mobile.models.Carrera
import com.matricula.mobile.models.Ciclo
import com.matricula.mobile.models.Curso
import com.matricula.mobile.models.Grupo

class GrupoViewModel {

    private var GrupoListLiveData: MutableLiveData<List<Grupo>>? = null
    private var state: MutableLiveData<Boolean>?=null
    private var message: MutableLiveData<String>?=null
    private var ciclo:MutableLiveData<Ciclo>?= MutableLiveData<Ciclo>()
    private var Grupo:MutableLiveData<Grupo>?= MutableLiveData<Grupo>()
    private var curso:MutableLiveData<Curso>?= MutableLiveData<Curso>()


    fun getCarrerasList(): MutableLiveData<List<Grupo>>? {
        if (GrupoListLiveData== null) {
            GrupoListLiveData= MutableLiveData<List<Grupo>>()
            state= MutableLiveData<Boolean>()
            message= MutableLiveData<String>()
        }
        return GrupoListLiveData
    }

    fun check_state(): Boolean? {
        return state!!.value
    }
    fun setState(estado:Boolean){
        state!!.value=estado
    }
    fun updateModel(Ngrupos:List<Grupo>) {
        GrupoListLiveData!!.value = Ngrupos
    }

    fun setMensaje(mensaje:String){
        message!!.value=mensaje
    }
}