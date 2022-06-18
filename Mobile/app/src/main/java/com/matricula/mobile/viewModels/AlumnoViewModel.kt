package com.matricula.mobile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.matricula.mobile.apiService.AlumnoService
import com.matricula.mobile.models.Alumno
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class AlumnoViewModel: ViewModel() {

    private var AlumnoListLiveData: MutableLiveData<List<Alumno>>? = null
    private var state:MutableLiveData<Boolean>?=null
    private var message:MutableLiveData<String>?=null

    fun getAlumnosList(): LiveData<List<Alumno>>? {
        if (AlumnoListLiveData == null) {
            AlumnoListLiveData = MutableLiveData<List<Alumno>>()
            state=MutableLiveData<Boolean>()
            message=MutableLiveData<String>()
        }
        return AlumnoListLiveData
    }

    fun check_state(): Boolean? {
        return state!!.value
    }
    fun setState(estado:Boolean){
        state!!.value=estado
    }
    fun updateModel(NAlumnos:List<Alumno>) {
        AlumnoListLiveData!!.value = NAlumnos
    }

    fun setMensaje(mensaje:String){
        message!!.value=mensaje
    }
}