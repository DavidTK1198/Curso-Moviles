package com.matricula.mobile.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.matricula.mobile.apiService.CicloService
import com.sistema.logicaDeNegocio.Ciclo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class CicloViewModel: ViewModel() {

    private var cicloListLiveData: MutableLiveData<List<Ciclo>>? = null
    private var state:MutableLiveData<Boolean>?=null
    private var message:MutableLiveData<String>?=null

    fun getCiclosList(): LiveData<List<Ciclo>>? {
        if (cicloListLiveData == null) {
            cicloListLiveData = MutableLiveData<List<Ciclo>>()
            state=MutableLiveData<Boolean>()
            message=MutableLiveData<String>()
        }
        return cicloListLiveData
    }

    fun check_state(): Boolean? {
        return state!!.value
    }
    fun setState(estado:Boolean){
        state!!.value=estado
    }
    fun updateModel(NCiclos:List<Ciclo>) {
        cicloListLiveData!!.value = NCiclos
    }

    fun setMensaje(mensaje:String){
        message!!.value=mensaje
    }
}