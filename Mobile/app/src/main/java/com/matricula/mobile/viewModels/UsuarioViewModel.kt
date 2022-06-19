package com.matricula.mobile.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.matricula.mobile.models.Usuario


class UsuarioViewModel: ViewModel() {

    private var UsuarioListLiveData: MutableLiveData<List<Usuario>>? = null
    private var state:MutableLiveData<Boolean>?=null
    private var message:MutableLiveData<String>?=null

    fun getUsuariosList(): LiveData<List<Usuario>>? {
        if (UsuarioListLiveData == null) {
            UsuarioListLiveData = MutableLiveData<List<Usuario>>()
            state=MutableLiveData<Boolean>()
            message=MutableLiveData<String>()
        }
        return UsuarioListLiveData
    }

    fun check_state(): Boolean? {
        return state!!.value
    }
    fun setState(estado:Boolean){
        state!!.value=estado
    }
    fun updateModel(NUsuarios:List<Usuario>) {
        UsuarioListLiveData!!.value = NUsuarios
    }

    fun setMensaje(mensaje:String){
        message!!.value=mensaje
    }
}