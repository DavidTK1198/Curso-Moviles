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
        }
        return carreraListLiveData
    }

     fun  getListOfCarreras() {
        CoroutineScope(Dispatchers.IO).launch {
            val call = CarreraService.getInstance().obtenerCarreras()
            val nCarreras = call.body()
            if (call.isSuccessful) {
                withContext(Dispatchers.Main) {
                    state!!.value=true
                    updateModel(nCarreras!!)
                }
            } else {
                state!!.value=false
                message!!.value=call.message()//mensaje de error del servidor...
            }
        }
    }

    fun check_state(): Boolean? {
        return state!!.value
    }

   private fun updateModel(NCarreras:List<Carrera>) {
        carreraListLiveData!!.value = NCarreras
    }
}