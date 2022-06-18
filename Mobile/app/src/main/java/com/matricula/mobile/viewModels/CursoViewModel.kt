package com.matricula.mobile.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.matricula.mobile.apiService.CarreraService
import com.sistema.logicaDeNegocio.Curso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class CursoViewModel: ViewModel() {

    private var cursoListLiveData: MutableLiveData<List<Curso>>? = null
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

    fun setMensaje(mensaje:String){
        message!!.value=mensaje
    }
}