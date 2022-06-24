package com.matricula.mobile.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.matricula.mobile.models.Inscripcion

class InscripcionViewModel: ViewModel()  {
    private var InscripcionListLiveData: MutableLiveData<List<Inscripcion>>? = null
    private var state: MutableLiveData<Boolean>?=null
    private var inscripcion: MutableLiveData<Inscripcion>?=MutableLiveData<Inscripcion>()
    private var message: MutableLiveData<String>?=null

    fun getInscripcionesList(): LiveData<List<Inscripcion>>? {
        if (InscripcionListLiveData == null) {
            InscripcionListLiveData = MutableLiveData<List<Inscripcion>>()
            state= MutableLiveData<Boolean>()
            message= MutableLiveData<String>()
        }
        return InscripcionListLiveData
    }

    fun check_state(): Boolean? {
        return state!!.value
    }
    fun setState(estado:Boolean){
        state!!.value=estado
    }
    fun updateModel(NInscripcions:List<Inscripcion>) {
        InscripcionListLiveData!!.value = NInscripcions
    }

    fun setInscripcion(inscripcion: Inscripcion){
        this.inscripcion!!.value=inscripcion
    }
    fun getInscripcion(): Inscripcion? {
        return this.inscripcion!!.value
    }
    fun setMensaje(mensaje:String){
        message!!.value=mensaje
    }
}