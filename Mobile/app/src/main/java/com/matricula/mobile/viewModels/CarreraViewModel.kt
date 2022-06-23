package com.matricula.mobile.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.matricula.mobile.models.Carrera


class CarreraViewModel: ViewModel() {

    private var carreraListLiveData: MutableLiveData<List<Carrera>>? = null
    private var state:MutableLiveData<Boolean>?=null
    private var carrera:MutableLiveData<Carrera>?=MutableLiveData<Carrera>()
    private var message:MutableLiveData<String>?=null

    fun getCarrerasList(): LiveData<List<Carrera>>? {
        if (carreraListLiveData == null) {
            carreraListLiveData = MutableLiveData<List<Carrera>>()
            state=MutableLiveData<Boolean>()
            message=MutableLiveData<String>()
        }
        return carreraListLiveData
    }

    fun check_state(): Boolean? {
        return state!!.value
    }
    fun setState(estado:Boolean){
        state!!.value=estado
    }
  fun updateModel(NCarreras:List<Carrera>) {
        carreraListLiveData!!.value = NCarreras
    }

    fun setMensaje(mensaje:String){
        message!!.value=mensaje
    }

    fun setCarrera(carrera: Carrera){
        this.carrera!!.value=carrera
    }

    fun  getCarrera(): Carrera? {
        return this.carrera!!.value
    }

}