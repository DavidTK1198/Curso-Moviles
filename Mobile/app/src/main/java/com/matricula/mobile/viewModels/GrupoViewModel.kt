package com.matricula.mobile.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.matricula.mobile.models.Carrera
import com.matricula.mobile.models.Ciclo
import com.matricula.mobile.models.Curso
import com.matricula.mobile.models.Grupo

class GrupoViewModel:ViewModel() {

    private var GrupoListLiveData: MutableLiveData<List<Grupo>>? = null
    private var state: MutableLiveData<Boolean>?=null
    private var message: MutableLiveData<String>?=null
    private var ciclo:MutableLiveData<Ciclo>?= MutableLiveData<Ciclo>()
    private var Grupo:MutableLiveData<Grupo>?= MutableLiveData<Grupo>()
    private var curso:MutableLiveData<Curso>?= MutableLiveData<Curso>()


    fun getGruposList(): MutableLiveData<List<Grupo>>? {
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

    fun getCiclo(): Ciclo? {
        return this.ciclo!!.value
    }

    fun getCurso(): Curso? {
        return this.curso!!.value
    }
    fun updateCurso(curso: Curso) {
        this.curso!!.value=curso
    }
    fun updateCiclo(ciclo: Ciclo) {
        this.ciclo!!.value=ciclo
    }

    fun updateGrupo(grupo: Grupo){
        this.Grupo!!.value=grupo
    }
    fun getGrupo(): Grupo? {
        return this.Grupo!!.value
    }
}