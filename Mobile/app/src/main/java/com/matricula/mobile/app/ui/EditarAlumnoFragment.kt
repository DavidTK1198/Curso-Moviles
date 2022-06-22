package com.matricula.mobile.app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.fragment.app.activityViewModels
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.matricula.mobile.R
import com.matricula.mobile.apiService.AlumnoService
import com.matricula.mobile.models.Alumno
import com.matricula.mobile.models.Carrera
import com.matricula.mobile.viewModels.AlumnoViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditarAlumnoFragment : FragmentUtils() {private lateinit  var editTextName: EditText
    private lateinit var editTextCodigo : EditText
    private lateinit var editTexttelefono : EditText
    private lateinit var editTextmail : EditText
    private lateinit var fec_nac : EditText
    private  lateinit var alumno: Alumno
    private val alumnoViewModel: AlumnoViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_edit_students, container, false)

        view.findViewById<Button>(R.id.btn_guardar_alumno).setOnClickListener{
            this.crearAlumno()
        }
        view.findViewById<Button>(R.id.cargarCarrera).setOnClickListener{
            this.selecionar()
        }
        view.findViewById<Button>(R.id.btn_volver_alumno).setOnClickListener {
            volver()
        }
        val carrera= view.findViewById<EditText>(R.id.CarreraAl)
        carrera.isEnabled=false
        if(alumnoViewModel.getCarrera()!=null)
            carrera.setText(alumnoViewModel.getCarrera()!!.nombre)
        editTexttelefono=view.findViewById(R.id.editText_Tel_al)
        editTextName=view.findViewById(R.id.editText_Name_al)
        editTextCodigo= view.findViewById(R.id.editText_Cedula_al)
        editTextmail=view.findViewById(R.id.editTextEmail_al)
        fec_nac=view.findViewById(R.id.editTextTextfec_al2)
        alumnoViewModel.getAlumno()!!.observe(viewLifecycleOwner) { alumno ->
            rellenar(this.alumno)
        }
        editTextCodigo.isEnabled=false
        val bundle = this.arguments
        if (bundle != null) {
            val json = bundle.get("Alumno").toString() // Key
            val gson = Gson()
            alumno = gson.fromJson(json, Alumno::class.java)
            rellenar(alumno)
        }else{
            setToolbarTitle("Alumnos")
            changeFragment(AlumnosFragment())
        }
        return view
    }

    private fun rellenar(alumno: Alumno?) {
        var name = editTextName?.setText(alumno!!.nombre)
        var ced = editTextCodigo?.setText(alumno!!.cedula)
        var tel = editTexttelefono?.setText(alumno!!.teléfono)
        var mail=editTextmail?.setText(alumno!!.email)
        var fecha=fec_nac.setText(alumno!!.fech_nac)
    }

    private fun crearAlumno() {
        var name = editTextName?.text.toString()
        var ced = editTextCodigo?.text.toString()
        var tel = editTexttelefono?.text.toString()
        var mail=editTextmail?.text.toString()
        var fecha=fec_nac.text.toString()
        var carrera=alumnoViewModel.getCarrera()
        var Alumno = Alumno(ced,name,tel,mail,fecha,alumno.getCarrera())
        if (validarDatos()) {
            insertarAlumno(Alumno)
        }else{
            val  message = "Debe Completar todos los datos!!"
            Snackbar
                .make(view!!, message!!, Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show()}
    }

    private fun volver(){
        setToolbarTitle("Alumnos")
        changeFragment(AlumnosFragment())
    }

    private fun selecionar(){
        var name = editTextName?.text.toString()
        var ced = editTextCodigo?.text.toString()
        var tel = editTexttelefono?.text.toString()
        var mail=editTextmail?.text.toString()
        var fecha=fec_nac.text.toString()
        var al = Alumno(ced,name,tel,mail,fecha, Carrera())
        alumnoViewModel.updateAlumno(al)
        setToolbarTitle("Selecionar Carrera")
        changeFragment(Alumno_CarreraFragment())
    }

    private fun initLoading(){
        val loader = view?.findViewById<ProgressBar>(R.id.loading)
        loader?.visibility= View.VISIBLE
    }

    private fun stopLoadingError(){
        val loader=view?.findViewById<ProgressBar>(R.id.loading)
        loader?.visibility= View.GONE
    }

    private fun stopLoadingSuccess(){
        val loader=view?.findViewById<ProgressBar>(R.id.loading)
        loader?.visibility= View.GONE
        val dialog=SuccessDiaglogFragment()
        val bundle = Bundle()
        bundle.putString("La Alumno ${editTextName.text.toString()} con el código ${editTextCodigo.text.toString()} fue creada " +
                "correctamente", "mensaje")
        dialog.arguments=bundle
        dialog.show(childFragmentManager,"agregar")
    }

    private fun validarDatos():Boolean{//Manejo de errores
        return(editTextName?.text.toString()!="" &&
                editTextCodigo?.text.toString()!=""&& editTexttelefono?.text.toString()!=""
                &&editTextmail?.text.toString()!="" && fec_nac.text.toString()!="")
    }

    private fun insertarAlumno(Alumno: Alumno){
        initLoading()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val call = AlumnoService.getInstance().modificarAlumno(Alumno)
                if (call.isSuccessful) {
                    withContext(Dispatchers.Main) {
                        stopLoadingSuccess()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        stopLoadingError()
                    }
                }
            } catch (e: Exception) {

            }
        }
    }
}