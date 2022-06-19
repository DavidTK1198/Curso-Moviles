package com.matricula.mobile.app.ui
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.matricula.mobile.R
import com.matricula.mobile.apiService.CarreraService
import com.matricula.mobile.apiService.UsuarioService
import com.matricula.mobile.models.Carrera
import com.matricula.mobile.models.Usuario
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditarUsuarioFragment: FragmentUtils() {
    private lateinit  var editTextName: EditText
    private lateinit var editTextCodigo : EditText
    private lateinit var usuario: Usuario
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_edit_users, container, false)

        view.findViewById<Button>(R.id.btn_guardar_Us).setOnClickListener{
            this.crearUsuario()
        }

        view.findViewById<Button>(R.id.btn_volver_us).setOnClickListener {
            volver()
        }
        editTextName=view.findViewById(R.id.editText_Name_User_edit)
        editTextCodigo= view.findViewById(R.id.editText_Clave_us)
        val bundle = this.arguments
        if (bundle != null) {
            val json = bundle.get("usuario").toString() // Key
            val gson = Gson()
            val usuario = gson.fromJson(json, Usuario::class.java)
            this.usuario=usuario
            rellenar(usuario)
        }else{
            volver()
        }
        return view
    }
    private fun crearUsuario() {
        var name = editTextName?.text.toString()
        var codigo = editTextCodigo?.text.toString()
        this.usuario.clave=codigo
        this.usuario.nombre=name
        if (validarDatos()) {
            modificarUsuario()
        }else{
            val  message = "Debe Completar todos los datos!!"
            Snackbar
                .make(view!!, message!!, Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show()}
    }


    private  fun rellenar(usuario: Usuario){
        editTextName?.setText(usuario.nombre)
        editTextCodigo?.setText(usuario.clave)
    }
    private fun volver(){
        setToolbarTitle("Seguridad")
        changeFragment(UsuariosFragment())
    }

    private fun validarDatos():Boolean{//Manejo de errores
        return(editTextName?.text.toString()!="" &&
                editTextCodigo?.text.toString()!="")
    }

    private fun modificarUsuario(){
        CoroutineScope(Dispatchers.IO).launch {
            val call = UsuarioService.getInstance().modificarUsuario(usuario)
            if (call.isSuccessful) {
                withContext(Dispatchers.Main) {
                }
            } else {

            }
        }
    }
}