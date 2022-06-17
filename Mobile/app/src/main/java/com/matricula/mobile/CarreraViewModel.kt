package com.matricula.mobile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.matricula.mobile.apiService.CarreraService
import com.matricula.mobile.models.Carrera
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class CarreraViewModel: ViewModel() {

    private var carreraListLiveData: MutableLiveData<List<Carrera>>? = null
    private var state:MutableLiveData<Boolean>?=null
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
}